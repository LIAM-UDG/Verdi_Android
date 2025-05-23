package com.liam.lothgarder;

import android.content.Intent;
import android.content.SyncRequest;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.DefaultRetryPolicy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class NewUsuario extends AppCompatActivity {

    //Definicion global
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_usuario);

        Toast.makeText(NewUsuario.this, "Pantalla usuario nuevo", Toast.LENGTH_LONG).show();

        //Accion del boton para cambiar de la pantalla de registro a la main
        Button brRtoP = findViewById(R.id.brRtoP);
        brRtoP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NewUsuario.this, "Botón presionado", Toast.LENGTH_LONG).show();
                Intent intRMain = new Intent(NewUsuario.this, MainActivity.class);
                startActivity(intRMain);
            }
        });

        //Accion del boton para guardar el registro del nuevo usuario
        Button brGuar = findViewById(R.id.brGuar);
        brGuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Guardar datos en base
                guardarUsuario("http://10.116.133.114:80/lothgarder/insertarU.php");
                //Cambiar la IP si se reinicia la computadora

                /*
                Prueba de uso de editText
                String caNom = edNom.getText().toString();
                String caEdad = edEdad.getText().toString();
                String caCor = edCor.getText().toString();
                String caCon = edCon.getText().toString();*/

                //Toast.makeText(NewUsuario.this, "Usuario Guardado", Toast.LENGTH_LONG).show();
                /*Intent intRGua = new Intent(NewUsuario.this, PerfilUs.class);
                intRGua.putExtra("Nombre", caNom);
                intRGua.putExtra("Edad", caEdad);
                intRGua.putExtra("Correo", caCor);
                intRGua.putExtra("Contra", caCon);

                startActivity(intRGua);*/
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    //Metodo para guardar el usuario
    private void guardarUsuario(String URL){
        //Creacion de la peticion al servidor con el metodo POST
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Respuesta del servidor: " + response, Toast.LENGTH_LONG).show();
                EditText edError = findViewById(R.id.error);
                edError.setText(response);
                //Toast.makeText(getApplicationContext(), "Operación exitosa",Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(),Toast.LENGTH_LONG).show();
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                //Definicion local de editText
                EditText edNom = findViewById(R.id.edrNom);
                EditText edEdad = findViewById(R.id.edrEdad);
                EditText edCor = findViewById(R.id.edrCor);
                EditText edCon = findViewById(R.id.edrCon);
                String diasUso = "1";

                //Metodo Map que manda los datos de la peticion al servidor extrallendo informacion del editText para guardar en la base
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("correo", edCor.getText().toString());
                parametros.put("nombre", edNom.getText().toString());
                parametros.put("edad", edEdad.getText().toString());
                parametros.put("contrasena", edCon.getText().toString());
                parametros.put("tiempo", diasUso);

                return parametros;
                //return super.getParams();
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

    /*
    Funcion de boton provicional para buscar info de usuario
    private void buscarUsuario(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int reco = 0; reco < response.length(); reco++) {
                    try {
                        jsonObject = response.getJSONObject(reco);
                        eNomPU.setText(jsonObject.getString("nombre"));
                        eEdadPU.setText(jsonObject.getString("edad"));
                        eCorrPU.setText(jsonObject.getString("correo"));
                        eContraPU.setText(jsonObject.getString("contraseña"));
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "ERROR DE CONEXIÓN", Toast.LENGTH_LONG).show();
            }
        }
        );

        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);

    }*/

}