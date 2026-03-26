package com.liam.lothgarder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
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

import java.util.HashMap;
import java.util.Map;


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

                // Definición de campos
                EditText edNom = findViewById(R.id.edrNom);
                EditText edCor = findViewById(R.id.edrCor);
                EditText edCon = findViewById(R.id.edrCon);
                String nombre = edNom.getText().toString().trim();
                String correo = edCor.getText().toString().trim();
                String contrasena = edCon.getText().toString();

                // --- VALIDACIÓN DE CONTRASEÑA CON REGEX ---

                // Patrón Regex: Mínimo 8 caracteres, al menos 1 mayúscula, 1 minúscula, 1 número y 1 especial.
                String patronC = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*-]).{8,}$";
                //Cada para evaluar el nombre valido
                String nombreV = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ][a-zA-ZáéíóúÁÉÍÓÚñÑ ]{1,99}$";

                if (!nombre.matches(nombreV)) {
                    Toast.makeText(NewUsuario.this, "Nombre inválido.", Toast.LENGTH_LONG).show();
                    edNom.setError("Nombre inválido solo se permiten letras");
                    return;
                }

                // --- VALIDACIÓN DE CORREO ---
                if (correo.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
                    Toast.makeText(NewUsuario.this, "Introduce un correo electrónico válido.", Toast.LENGTH_LONG).show();
                    edCor.setError("Correo inválido");
                    return;
                }

                if (!contrasena.matches(patronC)) {
                    new androidx.appcompat.app.AlertDialog.Builder(NewUsuario.this)
                            .setTitle("Contraseña poco segura")
                            .setMessage("La contraseña debe tener:\n\n" +
                                    "• Mínimo 8 caracteres.\n" +
                                    "• Al menos 1 mayúscula y 1 minúscula.\n" +
                                    "• Al menos 1 número.\n" +
                                    "• Al menos 1 carácter especial (!@#$%^&*).")
                            .setPositiveButton("Aceptar", null)
                            .show();
                    /*String mensajeError = "La contraseña debe tener:\n" +
                            " - Mínimo 8 caracteres.\n" +
                            " - Al menos 1 mayúscula y 1 minúscula.\n" +
                            " - Al menos 1 número.\n" +
                            " - Al menos 1 caracter especial como: (!@#$%^&*).";

                    Toast.makeText(NewUsuario.this, mensajeError, Toast.LENGTH_LONG).show();*/
                    edCon.setError("Contraseña débil");
                    return; // Detiene la ejecución
                }

                //Extraccion del link de dominio desde strings.xml
                String link_domain = getString(R.string.link_domain);

                //Guardar usuario base en la nube
                guardarUsuario(link_domain + "?accion=insertarU");

                /*
                Servidor local
                guardarUsuario("http://192.168.137.128:80/lothgarder/insertarU.php");
                */

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
                intRGua.putExtra("Edad", caEdad);2
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
                //Pasar a pantalla de ingresar usuario tras registrar
                Intent intRtoP = new Intent(NewUsuario.this, InUsuario.class);
                startActivity(intRtoP);
                //Toast.makeText(getApplicationContext(), "Operación exitosa",Toast.LENGTH_LONG).show();

                finish();
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

                //Definicion local de editText
                EditText edNom = findViewById(R.id.edrNom);
                EditText edEdad = findViewById(R.id.edrEdad);

                EditText edCor = findViewById(R.id.edrCor);

                EditText edCon = findViewById(R.id.edrCon);

                //Metodo Map que manda los datos de la peticion al servidor extrallendo informacion del editText para guardar en la base
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("correo", edCor.getText().toString());
                parametros.put("nombre", edNom.getText().toString());
                parametros.put("edad", edEdad.getText().toString());
                parametros.put("contrasena", edCon.getText().toString());

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