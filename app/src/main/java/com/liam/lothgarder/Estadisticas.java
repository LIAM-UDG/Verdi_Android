package com.liam.lothgarder;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Estadisticas extends AppCompatActivity {

    private TextView etEsHumedadV, etEsTempV, etEsAguaV, etEsTotalPV;
    private Button btnEsActuali, btnEstoP;

    private String link_domain, idSis, correo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas);

        link_domain = getString(R.string.link_domain);

        //Definicion local de variables
        SharedPreferences preferencesU = getSharedPreferences("guardarSesion", Context.MODE_PRIVATE);
        SharedPreferences preferencesS = getSharedPreferences("guardarSistema", Context.MODE_PRIVATE);
        idSis = preferencesS.getString("ID", "");
        correo = preferencesU.getString("Correo", "");

        obtenerEstadisticasDesdeServidor(link_domain + "?accion=buscarEst" + "&idS=" + idSis + "&correo=" + correo);

        //Accion de boton para actualizar las estadisticas
        btnEsActuali = findViewById(R.id.btnEsActuali);
        btnEsActuali.setOnClickListener(v -> {
            Toast.makeText(this, "Actualizando estadísticas...", Toast.LENGTH_SHORT).show();

            obtenerEstadisticasDesdeServidor(link_domain + "?accion=buscarEst" + "&idS=" + idSis + "&correo=" + correo);
        });

        // Botón Regresar
        btnEstoP = findViewById(R.id.btnEstoP);
        btnEstoP.setOnClickListener(v -> {
            Toast.makeText(this, "Regresando...", Toast.LENGTH_SHORT).show();
            finish();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void obtenerEstadisticasDesdeServidor(String URL) {

        etEsHumedadV = findViewById(R.id.etEsHumedadV);
        etEsTempV = findViewById(R.id.etEsTempV);
        etEsAguaV = findViewById(R.id.etEsAguaV);
        etEsTotalPV = findViewById(R.id.etEsTotalPV);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int reco = 0; reco < response.length(); reco++) {
                    try {
                        jsonObject = response.getJSONObject(reco);
                        etEsHumedadV.setText(jsonObject.getString("Humedad"));
                        etEsTempV.setText(jsonObject.getString("Temperatura"));
                        etEsAguaV.setText(jsonObject.getString("AguaDia"));
                        etEsTotalPV.setText(jsonObject.getString("Plantas"));
                        Toast.makeText(Estadisticas.this, "Datos actualizados", Toast.LENGTH_SHORT).show();

                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Para ver error en cado de fallo
                Log.e("Volley", "Error: " + error.toString());
                //error.printStackTrace();
                Toast.makeText(getApplicationContext(), "ERROR DE CONEXIÓN", Toast.LENGTH_SHORT).show();
            }
        }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
}