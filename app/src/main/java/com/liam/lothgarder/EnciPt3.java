package com.liam.lothgarder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EnciPt3 extends AppCompatActivity {

    private int PKpl;
    private Button btnE3CAdan, btnE3Duranta, btnE3Abunda, btnE3CdeMoi, btnE3Hibisco,
            btnE3Malva, btnE3CdeCristo, btnE3HSanta, btnE3Millonaria, btnE3PCebra, btnE3toE2, btnE3Su;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_enci_pt3);

        // Botón Costilla de Adán
        btnE3CAdan = findViewById(R.id.btnE3CAdan);
        btnE3CAdan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intEtoCda = new Intent(EnciPt3.this, PlantaE.class);
                PKpl = 21;
                intEtoCda.putExtra("PKpl", PKpl);
                startActivity(intEtoCda);
            }
        });

        // Botón Duranta
        btnE3Duranta = findViewById(R.id.btnE3Duranta);
        btnE3Duranta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intEtoDur = new Intent(EnciPt3.this, PlantaE.class);
                PKpl = 22;
                intEtoDur.putExtra("PKpl", PKpl);
                startActivity(intEtoDur);
            }
        });

        // Botón Abundancia
        btnE3Abunda = findViewById(R.id.btnE3Abunda);
        btnE3Abunda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intEtoAbu = new Intent(EnciPt3.this, PlantaE.class);
                PKpl = 23;
                intEtoAbu.putExtra("PKpl", PKpl);
                startActivity(intEtoAbu);
            }
        });

        // Botón Cuna de moises
        btnE3CdeMoi = findViewById(R.id.btnE3CdeMoi);
        btnE3CdeMoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intEtoMoi = new Intent(EnciPt3.this, PlantaE.class);
                PKpl = 24;
                intEtoMoi.putExtra("PKpl", PKpl);
                startActivity(intEtoMoi);
            }
        });

        // Botón Hibisco
        btnE3Hibisco = findViewById(R.id.btnE3Hibisco);
        btnE3Hibisco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intEtoHib = new Intent(EnciPt3.this, PlantaE.class);
                PKpl = 25;
                intEtoHib.putExtra("PKpl", PKpl);
                startActivity(intEtoHib);
            }
        });

        // Botón Malva
        btnE3Malva = findViewById(R.id.btnE3Malva);
        btnE3Malva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intEtoMal = new Intent(EnciPt3.this, PlantaE.class);
                PKpl = 26;
                intEtoMal.putExtra("PKpl", PKpl);
                startActivity(intEtoMal);
            }
        });

        // Botón Corona de Cristo
        btnE3CdeCristo = findViewById(R.id.btnE3CdeCristo);
        btnE3CdeCristo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intEtoCdc = new Intent(EnciPt3.this, PlantaE.class);
                PKpl = 27;
                intEtoCdc.putExtra("PKpl", PKpl);
                startActivity(intEtoCdc);
            }
        });

        // Botón Hierba Santa
        btnE3HSanta = findViewById(R.id.btnE3HSanta);
        btnE3HSanta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intEtoHsa = new Intent(EnciPt3.this, PlantaE.class);
                PKpl = 28;
                intEtoHsa.putExtra("PKpl", PKpl);
                startActivity(intEtoHsa);
            }
        });

        // Botón Millonaria
        btnE3Millonaria = findViewById(R.id.btnE3Millonaria);
        btnE3Millonaria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intEtoMil = new Intent(EnciPt3.this, PlantaE.class);
                PKpl = 29;
                intEtoMil.putExtra("PKpl", PKpl);
                startActivity(intEtoMil);
            }
        });

        // Botón Planta Cebra
        btnE3PCebra = findViewById(R.id.btnE3PCebra);
        btnE3PCebra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intEtoCeb = new Intent(EnciPt3.this, PlantaE.class);
                PKpl = 30;
                intEtoCeb.putExtra("PKpl", PKpl);
                startActivity(intEtoCeb);
            }
        });

        // Botón Sugerir Planta
        btnE3Su = findViewById(R.id.btnE3Su);
        btnE3Su.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intEtoSp = new Intent(EnciPt3.this, SugerirP.class);
                startActivity(intEtoSp);
                Toast.makeText(EnciPt3.this, "Abriendo Sugerir Planta", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        //Botón Regreso
        btnE3toE2 = findViewById(R.id.btnE3toE2);
        btnE3toE2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intEttoEs = new Intent(EnciPt3.this, EnciPt2.class);
                startActivity(intEttoEs);
                finish();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}