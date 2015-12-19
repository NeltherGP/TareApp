package com.example.nelther.recycleviewapp.materia;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.nelther.recycleviewapp.AcercaDe;
import com.example.nelther.recycleviewapp.DividerItemDecoration;
import com.example.nelther.recycleviewapp.R;
import com.example.nelther.recycleviewapp.TareappBD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nelther on 08/11/2015.
 */
public class FragmentoMateria extends Fragment implements SearchView.OnQueryTextListener {
    int color;
    MateriaRecycleAdapter adapter;
    Context context;
    List<MateriaInfo> listMaterias;
    MateriaInfo materia ;
    RecyclerView recyclerView;

    public FragmentoMateria(){

    }
    @SuppressLint("ValidFragment")
    public FragmentoMateria(int color, Context context) {
        Log.i("MOVILES","Constructor Frag.Materia");
        this.context = context;
        this.color = color;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("MOVILES","FragmentoMateria");
        setHasOptionsMenu(true);
    consulta();

        View view = inflater.inflate(R.layout.fragmento_layout,container,false);

        final FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.framelayout);

        frameLayout.setBackgroundColor(0xFF37474f);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerviewFragment);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(context);
        recyclerView.addItemDecoration(itemDecoration);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);


        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        final MenuItem acerca=menu.findItem(R.id.AcercaDe);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        // Do something when collapsed
                        adapter.setFilter(listMaterias);
                        return true; // Return true to collapse action view
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        // Do something when expanded
                        return true; // Return true to expand action view
                    }
                });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intAceerca;
        if(item.getItemId()==R.id.AcercaDe){
            intAceerca = new Intent(context,AcercaDe.class);
            startActivity(intAceerca);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public void consulta(){
        String url = "http://162.243.64.92:8080/TareappWS/rest/tareas/listaMaterias";
        Log.i("Moviles", url);
        listMaterias=new ArrayList<MateriaInfo>();
        JsonArrayRequest jsObjRequest = new JsonArrayRequest
                (Request.Method.GET, url,new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        for(int i=0; i<response.length();i++){
                          materia=new MateriaInfo();
                            try {
                                JSONObject jsonObject=response.getJSONObject(i);
                                materia.nombreMateria=jsonObject.getString("nombreMateria");
                                materia.profesor=jsonObject.getString("nomProfesor");
                                materia.idMateria=jsonObject.getString("idmateria");
                                listMaterias.add(materia);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        adapter= new MateriaRecycleAdapter(listMaterias,context);

                        recyclerView.setAdapter(adapter);

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("Moviles", "Error: " + error.getStackTrace());
                        // TODO Auto-generated method stub

                    }
                });
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(jsObjRequest);
    }

    private List<MateriaInfo> filter(List<MateriaInfo> models, String query) {
        query = query.toLowerCase();

        final List<MateriaInfo> filteredModelList = new ArrayList<>();
        for (MateriaInfo model : models) {
            final String text = model.nombreMateria.toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<MateriaInfo> filteredModelList = filter(listMaterias, newText);
        adapter.setFilter(filteredModelList);
        return false;
    }

}
