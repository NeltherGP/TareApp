package com.example.nelther.recycleviewapp.tareas;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
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
import com.example.nelther.recycleviewapp.DatePicker;
import com.example.nelther.recycleviewapp.InicioActivity;
import com.example.nelther.recycleviewapp.R;
import com.example.nelther.recycleviewapp.TareappBD;
import com.example.nelther.recycleviewapp.TimePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nelther on 10/11/2015.
 */
public class UpdateTarea extends AppCompatActivity implements View.OnClickListener{
    TareappBD BD;
    SQLiteDatabase admin_flujo;
    List<String> nombMaterias = new ArrayList<String>();
    ArrayAdapter<String> materias;
    Spinner spnMaterias;
    EditText edtNombreMateria,edtDescTarea,edtRecordatorio,edtFechaEntrega,edtNombreTarea,horaReminder;
    ImageButton btnSave;
    TareaInfo tareaInfo = new TareaInfo();
    Bundle b;
    Context context;
    DialogFragment datePicker,timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tarea);
        context=this;
        b=getIntent().getBundleExtra("TareaBundle");
        BD = new TareappBD(this,"Tareapp",null,2);
        //admin_flujo = BD.getReadableDatabase();
        consultarMaterias();
        Log.i("Moviles", "consulta");
        materias = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,nombMaterias);
        spnMaterias = (Spinner) findViewById(R.id.spnMateriaTarea);
        spnMaterias.setAdapter(materias);
        edtNombreTarea = (EditText) findViewById(R.id.edtNombreTarea);
        edtDescTarea = (EditText) findViewById(R.id.edtDescTarea);
        edtFechaEntrega=(EditText) findViewById(R.id.edtFechaEntrega);
        horaReminder=(EditText)findViewById(R.id.edtTimereminder);
        horaReminder.setOnClickListener(this);
        edtRecordatorio = (EditText) findViewById(R.id.edtRecordar);
        btnSave = (ImageButton) findViewById(R.id.fabTarea1);
        btnSave.setOnClickListener(this);

        edtNombreTarea.setText(b.getString("nomtarea"));
        edtDescTarea.setText(b.getString("descTarea"));
        edtFechaEntrega.setText(b.getString("fechaEntrega"));
        edtFechaEntrega.setInputType(InputType.TYPE_NULL);
        edtFechaEntrega.setOnClickListener(this);
        spnMaterias.setSelection(materias.getPosition(b.getString("materia")));
        datePicker = new DatePicker();
        timePicker= new TimePicker();
        Log.i("Moviles","ID TAREA UPDATE: "+b.getInt("idTarea"));



    }
    public void consultarMaterias() {
        String url = "http://162.243.64.92:8080/TareappWS/rest/tareas/listaMaterias";
        Log.i("Moviles", url);
        JsonArrayRequest jsObjRequest = new JsonArrayRequest
                (Request.Method.GET, url,new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i=0; i<response.length();i++){
                            try{
                                nombMaterias.add(response.getJSONObject(i).getString("nombreMateria"));
                            }catch (Exception e){
                                Log.e("Moviles",response.toString());
                                Log.e("Moviles",e.getMessage().toString());
                            }

                        }
                        materias = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,nombMaterias);
                        spnMaterias = (Spinner) findViewById(R.id.spnMateriaTarea);
                        spnMaterias.setAdapter(materias);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Moviles",error.getMessage().toString());
                        // TODO Auto-generated method stub

                    }
                });
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(jsObjRequest);
    }

    @Override

        public void onClick(View v) {

            switch (v.getId()){
                case R.id.edtTimereminder: timePicker.show(getFragmentManager(), "datePicker");
                    break;
                case R.id.edtFechaEntrega:  datePicker.show(getFragmentManager(), "datePicker");
                    break;
                case R.id.fabTarea1:
                    tareaInfo.descripcion = edtDescTarea.getText().toString();
                    tareaInfo.entrega = edtFechaEntrega.getText().toString();
                    tareaInfo.nombreTarea = edtNombreTarea.getText().toString();
                    tareaInfo.materia = spnMaterias.getSelectedItem().toString();
                    tareaInfo.recordar = edtRecordatorio.getText().toString();

                    getIdMateria(tareaInfo.materia);

            }

        }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    public void actTarea(int idMateria){
        String url = "http://162.243.64.92:8080/TareappWS/rest/tareas/actTarea";
        Log.i("Moviles", url);
        Map<String,String> params = new HashMap<String,String>();
        params.put("idTarea",""+b.getInt("idTarea"));
        params.put("nombreTarea",tareaInfo.nombreTarea);
        params.put("Fechaentrega",tareaInfo.entrega);
        params.put("DescTarea",tareaInfo.descripcion);
        params.put("Recordar",tareaInfo.recordar);
        params.put("Materia", "" + idMateria);
        Log.i("Moviles",new JSONObject(params).toString());
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.PUT, url,new JSONObject(params),new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context,"Tarea Actualizada", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context,InicioActivity.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("Moviles", "Error: " + error.getMessage());
                        Log.i("Moviles", "Error: " + error.getMessage());
                        // TODO Auto-generated method stub

                    }


                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Log.i("Moviles","GET HEADERS");
                Map<String,String> headers = new HashMap<String,String>();
                headers.put("Content-Type","application/json");
                return headers;
            }
        };

        Volley.newRequestQueue(context).add(jsObjRequest);
    }

    public void getIdMateria(String nom){

        String url = "http://162.243.64.92:8080/TareappWS/rest/tareas/getIdMateriaByNombre/"+nom.replaceAll(" ","%20");

        Log.i("Moviles", url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url,new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            Log.e("Moviles","IDMATERIA: "+response.toString());
                            actTarea(response.getInt("idmateria"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("Moviles", "Error: " + error.getMessage());
                        // TODO Auto-generated method stub

                    }
                });

        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(5000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(jsObjRequest);
    }

}
