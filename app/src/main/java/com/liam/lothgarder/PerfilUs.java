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

    private String contrasenaReal = "";
    private boolean isPasswordVisible = false;
    private TextView euContraPU; // Hacemos global el TextView de la contraseña
    private ImageView ivToggleContra; // Nuevo: El icono de ojo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_perfil_us);

        euContraPU = findViewById(R.id.euContraPU);
        ivToggleContra = findViewById(R.id.ivToggleContra);

        //Creacion de la instancia de las preferencias(informacion de sesion)
        SharedPreferences preferences = getSharedPreferences("guardarSesion", Context.MODE_PRIVATE);

        //Llamada al metodo de buscar usuario para mostrar su informacion usando la info de la prefencia de correo
        //buscarUsuario("http://192.168.137.128:80/lothgarder/buscarU.php?correo="+preferences.getString("Correo",""));

        buscarUsuario("https://app-d9fd7517-b3e4-4e1e-8fba-66483bfb6711.cleverapps.io//?accion=buscarU&correo=" + preferences.getString("Correo",""));

        //Accion de Boton para salir de Perfil Usuario a la Pantalla Principal
        Button buUtoPrin = findViewById(R.id.buUtoPrin);
        buUtoPrin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PerfilUs.this, "Botón presionado", Toast.LENGTH_LONG).show();
                Intent intRtoPrin = new Intent(PerfilUs.this, PantallaPrin.class);
                startActivity(intRtoPrin);
            }
        });

        // 2. NUEVO: Lógica para el clic del icono de alternar
        ivToggleContra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility();
            }
        });

        /*
        PRUEBAS/PROVICIONALES
        Button buBus = findViewById(R.id.buBus);
        buBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView eNomPU = findViewById(R.id.euNomPU);
                EditText edCorr = findViewById(R.id.edCorr);

                Toast.makeText(PerfilUs.this, "Buscando usuario", Toast.LENGTH_LONG).show();
                SharedPreferences preferences = getSharedPreferences("guardarSesion", Context.MODE_PRIVATE);

                buscarUsuario("http://10.116.133.114:80/lothgarder/buscarU.php?correo="+preferences.getString("Correo",""));
                //buscarUsuario("http://10.0.2.2/lothgarder/buscar.php?correo="+edCorr.getText().toString());
            }
        });

        Modo de variable
        TextView eNomPU = findViewById(R.id.eNomPU);
        TextView eEdadPU = findViewById(R.id.eEdadPU);
        TextView eCorrPU = findViewById(R.id.eCorrPU);
        TextView eContraPU = findViewById(R.id.eContraPU);

        String nombrePU = getIntent().getExtras().getString("Nombre");
        String edadPU = getIntent().getExtras().getString("Edad");
        String correoPU = getIntent().getExtras().getString("Correo");
        String contraPU = getIntent().getExtras().getString("Contra");

        eNomPU.setText(nombrePU);
        eEdadPU.setText(edadPU);
        eCorrPU.setText(correoPU);
        eContraPU.setText(contraPU);*/

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    //Metodo para buscar usuario
    //Metodo para buscar usuario (MODIFICADO)
    private void buscarUsuario(String URL) {

        // Los TextViews que no son de la contraseña pueden seguir siendo locales
        TextView eNomPU = findViewById(R.id.euNomPU);
        TextView eEdadPU = findViewById(R.id.euEdadPU);
        TextView eCorrPU = findViewById(R.id.euCorrPU);
        // euContraPU y ivToggleContra ya están enlazados globalmente

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

                        // 3. CLAVE: Guardar la contraseña real antes de ocultarla
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
                // ... (código existente)
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