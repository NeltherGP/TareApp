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
 * Created by nelther on 10/12/2015.
 */
public class updateProfesor extends FragmentActivity implements View.OnClickListener{
    EditText nombre,correo;
    Context context;
    ImageButton btn;
    ProfesorInfo profe;
    Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_profesor);
        b=getIntent().getBundleExtra("ProfesorBundle");
        context=this;
        nombre = (EditText) findViewById(R.id.edtNombreProfesor);
        nombre.setText(b.getString("nombre"));
        correo=(EditText) findViewById(R.id.edtCorreoProfesor);
        correo.setText(b.getString("correo"));
        btn=(ImageButton)findViewById(R.id.fabProfesors);
        btn.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fabProfesors:
                profe=new ProfesorInfo();
                profe.nombreProfesor=nombre.getText().toString();
                profe.correoProfesor=correo.getText().toString();
                profe.idProfesor=b.getString("idProfesor");
                insertarProfe();
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    public void insertarProfe(){
        String url = "http://162.243.64.92:8080/TareappWS/rest/tareas/actProfesor";
        Map<String,String> params = new HashMap<String,String>();
        params.put("nombre",profe.nombreProfesor);
        params.put("idProfesor",profe.idProfesor);
        params.put("correo",profe.correoProfesor);
        Log.i("Moviles", new JSONObject(params).toString());
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.PUT, url,new JSONObject(params),new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context, "Se Actualizo Profesor", Toast.LENGTH_SHORT).show();
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
