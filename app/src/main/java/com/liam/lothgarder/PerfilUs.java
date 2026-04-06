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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.widget.ImageView;

public class PerfilUs extends AppCompatActivity {

    //Definicion de variables globales
    private String contrasenaReal = "";
    private boolean isPasswordVisible = false;
    private TextView euContraPU;
    private Button buUtoPrin;
    private ImageView ivToggleContra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_perfil_us);

        euContraPU = findViewById(R.id.euContraPU);
        ivToggleContra = findViewById(R.id.ivToggleContra);

        //Creacion de la instancia de las preferencias(informacion de sesion)
        SharedPreferences preferences = getSharedPreferences("guardarSesion", Context.MODE_PRIVATE);

        //Extraccion del link de dominio desde strings.xml
        String link_domain = getString(R.string.link_domain);
        //Llamado a la funcion para buscar el usuario y mostrar su perfil
        buscarUsuario(link_domain + "?accion=buscarU&correo=" + preferences.getString("Correo",""));

        //Accion de Boton para salir de Perfil Usuario a la Pantalla Principal
        buUtoPrin = findViewById(R.id.buUtoPrin);
        buUtoPrin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PerfilUs.this, "Botón presionado", Toast.LENGTH_LONG).show();
                Intent intRtoPrin = new Intent(PerfilUs.this, PantallaPrin.class);
                startActivity(intRtoPrin);
                finish();
            }
        });

        //Lógica para el clic del icono de contraseña y alternar
        ivToggleContra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    //FUncion para buscar usuario
    private void buscarUsuario(String URL) {

        // Los TextViews que no son de la contraseña pueden seguir siendo locales
        TextView eNomPU = findViewById(R.id.euNomPU);
        TextView eEdadPU = findViewById(R.id.euEdadPU);
        TextView eCorrPU = findViewById(R.id.euCorrPU);
        //El textview de contraseña en global para ocultar y mostrar

        //Creacion de la peticion
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int reco = 0; reco < response.length(); reco++) {
                    try {
                        jsonObject = response.getJSONObject(reco);
                        eCorrPU.setText(jsonObject.getString("Correo"));
                        eNomPU.setText(jsonObject.getString("Nombre"));
                        eEdadPU.setText(jsonObject.getString("Edad"));

                        //Guardar la contraseña real antes de ocultarla
                        contrasenaReal = jsonObject.getString("Contrasena");

                        // Ocultar la contraseña por defecto
                        mostrarContrasenaOculta();

                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "ERROR DE CONEXIÓN", Toast.LENGTH_SHORT).show();
            }
        }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            // Si está visible, la ocultamos
            mostrarContrasenaOculta();
        } else {
            // Si está oculta, la mostramos
            mostrarContrasenaVisible();
        }
        // Invertimos el estado después de la acción
        isPasswordVisible = !isPasswordVisible;
    }

    private void mostrarContrasenaVisible() {
        // Muestra el valor de la variable real
        euContraPU.setText(contrasenaReal);
        // Cambia el icono a "ojo cerrado" (significa que al hacer clic se ocultará)
        ivToggleContra.setImageResource(R.drawable.ic_visibility_off);
    }

    private void mostrarContrasenaOculta() {
        // Crea una cadena de asteriscos para ocultar el texto
        char[] chars = new char[contrasenaReal.length()];
        java.util.Arrays.fill(chars, '•'); // Usar un punto o asterisco para ocultar
        euContraPU.setText(new String(chars));

        // Cambia el icono a "ojo abierto" (significa que al hacer clic se mostrará)
        ivToggleContra.setImageResource(R.drawable.ic_visibility);
    }

}