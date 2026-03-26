package com.liam.lothgarder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class PlantaU extends AppCompatActivity {

    String nombreP;
    //int idP;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_planta_u);

        String correoU;

        Intent intent = getIntent();
        nombreP = intent.getStringExtra("nombre");
        //idP = intent.getStringExtra("id");
        preferences = getSharedPreferences("guardarSesion", Context.MODE_PRIVATE);
        correoU = preferences.getString("Correo","");

        //Extraccion del link de dominio desde strings.xml
        String link_domain = getString(R.string.link_domain);

        buscarPlanatU(link_domain + "?accion=buscarPU" + "&nombre=" + nombreP + "&usuario=" + correoU);

        /*
        Funcion local
        buscarPlanatU("http:// 192.168.137.128:80/lothgarder/buscarPU.php?nombre=" + nombreP + "&usuario=" + correoU);
         */


        Button bpluElim = findViewById(R.id.bpluElim);
        bpluElim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Extraccion del link de dominio desde strings.xml
                String link_domain = getString(R.string.link_domain);

                eliminarPlanta(link_domain + "?accion=eliminarPU");
                /*
                Funcion local
                eliminarPlanta( "http:// 192.168.137.128:80/lothgarder/eliminarPU.php");
                */
                Intent intPlutoPs = new Intent(PlantaU.this, Plantas.class);
                startActivity(intPlutoPs);

                finish();
            }
        });

        Button bplEdiP = findViewById(R.id.bplEdiP);
        bplEdiP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intPlutoEp = new Intent(PlantaU.this, EditarP.class);

                intPlutoEp.putExtra("nombrePSel", nombreP);
                startActivity(intPlutoEp);
            }
        });

        Button bpluPlutoPrin = findViewById(R.id.bpluPlutoPrin);
        bpluPlutoPrin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intPlutoPrin = new Intent(PlantaU.this, PantallaPrin.class);
                startActivity(intPlutoPrin);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    //Metodo para buscar planta de usuario
    private void buscarPlanatU(String URL) {

        TextView eNomPlU = findViewById(R.id.epluTitulo);
        TextView eApodoPlU = findViewById(R.id.epluApot);
        TextView eEstadoPlU = findViewById(R.id.epluEstadoT);
        TextView eLugarPlU = findViewById(R.id.epluLugarT);

        //Creacion de la peticion
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int reco = 0; reco < response.length(); reco++) {
                    try {

                        jsonObject = response.getJSONObject(reco);
                        eNomPlU.setText(jsonObject.getString("Nombre"));
                        eApodoPlU.setText(jsonObject.getString("Apodo"));
                        eEstadoPlU.setText(jsonObject.getString("Estado"));
                        eLugarPlU.setText(jsonObject.getString("Lugar"));

                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.e("Volley", "Error: " + error.toString());
                //error.printStackTrace();
                Toast.makeText(getApplicationContext(), "ERROR DE CONEXIÓN", Toast.LENGTH_SHORT).show();
            }
        }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    //Metodo para eliminar planta
    private void eliminarPlanta(String URL){
        //Creacion de la peticion al servidor con el metodo POST
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                /*Toast.makeText(getApplicationContext(), "Respuesta del servidor: " + response, Toast.LENGTH_LONG).show();
                EditText edError = findViewById(R.id.error);
                edError.setText(response);*/

                Toast.makeText(getApplicationContext(), "Operación exitosa",Toast.LENGTH_LONG).show();
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
                String nombreP;
                Intent intent = getIntent();
                nombreP = intent.getStringExtra("nombre");
                getSharedPreferences("guardarSesion", Context.MODE_PRIVATE);
                String correoU= preferences.getString("Correo", "");

                //Metodo Map que manda los datos de la peticion al servidor extrallendo informacion del editText para guardar en la base
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("nombre", nombreP);
                parametros.put("correo", correoU);

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