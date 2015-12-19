package com.example.nelther.recycleviewapp.profesor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.nelther.recycleviewapp.InicioActivity;
import com.example.nelther.recycleviewapp.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nelther on 21/11/2015.
 */
public class addProfesor extends FragmentActivity implements View.OnClickListener {
    EditText nombre,correo;
    Context context;
    ImageButton btn;
    ProfesorInfo profe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_profesor);
        context=this;
        nombre = (EditText) findViewById(R.id.edtNombreProfesor);
        correo= (EditText)findViewById(R.id.edtCorreoProfesor);
        btn=(ImageButton)findViewById(R.id.fabProfesors);
        btn.setOnClickListener(this);


    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case R.id.fabProfesors:
                    profe=new ProfesorInfo();
                    profe.nombreProfesor=nombre.getText().toString();
                    profe.correoProfesor=correo.getText().toString();
                    insertarProfe();
                    break;
            }
    }

    public void insertarProfe(){
        String url = "http://162.243.64.92:8080/TareappWS/rest/tareas/crearProfe";
        Map<String,String> params = new HashMap<String,String>();
        params.put("nombre",profe.nombreProfesor);
        params.put("correo",profe.correoProfesor);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, url,new JSONObject(params),new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context, "Se añadio Profesor", Toast.LENGTH_SHORT).show();
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
                Log.i("Moviles", "GET HEADERS");
                Map<String,String> headers = new HashMap<String,String>();
                headers.put("Content-Type","application/json");
                return headers;
            }
        };

        Volley.newRequestQueue(context).add(jsObjRequest);
    }
}
