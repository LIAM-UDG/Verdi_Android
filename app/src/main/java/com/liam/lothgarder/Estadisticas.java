package com.liam.lothgarder;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

public class Estadisticas extends AppCompatActivity {

    private RequestQueue requestQueue;
    private TextView valueHumedad, valueEstado, valueTemperatura, valueAgua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas);

        // Inicializar vistas
        valueHumedad = findViewById(R.id.valueHumedad);
        valueEstado = findViewById(R.id.valueEstado);
        valueTemperatura = findViewById(R.id.valueTemperatura);
        valueAgua = findViewById(R.id.valueAgua);

        // Inicializar RequestQueue de Volley
        requestQueue = Volley.newRequestQueue(this);

        // Botón para actualizar estadísticas (inspirado en los botones de NewUsuario)
        Button btnActualizar = findViewById(R.id.btnActualizar);
        if (btnActualizar != null) {
            btnActualizar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(Estadisticas.this, "Actualizando estadísticas...", Toast.LENGTH_SHORT).show();
                    obtenerEstadisticasDesdeServidor();
                }
            });
        }

        // Botón para regresar (inspirado en brRtoP de NewUsuario)
        Button btnRegresar = findViewById(R.id.btnRegresar);
        if (btnRegresar != null) {
            btnRegresar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(Estadisticas.this, "Regresando...", Toast.LENGTH_SHORT).show();
                    finish(); // Regresa a la actividad anterior
                }
            });
        }

        // Manejar insets de la ventana para pantalla edge-to-edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            WindowInsetsCompat systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Cargar datos iniciales simulados
        cargarDatosEstadisticas();
    }

    private void cargarDatosEstadisticas() {
        // Datos simulados
        valueHumedad.setText("50%");
        valueEstado.setText("Saludable");
        valueTemperatura.setText("25°C");
        valueAgua.setText("250 ml");
    }

    private void obtenerEstadisticasDesdeServidor() {
        String url = "http://x.x.x.x:80/lothgarder/get_estadisticas.php"; // Reemplazar con tu IP real
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            valueHumedad.setText(jsonObject.getString("humedad"));
                            valueEstado.setText(jsonObject.getString("estado"));
                            valueTemperatura.setText(jsonObject.getString("temperatura"));
                            valueAgua.setText(jsonObject.getString("agua"));
                            Toast.makeText(Estadisticas.this, "Datos actualizados", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            Toast.makeText(Estadisticas.this, "Error al analizar datos: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Estadisticas.this, "Error de conexión: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        // Agregar la solicitud a la cola con política de reintento
        stringRequest.setRetryPolicy(new com.android.volley.DefaultRetryPolicy(
                10000, // Tiempo de espera en ms
                com.android.volley.DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                com.android.volley.DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }
}