package com.example.nelther.recycleviewapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.nelther.recycleviewapp.profesor.updateProfesor;

import org.json.JSONObject;


public class verProfesor extends AppCompatActivity {
    Toolbar toolbar;
    TextView nombreProfesor,correo;
    Bundle b;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_profesor);
        context = this;
        b= getIntent().getBundleExtra("ProfesorBundle");
        toolbar = (Toolbar) findViewById(R.id.tb_verProfesor);
        setSupportActionBar(toolbar);
        nombreProfesor= (TextView) findViewById(R.id.txtNomProfesor_verProfesor);
        correo = (TextView)findViewById(R.id.txtCorreo_verProfesor);
        correo.setText(b.getString("correo"));
        nombreProfesor.setText(b.getString("nombre"));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ver_profesor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.eliminarProfesor :
                eliminarProfesor(b.getString("idProfesor"));
                break;

            case R.id.actualizarProfesor:
                Intent intent2 = new Intent(this,updateProfesor.class);
                intent2.putExtra("ProfesorBundle",b);
                startActivity(intent2);
                break;
            case R.id.enviarProfesor:
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto",b.getString("correo"), null));

                startActivity(Intent.createChooser(emailIntent, "Send email..."));
        }

        return super.onOptionsItemSelected(item);
    }
    public void eliminarProfesor(String idProfesor){
        String url = "http://162.243.64.92:8080/TareappWS/rest/tareas/delProfesor/"+ idProfesor;
        Log.i("Moviles", url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url,new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context, "Profesor Eliminado", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context,InicioActivity.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Moviles",error.getMessage());
                        // TODO Auto-generated method stub

                    }
                });

        Volley.newRequestQueue(context).add(jsObjRequest);
    }
}
