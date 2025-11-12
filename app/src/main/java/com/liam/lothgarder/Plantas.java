package com.liam.lothgarder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Plantas extends AppCompatActivity {

    planta plantaSeleccionada = null;
    RecyclerView recyclerView;
    adaptadorRecycler adapter;
    List<planta> listaPlantas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_plantas);

        recyclerView = findViewById(R.id.rvP);
        listaPlantas = new ArrayList<>();
        adapter = new adaptadorRecycler(listaPlantas, planta -> {
            plantaSeleccionada = planta;
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Obtener correo del usuario desde SharedPreferences
        SharedPreferences preferences = getSharedPreferences("guardarSesion", Context.MODE_PRIVATE);
        String correoU= preferences.getString("Correo", "");

        if (!correoU.isEmpty()) {
            buscarPlantas("https://app-d9fd7517-b3e4-4e1e-8fba-66483bfb6711.cleverapps.io//?accion=buscarPs&correo=" + correoU);
            /*
            Funcion local
            buscarPlantas("http://192.168.137.128:80/lothgarder/buscarPs.php?correo=" + correoU);
             */
        } else {
            Toast.makeText(this, "No se encontró el correo del usuario", Toast.LENGTH_SHORT).show();
        }

        Button bplNuP = findViewById(R.id.bplNuP);
        bplNuP.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(Plantas.this, "Avanzando a registrar planta...", Toast.LENGTH_SHORT).show();
                Intent intPltoPU = new Intent(Plantas.this, NuevaP.class);
                startActivity(intPltoPU);

                finish();
            }
        });

        Button bplSelP = findViewById(R.id.bplSelP);
        bplSelP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (plantaSeleccionada != null) {
                    // Aquí guardas o usas la planta seleccionada
                    Toast.makeText(Plantas.this, "Planta seleccionada: " + plantaSeleccionada.getNombre(), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Plantas.this, PlantaU.class);
                    intent.putExtra("nombre", plantaSeleccionada.getNombre());
                    startActivity(intent);

                    finish();

                } else {
                    Toast.makeText(Plantas.this, "Selecciona una planta primero", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button bplPltoPrin = findViewById(R.id.bplPltoPrin);
        bplPltoPrin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Plantas.this, "Regresando al inicio...", Toast.LENGTH_SHORT).show();
                Intent intPltoPrin = new Intent(Plantas.this, PantallaPrin.class);
                startActivity(intPltoPrin);

                finish();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void buscarPlantas(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL,
                response -> {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject plantaJSON = response.getJSONObject(i);
                            String nombre = plantaJSON.getString("nombre");
                            listaPlantas.add(new planta(nombre));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    adapter.notifyDataSetChanged();
                },
                error -> Toast.makeText(this, "Error al conectar con el servidor", Toast.LENGTH_SHORT).show()
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
}