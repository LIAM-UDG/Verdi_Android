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

    int PKpl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_enci);

        // Botón Aloe Vera
        Button beAloe = findViewById(R.id.beAloe);
        beAloe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intEtoAl = new Intent(Enci.this, PlantaE.class);
                PKpl = 1;
                intEtoAl.putExtra("PKpl", PKpl);
                startActivity(intEtoAl);
            }
        });

        // Botón Lavanda
        Button beLava = findViewById(R.id.beLava);
        beLava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intEtoLa = new Intent(Enci.this, PlantaE.class);
                PKpl = 2;
                intEtoLa.putExtra("PKpl", PKpl);
                startActivity(intEtoLa);
            }
        });

        // Botón Menta
        Button beMenta = findViewById(R.id.beMenta);
        beMenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intEtoMe = new Intent(Enci.this, PlantaE.class);
                PKpl = 3;
                intEtoMe.putExtra("PKpl", PKpl);
                startActivity(intEtoMe);
            }
        });

        // Botón Manzanilla
        Button beManza = findViewById(R.id.beManza);
        beManza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intEtoMa = new Intent(Enci.this, PlantaE.class);
                PKpl = 4;
                intEtoMa.putExtra("PKpl", PKpl);
                startActivity(intEtoMa);
            }
        });

        // Botón Albahaca
        Button beAlba = findViewById(R.id.beAlba);
        beAlba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intEtoAlba = new Intent(Enci.this, PlantaE.class);
                PKpl = 5;
                intEtoAlba.putExtra("PKpl", PKpl);
                startActivity(intEtoAlba);
            }
        });

        // Botón Romero
        Button beRomero = findViewById(R.id.beRomero);
        beRomero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intEtoRo = new Intent(Enci.this, PlantaE.class);
                PKpl = 6;
                intEtoRo.putExtra("PKpl", PKpl);
                startActivity(intEtoRo);
            }
        });

        // Botón Planta Araña
        Button beArana = findViewById(R.id.beArana);
        beArana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intEtoAr = new Intent(Enci.this, PlantaE.class);
                PKpl = 7;
                intEtoAr.putExtra("PKpl", PKpl);
                startActivity(intEtoAr);
            }
        });

        // Botón Bugambilia
        Button beBugam = findViewById(R.id.beBugam);
        beBugam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intEtoBu = new Intent(Enci.this, PlantaE.class);
                PKpl = 8;
                intEtoBu.putExtra("PKpl", PKpl);
                startActivity(intEtoBu);
            }
        });

        // Botón Cactus
        Button beCactus = findViewById(R.id.beCactus);
        beCactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intEtoCa = new Intent(Enci.this, PlantaE.class);
                PKpl = 9;
                intEtoCa.putExtra("PKpl", PKpl);
                startActivity(intEtoCa);
            }
        });

        // Botón Orquídea
        Button beOrqui = findViewById(R.id.beOrqui);
        beOrqui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intEtoOr = new Intent(Enci.this, PlantaE.class);
                PKpl = 10;
                intEtoOr.putExtra("PKpl", PKpl);
                startActivity(intEtoOr);
            }
        });

        // Botón Sugerir Planta
        Button beSu = findViewById(R.id.beSu);
        beSu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intEtoSp = new Intent(Enci.this, SugerirP.class);
                startActivity(intEtoSp);
                Toast.makeText(Enci.this, "Abriendo Sugerir Planta", Toast.LENGTH_SHORT).show();
            }
        });

        // Botón Volver a Principal
        Button beEtoMain = findViewById(R.id.beEtoMain);
        beEtoMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intEtoPrin = new Intent(Enci.this, PantallaPrin.class);
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