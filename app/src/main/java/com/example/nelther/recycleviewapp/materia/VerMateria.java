package com.example.nelther.recycleviewapp.materia;

import android.content.Context;
import android.content.Intent;
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
import com.example.nelther.recycleviewapp.InicioActivity;
import com.example.nelther.recycleviewapp.R;

import org.json.JSONObject;

public class VerMateria extends AppCompatActivity {

    Toolbar toolbar;
    TextView materia, profesor;
    Bundle b;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_materia);
        context= getApplicationContext();
        b = getIntent().getBundleExtra("MateriaBundle");
        toolbar = (Toolbar)findViewById(R.id.tb_verMateria);
        setSupportActionBar(toolbar);
        materia = (TextView) findViewById(R.id.txtNomMateria_verMateria);
        profesor = (TextView) findViewById(R.id.txtProfesor_verMateria);
        materia.setText(b.getString("nomMateria"));
        profesor.setText(b.getString("nomProfesor"));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ver_tarea, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()){
            case R.id.eliminar :
                eliminarMateria(b.getString("idMateria"));
                break;

            case R.id.actualizar:
                Intent intent2 = new Intent(this,UpdateMateria.class);
                intent2.putExtra("MateriaBundle",b);
                Log.i("Moviles","Boton actualizar");
                startActivity(intent2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void eliminarMateria(String idMateria){
        String url = "http://162.243.64.92:8080/TareappWS/rest/tareas/delMateria/"+ idMateria;
        Log.i("Moviles", url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url,new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context,"Materia Eliminada", Toast.LENGTH_SHORT).show();
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
