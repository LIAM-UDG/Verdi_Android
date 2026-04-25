package com.liam.lothgarder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Busqueda extends AppCompatActivity {

    private Spinner spnBuFiltro;

    private RecyclerView recyclerView;
    private riegoAdapter adapter;
    private List<riego> listaRiego;

    private Button btnButoP, btnBuaAct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda);

        recyclerView = findViewById(R.id.rvBuInfo);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listaRiego = new ArrayList<>();
        adapter = new riegoAdapter(listaRiego);
        recyclerView.setAdapter(adapter);

        spnBuFiltro = findViewById(R.id.spnBuFiltro);
        spnBuFiltro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedOption = parent.getItemAtPosition(position).toString();
                if (!selectedOption.equals("Filtro")) {
                    Toast.makeText(Busqueda.this, "Filtro seleccionado: " + selectedOption, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //Accion de Botón de regreso
        btnButoP = findViewById(R.id.btnButoP);
        btnButoP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intBtoPrin = new Intent(Busqueda.this, PantallaPrin.class);
                intBtoPrin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intBtoPrin);
            }
        });

        btnBuaAct = findViewById(R.id.btnBuaAct);
        btnBuaAct.setOnClickListener(v -> {
            String filtroSeleccionado = spnBuFiltro.getSelectedItem().toString();

            if (filtroSeleccionado.equals("Filtro")) {
                Toast.makeText(Busqueda.this, "Selecciona un filtro primero", Toast.LENGTH_SHORT).show();
                return;
            }

            if (filtroSeleccionado.equals("Notificación de Riego")) {

                // Obtener correo del usuario desde SharedPreferences
                SharedPreferences preferences = getSharedPreferences("guardarSistema", Context.MODE_PRIVATE);
                String sistemaU= preferences.getString("ID", "");

                String link_domain = getString(R.string.link_domain);

                obtenerInfoR(link_domain + "?accion=buscarRiegos&idS=" + sistemaU);
            }
        });
    }

    private void obtenerInfoR(String URL) {
        JsonArrayRequest request = new JsonArrayRequest(URL,
                response -> {

                    List<riego> nuevaLista = new ArrayList<>();

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);

                            int idPu = obj.getInt("ID_Planta");
                            String fechaR = obj.getString("FechaRiego");
                            int segR = obj.getInt("Duracion");
                            double aguaR = obj.getDouble("CantidadML");

                            nuevaLista.add(
                                    new riego(idPu, fechaR, segR, aguaR)
                            );

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    Collections.sort(nuevaLista,
                            (a, b) -> Integer.compare(a.getIdPu(), b.getIdPu()));

                    adapter.actualizarLista(nuevaLista);

                },
                error -> Toast.makeText(this, "Error al obtener datos", Toast.LENGTH_SHORT).show()
        );

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}
