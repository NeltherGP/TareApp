package com.example.nelther.recycleviewapp.materia;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.nelther.recycleviewapp.InicioActivity;
import com.example.nelther.recycleviewapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nelther on 11/12/2015.
 */
public class UpdateMateria extends FragmentActivity implements View.OnClickListener{
    Context context;
    EditText nombremat;
    Spinner profesor;
    ArrayAdapter<String> profesores;
    List<String> nombProfesores = new ArrayList<String>();
    MateriaInfo materia=new MateriaInfo();
    ImageButton btnGuardar;
    Bundle b;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_materia);
        Log.i("Moviles", "UPDATE MATERIA");
        context=this;
        b=getIntent().getBundleExtra("MateriaBundle");

        consultarProfesores();
        nombremat= (EditText) findViewById(R.id.edtNombreMateria_addMateria);
        nombremat.setText(b.getString("nomMateria"));

        btnGuardar = (ImageButton) findViewById(R.id.fabAddMateria);
        btnGuardar.setOnClickListener(this);


    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    public void getIdProfesor(String nom){
        String url="http://162.243.64.92:8080/TareappWS/rest/tareas/getIdProfesorByNombre/"+nom.replaceAll(" ","%20");
        Log.i("Moviles", "AddMateria URL: " + url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url,new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            Log.e("Moviles","RESPONSE: "+response.toString());
                            insertarMateria(response.getInt("idProfesor"));
                        } catch (JSONException e) {
                            Log.e("Moviles","Error onResponseGetIdProfesor: "+e.getMessage());
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Moviles","Error GetIdPofesor: "+error.getMessage());
                        VolleyLog.d("Moviles", "Error: " + error.getMessage());
                        // TODO Auto-generated method stub

                    }
                });

        Volley.newRequestQueue(context).add(jsObjRequest);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fabAddMateria:
                getIdProfesor(profesor.getSelectedItem().toString());
                break;
        }
    }

    public void insertarMateria(int idProfesor){
        String url = "http://162.243.64.92:8080/TareappWS/rest/tareas/actMateria";
        Map<String,String> params = new HashMap<String,String>();
        params.put("nombreMateria",nombremat.getText().toString());
        params.put("idprofesor",""+idProfesor);
        params.put("idmateria",b.getString("idMateria"));
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.PUT, url,new JSONObject(params),new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context, "Materia Guardada", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context,InicioActivity.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.e("Moviles","Error InsertarMateria");
                        VolleyLog.d("Moviles", "Error: " + error.getMessage());
                        // TODO Auto-generated method stub

                    }


                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Log.i("Moviles", "GET HEADERS");
                Map<String,String> headers = new HashMap<String,String>();
                headers.put("Content-Type","application/json");
                return headers;
            }
        };

        Volley.newRequestQueue(context).add(jsObjRequest);
    }

    public void consultarProfesores() {
        String url = "http://162.243.64.92:8080/TareappWS/rest/tareas/listaProfesores";
        JsonArrayRequest jsObjRequest = new JsonArrayRequest
                (Request.Method.GET, url,new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i=0; i<response.length();i++){
                            try{
                                nombProfesores.add(response.getJSONObject(i).getString("nombre"));
                            }catch (Exception e){
                                Log.e("Moviles","Error ConultarProfesores: "+response.toString());
                                Log.e("Moviles",e.getMessage().toString());
                            }

                        }
                        profesores = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,nombProfesores);
                        profesor = (Spinner) findViewById(R.id.spnProfesorMateria_addmateria);
                        profesor.setAdapter(profesores);
                        profesor.setSelection(profesores.getPosition(b.getString("nomProfesor")));
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Moviles","addMateria"+error.getMessage().toString());

                        // TODO Auto-generated method stub

                    }
                });
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(jsObjRequest);
    }
}




