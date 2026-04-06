package com.liam.lothgarder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class InUsuario extends AppCompatActivity {

    //Definicion de variables globales
    private EditText ediCorreo, ediContra;
    private Button biItoP,biCon;
    private String correo,contra;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_in_usuario);

        //Paso de la componentes de xml a java
        ediCorreo = findViewById(R.id.ediCorreo);
        ediContra = findViewById(R.id.ediContra);
        biCon = findViewById(R.id.biCon);

        //Busqueda de sesion
        recuperarSesion();

        //Accion del boton para validar el usuario
        biCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Captura de valores del editText a las varibles(string) globales
                correo = ediCorreo.getText().toString();
                contra = ediContra.getText().toString();

                //Validacion de que ninguno de los campos este vacio
                if (correo.isEmpty() || contra.isEmpty()) {
                    Toast.makeText(InUsuario.this, "Debes ingresar ambos campos", Toast.LENGTH_SHORT).show();
                    if(correo.isEmpty()) ediCorreo.setError("Campo obligatorio");
                    if(contra.isEmpty()) ediContra.setError("Campo obligatorio");
                    return;
                }

                //Extraccion del link de dominio desde strings.xml
                String link_domain = getString(R.string.link_domain);

                //Llamada a la funcion de validar usuario
                validarUsuario(link_domain + "?accion=validarU");


            }
        });

        //Accion del boton para volver al inicio
        biItoP = findViewById(R.id.biItoP);
        biItoP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intRMain = new Intent(InUsuario.this, MainActivity.class);
                startActivity(intRMain);
                finish();
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    //Funcion para validar el usuario
    private void validarUsuario(String URL) {
        //Creacion de la peticion al servidor con el metodo POST
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    //Creacion de un objeto JSON con la respuesta del servidor
                    JSONObject jsonObject = new JSONObject(response);
                    String estado = jsonObject.getString("estado");
                    //Segun la respuesta del servidor (en la validacion del usuario)
                    switch (estado) {
                        case "Ok":
                            guardarSesion();
                            String nombre = jsonObject.getString("nombre");
                            Toast.makeText(InUsuario.this, "Bienvenido " + nombre, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), PantallaPrin.class));
                            finish();
                            break;

                        case "Correo_Incorrecto":
                            Toast.makeText(InUsuario.this, "Correo incorrecto", Toast.LENGTH_SHORT).show();
                            ediCorreo.setError("Correo Incorrecto");
                            break;

                        case "Contrasena_Incorrecta":
                            Toast.makeText(InUsuario.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                            ediContra.setError("Contraseña incorrecta");
                            break;

                        default:
                            Toast.makeText(InUsuario.this, "Error desconocido", Toast.LENGTH_SHORT).show();
                            break;
                    }

                } catch (JSONException e) {
                    Toast.makeText(InUsuario.this, "Error en respuesta del servidor", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(InUsuario.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Nullable
            @Override
           //Metodo Map que manda los datos de la peticion al servidor
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String,String>();
                parametros.put("correo", correo);
                parametros.put("contrasena", contra);

                return parametros;
            }
        };

        RequestQueue requestQueue = com.android.volley.toolbox.Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    //Metodo para guardar la sesion
    private void guardarSesion() {
        SharedPreferences preferences = getSharedPreferences("guardarSesion", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Correo", correo);
        editor.putString("Contrasena", contra);
        editor.putBoolean("Sesion",true);
        editor.commit();

    }

    //Metodo para recuperar la sesion
    private void recuperarSesion() {
        SharedPreferences preferences = getSharedPreferences("guardarSesion", Context.MODE_PRIVATE);
        ediCorreo.setText(preferences.getString("Correo","ejemplocorreo@gmail.com"));
        ediContra.setText(preferences.getString("Contrasena","contraseña"));
        //Local -> ediContra.setText(preferences.getString("Contraseña","contraseña"));
    }

}