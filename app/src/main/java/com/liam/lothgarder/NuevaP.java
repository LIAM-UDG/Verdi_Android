package com.liam.lothgarder;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class NuevaP extends AppCompatActivity {

    RequestQueue requestQueue;
    ImageView imgPlanta;
    Button btnSubirFoto, btnContinuar, btnSeleccionarPlanta;
    EditText etNombre;
    Spinner spnAmbiente; // aunque no está en tabla, lo agrego por XML

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_nueva_p);

        // Vistas
        imgPlanta = findViewById(R.id.imgPlanta);
        btnSubirFoto = findViewById(R.id.btnSubirFoto);
        btnContinuar = findViewById(R.id.btnContinuar);
        btnSeleccionarPlanta = findViewById(R.id.btnSeleccionarPlanta);
        etNombre = findViewById(R.id.etNombre);
        spnAmbiente = findViewById(R.id.spnAmbiente);

        Toast.makeText(NuevaP.this, "Pantalla nueva planta", Toast.LENGTH_LONG).show();

        btnSubirFoto.setOnClickListener(v -> {
            Toast.makeText(NuevaP.this, "Funcionalidad subir foto pendiente", Toast.LENGTH_SHORT).show();
            // Aquí agregarás el selector de imagen o cámara
        });

        btnSeleccionarPlanta.setOnClickListener(v -> {
            Toast.makeText(NuevaP.this, "Funcionalidad seleccionar planta pendiente", Toast.LENGTH_SHORT).show();
            // Aquí puedes mostrar un diálogo para seleccionar planta, si quieres
        });

        btnContinuar.setOnClickListener(v -> {
            guardarPlanta("http://192.168.1.104:80/lothgarder/insertarP.php");
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void guardarPlanta(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, response -> {
            Toast.makeText(getApplicationContext(), "Respuesta del servidor: " + response, Toast.LENGTH_LONG).show();

            // Podrías limpiar campos o cerrar actividad
            Intent intRtoMain = new Intent(NuevaP.this, MainActivity.class);
            startActivity(intRtoMain);

        }, error -> Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show()) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();

                String nombre = etNombre.getText().toString().trim();

                // Por ahora no tienes descripción, cuidados ni uso en el XML,
                // si quieres agregar esos campos, crea EditText y recupéralos aquí.
                // En este ejemplo, sólo mando nombre

                parametros.put("nombre", nombre);

                // Por ejemplo, podrías enviar un campo vacío o default para los demás:
                parametros.put("descripcion", "");
                parametros.put("cuidados", "");
                parametros.put("uso", "");

                // Imagen y ambiente no están enviándose en esta versión.

                return parametros;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}

