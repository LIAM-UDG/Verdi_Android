package com.liam.lothgarder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

public class LogrosP extends AppCompatActivity {

    private int numeroPs;
    private ImageView imgLPLog1, imgLPLog2, imgLPLog3, imgLPLog4;
    private TextView etLPTitN1, etLPTitN2, etLPTitN3, etLPTitN4,
            etLPBarritaN1, etLPBarritaN2, etLPBarritaN3, etLPBarritaN4,
            etLPTextN1, etLPTextN2, etLPTextN3, etLPTextN4,
            etLPLockN1, etLPLockN2, etLPLockN3, etLPLockN4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_logros_p);

        numeroPs = 0;

        imgLPLog1 = findViewById(R.id.imgLPLog1);
        imgLPLog2 = findViewById(R.id.imgLPLog2);
        imgLPLog3 = findViewById(R.id.imgLPLog3);
        imgLPLog4 = findViewById(R.id.imgLPLog4);

        etLPBarritaN1 = findViewById(R.id.etLPBarritaN1);
        etLPBarritaN2 = findViewById(R.id.etLPBarritaN2);
        etLPBarritaN3 = findViewById(R.id.etLPBarritaN3);
        etLPBarritaN4 = findViewById(R.id.etLPBarritaN4);

        etLPTitN1 = findViewById(R.id.etLPTitN1);
        etLPTitN2 = findViewById(R.id.etLPTitN2);
        etLPTitN3 = findViewById(R.id.etLPTitN3);
        etLPTitN4 = findViewById(R.id.etLPTitN4);

        etLPTextN1 = findViewById(R.id.etLPTextN1);
        etLPTextN2 = findViewById(R.id.etLPTextN2);
        etLPTextN3 = findViewById(R.id.etLPTextN3);
        etLPTextN4 = findViewById(R.id.etLPTextN4);

        etLPLockN1 = findViewById(R.id.etLPLockN1);
        etLPLockN2 = findViewById(R.id.etLPLockN2);
        etLPLockN3 = findViewById(R.id.etLPLockN3);
        etLPLockN4 = findViewById(R.id.etLPLockN4);

        // Obtener correo del usuario desde SharedPreferences
        SharedPreferences preferences = getSharedPreferences("guardarSesion", Context.MODE_PRIVATE);
        String correoU= preferences.getString("Correo", "");

        String link_domain = getString(R.string.link_domain);

        buscarPlantas(link_domain + "?accion=buscarPs&correo=" + correoU);

        Button blpLptoPrin = findViewById(R.id.blpLptoPrin);
        blpLptoPrin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intLptoPrin = new Intent(LogrosP.this, PantallaPrin.class);
                intLptoPrin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intLptoPrin);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void buscarPlantas(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL, null,
                response -> {
                    //Obtención de la cantidad total directamente del tamaño del arreglo JSON
                    numeroPs = response.length();
                    //Llamado a la función para actualizar logros
                    actualizarLogros(numeroPs);
                },
                error -> Toast.makeText(this, "Error al conectar: " + error.getMessage(), Toast.LENGTH_SHORT).show()
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void actualizarLogros(int numeroPs) {
        // Nivel 1: 1 Planta
        if (numeroPs >= 1) {
            imgLPLog1.setVisibility(View.VISIBLE);
            etLPTitN1.setVisibility(View.VISIBLE);
            etLPTextN1.setVisibility(View.VISIBLE);
            etLPBarritaN1.setVisibility(View.VISIBLE);
            etLPLockN1.setVisibility(View.INVISIBLE);
        }

        // Nivel 2: 5 Plantas
        if (numeroPs >= 5) {
            imgLPLog2.setVisibility(View.VISIBLE);
            etLPTitN2.setVisibility(View.VISIBLE);
            etLPTextN2.setVisibility(View.VISIBLE);
            etLPBarritaN2.setVisibility(View.VISIBLE);
            etLPLockN2.setVisibility(View.INVISIBLE);
        }

        // Nivel 3: 25 Plantas
        if (numeroPs >= 25) {
            imgLPLog3.setVisibility(View.VISIBLE);
            etLPTitN3.setVisibility(View.VISIBLE);
            etLPTextN3.setVisibility(View.VISIBLE);
            etLPBarritaN3.setVisibility(View.VISIBLE);
            etLPLockN3.setVisibility(View.INVISIBLE);
        }

        // Nivel 4: 50 Plantas
        if (numeroPs >= 50) {
            imgLPLog4.setVisibility(View.VISIBLE);
            etLPTitN4.setVisibility(View.VISIBLE);
            etLPTextN4.setVisibility(View.VISIBLE);
            etLPBarritaN4.setVisibility(View.VISIBLE);
            etLPLockN4.setVisibility(View.INVISIBLE);
        }
    }

}