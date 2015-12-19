package com.example.nelther.recycleviewapp.tareas;

import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import com.example.nelther.recycleviewapp.AlarmReceiver;
import com.example.nelther.recycleviewapp.DatePicker;
import com.example.nelther.recycleviewapp.InicioActivity;
import com.example.nelther.recycleviewapp.R;
import com.example.nelther.recycleviewapp.TimePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddTarea extends FragmentActivity implements View.OnClickListener{


    List<String> nombMaterias = new ArrayList<String>();
    ArrayAdapter<String> materias;
    Spinner spnMaterias;
    EditText edtNombreMateria,edtDescTarea,edtRecordatorio,edtFechaEntrega,edtNombreTarea,horaReminder;
    ImageButton btnSave;
    TareaInfo tareaInfo = new TareaInfo();
    DialogFragment datePicker, timePicker;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tarea);
        context=getApplicationContext();

        consultarMaterias();
        Log.i("Moviles", "consulta");

        edtNombreTarea = (EditText) findViewById(R.id.edtNombreTarea);
        edtDescTarea = (EditText) findViewById(R.id.edtDescTarea);
        edtFechaEntrega=(EditText) findViewById(R.id.edtFechaEntrega);
        horaReminder=(EditText)findViewById(R.id.edtTimereminder);
        edtRecordatorio = (EditText) findViewById(R.id.edtRecordar);
        btnSave = (ImageButton) findViewById(R.id.fabTarea1);
        btnSave.setOnClickListener(this);
        edtFechaEntrega.setOnClickListener(this);
        horaReminder.setOnClickListener(this);
        edtFechaEntrega.setInputType(InputType.TYPE_NULL);
        datePicker = new DatePicker();
        timePicker= new TimePicker();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_tarea, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(5000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
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
                            insertarTarea(response.getInt("idmateria"));
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
               // Toast.makeText(this, "Tarea Guardada", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    public void setReminder (String recordar,int tiempo,String hora){

        final AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        String [] fecha = recordar.split("/");
        String [] Hora = hora.split(":");
        Log.i("Moviles","HORA: "+ Hora[0]+":"+Hora[1]);

        final Intent intentAlarm = new Intent(AddTarea.this, AlarmReceiver.class);
        Calendar calendar =  Calendar.getInstance();
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, Integer.parseInt(Hora[1]));
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(Hora[0]));
        calendar.set(Calendar.MONTH, Integer.parseInt(fecha[1])-1);
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(fecha[2]));
        calendar.set(Calendar.YEAR, Integer.parseInt(fecha[0]));
        if(tiempo!=0)
        calendar.add(Calendar.DAY_OF_MONTH,tiempo-1);

        long when = calendar.getTimeInMillis();

        Log.i("Moviles","TIEMPO: "+calendar.getTime());

        alarmManager.set(AlarmManager.RTC_WAKEUP, when, PendingIntent.getBroadcast(AddTarea.this, 1, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));

    }

    public void insertarTarea(int idMateria){
        String url = "http://162.243.64.92:8080/TareappWS/rest/tareas/crearTarea";
        Log.i("Moviles", url);
        Map<String,String> params = new HashMap<String,String>();
        params.put("nombreTarea",tareaInfo.nombreTarea);
        params.put("Fechaentrega",tareaInfo.entrega);
        params.put("DescTarea",tareaInfo.descripcion);
        params.put("Recordar",tareaInfo.recordar);
        params.put("Materia", "" + idMateria);
        Log.i("Moviles",new JSONObject(params).toString());
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, url,new JSONObject(params),new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context,"Tarea Guardada", Toast.LENGTH_SHORT).show();
                        setReminder(tareaInfo.entrega, Integer.parseInt(tareaInfo.recordar), horaReminder.getText().toString());
                        Intent intent = new Intent(context,InicioActivity.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {


                        VolleyLog.d("Moviles", "Error: " + error.getMessage());
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
    }

