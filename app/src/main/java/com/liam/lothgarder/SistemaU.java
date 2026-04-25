package com.liam.lothgarder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class SistemaU extends AppCompatActivity {

    private Button bsuSutoNS, btnEliS, bsuSutoEst, bsuSutoPrin;
    TextView esNom;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sistema_u);

        preferences = getSharedPreferences("guardarSistema", Context.MODE_PRIVATE);

        String nombre = preferences.getString("Nombre", "");

        esNom = findViewById(R.id.esuNomV);
        esNom.setText(nombre);

        //Accion de boton de Agregar Sistema
        bsuSutoNS = findViewById(R.id.bsuAgre);
        bsuSutoNS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intPtoEst = new Intent(SistemaU.this, NuevoSis.class);
                startActivity(intPtoEst);
            }
        });

        //Accion de boton de eliminar
        btnEliS = findViewById(R.id.bsuEli);
        btnEliS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Extraccion del link de dominio desde strings.xml
                String link_domain = getString(R.string.link_domain);

                eliminarSis(link_domain + "?accion=eliminarVinS");

            }
        });

        //Accion de boton de Estadisticas de Sistema
        bsuSutoEst = findViewById(R.id.bsuSutoEst);
        bsuSutoEst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intPtoEst = new Intent(SistemaU.this, Estadisticas.class);
                startActivity(intPtoEst);
            }
        });

        //Accion de boton de para regresar al Inicio
        bsuSutoPrin = findViewById(R.id.bsuSutoPrin);
        bsuSutoPrin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intPtoEst = new Intent(SistemaU.this, PantallaPrin.class);
                intPtoEst.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intPtoEst);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    //Metodo para eliminar planta
    private void eliminarSis(String URL){
        //Creacion de la peticion al servidor con el metodo POST
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Operación exitosa",Toast.LENGTH_LONG).show();

                SharedPreferences preferences = getSharedPreferences("guardarSistema", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("Nombre", "Cargando\nnombre...");
                editor.putString("ID", null);
                editor.putString("Clave",null);
                editor.commit();

                Intent refresh = new Intent(SistemaU.this, SistemaU.class);
                startActivity(refresh);
                finish();

            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(),Toast.LENGTH_LONG).show();
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                //Definicion local de variables
                SharedPreferences preferences = getSharedPreferences("guardarSistema", Context.MODE_PRIVATE);
                String id = preferences.getString("ID", "");
                String clave = preferences.getString("Clave", "");

                //Metodo Map que manda los datos de la peticion al servidor extrallendo informacion del editText para guardar en la base
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("idS", id);
                parametros.put("clave", clave);

                return parametros;
            }
        };

        //Extension de tiempo de espera para la respuesta del servidor
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000, // Timeout en ms
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}