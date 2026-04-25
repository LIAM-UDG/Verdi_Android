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
    plantaAdapter adapter;
    List<planta> listaPlantas;
    Button btnPlsRegisP, btnPlsSelect, btnPlstoP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_plantas);

        recyclerView = findViewById(R.id.rvPlsPlanti);
        listaPlantas = new ArrayList<>();
        adapter = new plantaAdapter(listaPlantas, planta -> {
            plantaSeleccionada = planta;
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Obtener correo del usuario desde SharedPreferences
        SharedPreferences preferences = getSharedPreferences("guardarSesion", Context.MODE_PRIVATE);
        String correoU= preferences.getString("Correo", "");

        if (!correoU.isEmpty()) {
            //Extraccion del link de dominio desde strings.xml
            String link_domain = getString(R.string.link_domain);

            buscarPlantas(link_domain + "?accion=buscarPs&correo=" + correoU);
            /*
            Funcion local
            buscarPlantas("http://192.168.137.128:80/lothgarder/buscarPs.php?correo=" + correoU);
             */
        } else {
            Toast.makeText(this, "No se encontró el correo del usuario", Toast.LENGTH_SHORT).show();
        }

        btnPlsRegisP = findViewById(R.id.btnPlsRegisP);
        btnPlsRegisP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Plantas.this, "Avanzando a registrar planta...", Toast.LENGTH_SHORT).show();
                Intent intPltoPU = new Intent(Plantas.this, NuevaP.class);
                startActivity(intPltoPU);

                finish();
            }
        });

        btnPlsSelect = findViewById(R.id.btnPlsSelect);
        btnPlsSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (plantaSeleccionada != null) {
                    // Aquí guardas o usas la planta seleccionada
                    Toast.makeText(Plantas.this, "Planta seleccionada: " + plantaSeleccionada.getNombre(), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Plantas.this, PlantaU.class);
                    intent.putExtra("id", plantaSeleccionada.getId());
                    intent.putExtra("nombre", plantaSeleccionada.getNombre());
                    startActivity(intent);

                    finish();

                } else {
                    Toast.makeText(Plantas.this, "Selecciona una planta primero", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnPlstoP = findViewById(R.id.btnPlstoP);
        btnPlstoP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Plantas.this, "Regresando al inicio...", Toast.LENGTH_SHORT).show();
                Intent intPltoPrin = new Intent(Plantas.this, PantallaPrin.class);
                intPltoPrin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intPltoPrin);
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

                    //listaPlantas.clear();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject plantaJSON = response.getJSONObject(i);
                            String nombre = plantaJSON.getString("Nombre");
                            int id = plantaJSON.getInt("ID_InfoP");
                            listaPlantas.add(new planta(nombre, id));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    //Opcion para mostrar numero
                    listaPlantas.sort((p1, p2) -> Integer.compare(p1.getId(), p2.getId()));
                    adapter.notifyDataSetChanged();
                },
                error -> Toast.makeText(this, "Error al conectar con el servidor", Toast.LENGTH_SHORT).show()
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
}