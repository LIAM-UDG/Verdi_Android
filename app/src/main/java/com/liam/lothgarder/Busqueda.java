package com.liam.lothgarder;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Busqueda extends AppCompatActivity {


    private Spinner spinnerFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda);

        spinnerFilter = findViewById(R.id.spinner_filter);
        spinnerFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        // Botón de regreso
        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
