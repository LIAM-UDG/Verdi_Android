package com.liam.lothgarder;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Estadisticas extends AppCompatActivity {

    private TextView valueHumedad, valueEstado, valueTemperatura, valueAgua;
    private Button btnActualizar, btnRegresar;
    private RequestQueue requestQueue;
    private static final String BASE_URL = "http://x.x.x.x:80/lothgarder/get_estadisticas.php"; // Reemplaza con tu IP real

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas);

        initViews();
        initData();
        setupListeners();
    }

    private void initViews() {
        valueHumedad = findViewById(R.id.valueHumedad);
        valueEstado = findViewById(R.id.valueEstado);
        valueTemperatura = findViewById(R.id.valueTemperatura);
        valueAgua = findViewById(R.id.valueAgua);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnRegresar = findViewById(R.id.btnRegresar);
    }

    private void initData() {
        // Inicializar RequestQueue de Volley
        requestQueue = Volley.newRequestQueue(this);
        // Cargar datos por defecto inicialmente
        cargarDatosPorDefecto();
        // Obtener datos del servidor
        obtenerEstadisticasDesdeServidor();
    }

    private void setupListeners() {
        // Botón Actualizar
        btnActualizar.setOnClickListener(v -> {
            Toast.makeText(this, "Actualizando estadísticas...", Toast.LENGTH_SHORT).show();
            obtenerEstadisticasDesdeServidor();
        });

        // Botón Regresar
        btnRegresar.setOnClickListener(v -> {
            Toast.makeText(this, "Regresando...", Toast.LENGTH_SHORT).show();
            finish();
        });

        // Manejar insets de la ventana para pantalla edge-to-edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            // Obtener los insets del sistema (barras de estado y navegación)
            androidx.core.graphics.Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            // Aplicar padding usando los valores de los insets
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void obtenerEstadisticasDesdeServidor() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, BASE_URL,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        // Usar optString para manejar campos faltantes
                        valueHumedad.setText(jsonObject.optString("humedad", "No disponible"));
                        valueEstado.setText(jsonObject.optString("estado", "No disponible"));
                        valueTemperatura.setText(jsonObject.optString("temperatura", "No disponible"));
                        valueAgua.setText(jsonObject.optString("agua", "No disponible"));
                        Toast.makeText(this, "Datos actualizados", Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        Toast.makeText(this, "Error al analizar datos: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        cargarDatosPorDefecto();
                    }
                },
                error -> {
                    Toast.makeText(this, "Error de connessione: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    cargarDatosPorDefecto();
                });

        // Configurar política de reintento
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000, // 10 segundos de tiempo de espera
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Agregar solicitud a la cola
        requestQueue.add(stringRequest);
    }

    private void cargarDatosPorDefecto() {
        // Datos por defecto en caso de error
        valueHumedad.setText("50%");
        valueEstado.setText("Saludable");
        valueTemperatura.setText("25°C");
        valueAgua.setText("250 ml");
    }
}