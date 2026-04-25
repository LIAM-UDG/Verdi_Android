package com.liam.lothgarder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NuevoSis extends AppCompatActivity {

    //DEFINICION DE VARIABLES GLOBALES
    private RequestQueue requestQueue;
    private EditText edId, edCod, edNom;
    private Button btnNstoP, btnGuar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_nuevo_sis);

        //Accion del boton para cambiar de la pantalla de registro a la main
        btnNstoP = findViewById(R.id.btnNstoPrin);
        btnNstoP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NuevoSis.this, "Llendo a la pantalla de inicio", Toast.LENGTH_SHORT).show();
                Intent intRMain = new Intent(NuevoSis.this, SistemaU.class);
                startActivity(intRMain);
                finish();
            }
        });

        //Accion del boton para cambiar de la pantalla de registro a la main
        btnGuar = findViewById(R.id.btnNsContinuar);
        btnGuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Paso de la componentes de xml a java
                edId = findViewById(R.id.etnsID);
                edCod = findViewById(R.id.etnsCodVal);
                //Extraccion de los string de los campos del xml
                String id = edId.getText().toString().trim();
                String codigo = edCod.getText().toString().trim();

                if (id.isEmpty()) {
                    Toast.makeText(NuevoSis.this, "Debes llenar todos los datos", Toast.LENGTH_LONG).show();
                    edId.setError("Llena el campo de ID");
                    return;
                } else if (codigo.isEmpty()) {
                    Toast.makeText(NuevoSis.this, "Debes llenar todos los datos", Toast.LENGTH_LONG).show();
                    edCod.setError("Llena el campo del codigo");
                    return;
                }

                //Extraccion del link de dominio desde strings.xml
                String link_domain = getString(R.string.link_domain);

                //Llamado a la funcion para guardar usuario base en la nube
                vincularSis(link_domain + "?accion=vincularSyU");

            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    //Funcion para vincular el sistema
    private void vincularSis(String URL){
        //Creacion de la peticion al servidor con el metodo POST
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    //Evaluacion del mensaje que envia el servidor
                    if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                        String mensajeError = jsonObject.getString("message");
                        Toast.makeText(getApplicationContext(), mensajeError, Toast.LENGTH_LONG).show();
                        return;
                    }

                    //Evaluacion
                    if (jsonObject.has("clave")) {
                        String clave = jsonObject.getString("clave");

                        // Referencias a los componentes
                        edId = findViewById(R.id.etnsID);
                        edNom = findViewById(R.id.etnsNombreS);

                        String id = edId.getText().toString().trim();
                        String nombre = edNom.getText().toString().trim();

                        Toast.makeText(getApplicationContext(), "¡Vinculación Exitosa!", Toast.LENGTH_SHORT).show();

                        // Guardamos y pasamos de pantalla
                        guardarSistema(nombre, id, clave);

                        Intent intNstoSu = new Intent(NuevoSis.this, SistemaU.class);
                        startActivity(intNstoSu);
                        finish();
                    }

                    /*String clave = jsonObject.getString("clave");

                    //Paso de la componentes de xml a java
                    edId = findViewById(R.id.etnsID);
                    edNom = findViewById(R.id.etnsNombreS);
                    //Extraccion de los string de los campos del xml
                    String id = edId.getText().toString().trim();
                    String nombre = edNom.getText().toString().trim();

                    Toast.makeText(getApplicationContext(),"Clave: " + clave, Toast.LENGTH_SHORT).show();

                    guardarSistema(nombre, id, clave);

                    // Pasar a la siguiente pantalla
                    Intent intNstoSu = new Intent(NuevoSis.this, SistemaU.class);
                    startActivity(intNstoSu);
                    finish();*/

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error al parsear JSON", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error: " +  error.toString(),Toast.LENGTH_LONG).show();
                Log.e("Volley Error", error.toString());
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                //Definicion local de editText contenedor del que se extrae la informacion
                edId = findViewById(R.id.etnsID);
                edCod = findViewById(R.id.etnsCodVal);
                //Llamada de sesion
                SharedPreferences preferences = getSharedPreferences("guardarSesion", Context.MODE_PRIVATE);

                //Metodo Map que manda los datos de la peticion al servidor extrallendo informacion del editText para guardar en la base
                Map<String, String> parametros = new HashMap<String, String>();
                //Dentro de map ademas de mandar los datos tambien los extra en cadena del editext
                parametros.put("idS", edId.getText().toString());
                parametros.put("codigo", edCod.getText().toString());
                parametros.put("correo", preferences.getString("Correo",""));

                return parametros;
            }
        };

        //Extension de tiempo de espera para la respuesta del servidor
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000, // Timeout en ms
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    //Funcion para guardar la informacion del sistema
    private void guardarSistema(String nombre, String id, String claveAct) {
        SharedPreferences preferences = getSharedPreferences("guardarSistema", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Nombre", nombre);
        editor.putString("ID", id);
        editor.putString("Clave",claveAct);
        editor.commit();

    }

}