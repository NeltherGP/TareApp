package com.example.nelther.recycleviewapp.tareas;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.example.nelther.recycleviewapp.TareappBD;
import com.example.nelther.recycleviewapp.tareas.UpdateTarea;

import org.json.JSONObject;


/**
 * Created by nelther on 09/11/2015.
 */
public class VerTarea extends AppCompatActivity{
    Toolbar toolbar;
    TextView nombreTarea,materia,descripcion,entrega;
    TareappBD BD;
    SQLiteDatabase admin_flujo;
    Bundle b;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_tarea_layout);

        context=this;
         b = getIntent().getBundleExtra("TareaBundle");
        toolbar = (Toolbar) findViewById(R.id.tb_verTarea);
        setSupportActionBar(toolbar);
        nombreTarea = (TextView) findViewById(R.id.txtNomTarea_verTarea);
        materia = (TextView) findViewById(R.id.txtMateriaTarea_verTarea);
        descripcion = (TextView) findViewById(R.id.txtDescripcionTarea_verTarea);
        entrega = (TextView) findViewById(R.id.txtFechaEntrega_verTarea);
        nombreTarea.setText(b.getString("nomtarea"));
        materia.setText(b.getString("materia"));
        descripcion.setText(b.getString("descTarea"));
        entrega.setText(b.getString("fechaEntrega"));

        BD = new TareappBD(this,"Tareapp",null,2);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ver_tarea,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.eliminar :
                eliminarTarea(b.getInt("idTarea"));
                Toast.makeText(this, "Tarea Eliminada", Toast.LENGTH_SHORT).show();

                break;

            case R.id.actualizar:
                Intent intent2 = new Intent(this,UpdateTarea.class);
                intent2.putExtra("TareaBundle",b);
                startActivity(intent2);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /*public void eliminarTarea(int idTarea){
        admin_flujo = BD.getWritableDatabase();
        String qry = "Delete from tarea where idtarea="+idTarea;
        admin_flujo.execSQL(qry);
    }*/
    public void eliminarTarea(int idTarea){
        String url = "http://162.243.64.92:8080/TareappWS/rest/tareas/delTarea/"+ idTarea;
        Log.i("Moviles", url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url,new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context,"Tarea Eliminada", Toast.LENGTH_SHORT).show();
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
