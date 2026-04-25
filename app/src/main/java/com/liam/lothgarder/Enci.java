package com.liam.lothgarder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Enci extends AppCompatActivity {

    //Definicion de variables globales
    private int PKpl;
    private Button btnESabila, btnELava, btnEMenta, btnEManza, btnEAlba, btnERomero, btnEArana,
            btnEBugam, btnEConchita, btnEOrqui, btnEtoE2, btnEtoM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_enci);

        // Botón Aloe Vera
        btnESabila = findViewById(R.id.btnESabila);
        btnESabila.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intEtoAl = new Intent(Enci.this, PlantaE.class);
                PKpl = 1;
                intEtoAl.putExtra("PKpl", PKpl);
                startActivity(intEtoAl);
            }
        });

        // Botón Lavanda
        btnELava = findViewById(R.id.btnELava);
        btnELava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intEtoLa = new Intent(Enci.this, PlantaE.class);
                PKpl = 2;
                intEtoLa.putExtra("PKpl", PKpl);
                startActivity(intEtoLa);
            }
        });

        // Botón Menta
        btnEMenta = findViewById(R.id.btnEMenta);
        btnEMenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intEtoMe = new Intent(Enci.this, PlantaE.class);
                PKpl = 3;
                intEtoMe.putExtra("PKpl", PKpl);
                startActivity(intEtoMe);
            }
        });

        // Botón Manzanilla
        btnEManza = findViewById(R.id.btnEManza);
        btnEManza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intEtoMa = new Intent(Enci.this, PlantaE.class);
                PKpl = 4;
                intEtoMa.putExtra("PKpl", PKpl);
                startActivity(intEtoMa);
            }
        });

        // Botón Albahaca
        btnEAlba = findViewById(R.id.btnEAlba);
        btnEAlba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intEtoAlba = new Intent(Enci.this, PlantaE.class);
                PKpl = 5;
                intEtoAlba.putExtra("PKpl", PKpl);
                startActivity(intEtoAlba);
            }
        });

        // Botón Romero
        btnERomero = findViewById(R.id.btnERomero);
        btnERomero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intEtoRo = new Intent(Enci.this, PlantaE.class);
                PKpl = 6;
                intEtoRo.putExtra("PKpl", PKpl);
                startActivity(intEtoRo);
            }
        });

        // Botón Planta Araña
        btnEArana = findViewById(R.id.btnEArana);
        btnEArana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intEtoAr = new Intent(Enci.this, PlantaE.class);
                PKpl = 7;
                intEtoAr.putExtra("PKpl", PKpl);
                startActivity(intEtoAr);
            }
        });

        // Botón Bugambilia
        btnEBugam = findViewById(R.id.btnEBugam);
        btnEBugam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intEtoBu = new Intent(Enci.this, PlantaE.class);
                PKpl = 8;
                intEtoBu.putExtra("PKpl", PKpl);
                startActivity(intEtoBu);
            }
        });

        // Botón Cactus
        btnEConchita = findViewById(R.id.btnEConchita);
        btnEConchita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intEtoCa = new Intent(Enci.this, PlantaE.class);
                PKpl = 9;
                intEtoCa.putExtra("PKpl", PKpl);
                startActivity(intEtoCa);
            }
        });

        // Botón Orquídea
        btnEOrqui = findViewById(R.id.btnEOrqui);
        btnEOrqui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intEtoOr = new Intent(Enci.this, PlantaE.class);
                PKpl = 10;
                intEtoOr.putExtra("PKpl", PKpl);
                startActivity(intEtoOr);
            }
        });

        // Botón Sugerir Planta
        btnEtoE2 = findViewById(R.id.btnEtoE2);
        btnEtoE2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intEtoSp = new Intent(Enci.this, EnciPt2.class);
                startActivity(intEtoSp);
                Toast.makeText(Enci.this, "Abriendo Segunda Parte", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        // Botón Volver a Principal
        btnEtoM = findViewById(R.id.btnEtoM);
        btnEtoM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intEtoPrin = new Intent(Enci.this, PantallaPrin.class);
                intEtoPrin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intEtoPrin);
            }
        });

        // Configuración de WindowInsets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}