package com.liam.lothgarder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class SugerirP extends AppCompatActivity {

    RequestQueue requestQueue;
    ImageView imgSPPlanta;
    Button btnSPSubFoto;
    EditText edSPNombre;
    EditText edSPDescrip;
    Button btnSPConti;
    Button btnSPtoP;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sugerir_p);

        // Inicializar los elementos del layout
        imgSPPlanta = findViewById(R.id.imgSPPlanta);
        btnSPSubFoto = findViewById(R.id.btnSPSubFoto);
        edSPNombre = findViewById(R.id.edSPNombre);
        edSPDescrip = findViewById(R.id.edSPDescrip);
        btnSPConti = findViewById(R.id.btnSPConti);
        btnSPtoP = findViewById(R.id.btnSPtoP);

        // Listener para el botón Subir Foto
        btnSPSubFoto.setOnClickListener(v -> {
            Toast.makeText(this, "Funcionalidad de subir foto proximamente", Toast.LENGTH_SHORT).show();
            // Aquí podrías implementar la selección de una foto en el futuro
        });

        // Listener para el botón Continuar
        btnSPConti.setOnClickListener(v -> {
            String nombre = edSPNombre.getText().toString().trim();
            String descripcion = edSPDescrip.getText().toString().trim();

            // Validar que los campos no estén vacíos
            if (nombre.isEmpty()) {
                edSPNombre.setError("Por favor, ingresa el nombre de la planta");
                edSPNombre.requestFocus();
                return;
            }
            if (descripcion.isEmpty()) {
                edSPDescrip.setError("Por favor, ingresa una descripción");
                edSPDescrip.requestFocus();
                return;
            }

            // Mostrar un mensaje con los datos ingresados
            Toast.makeText(this, "Planta sugerida:\nNombre: " + nombre + "\nDescripción: " + descripcion, Toast.LENGTH_LONG).show();

            //Extraccion del link de dominio desde strings.xml
            String link_domain = getString(R.string.link_domain);

            sugerirP(link_domain + "?accion=insertarSug");

            // Pasar los datos a la pantalla Plantas y regresar
            Intent intent = new Intent(SugerirP.this, Enci.class);
            startActivity(intent);
            finish();
        });

        // Listener para el botón Atrás
        btnSPtoP.setOnClickListener(v -> {
            Toast.makeText(this, "Regresando...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SugerirP.this, PantallaPrin.class);
            startActivity(intent);
            finish(); // Cierra la actividad actual
        });



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void sugerirP(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Respuesta del servidor: " + response, Toast.LENGTH_LONG).show();
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
                EditText edNombre = findViewById(R.id.edSPNombre);
                EditText edDescripcion = findViewById(R.id.edSPDescrip);

                //Metodo Map que manda los datos de la peticion al servidor extrallendo informacion del editText para guardar en la base
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("nombre", edNombre.getText().toString());
                parametros.put("descripcion", edDescripcion.getText().toString());

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


}