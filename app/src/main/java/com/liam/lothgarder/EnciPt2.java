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

public class EnciPt2 extends AppCompatActivity {

    private int PKpl;
    private Button btnE2Dalia, btnE2Jazmin, btnE2Clavel, btnE2Ruda, btnE2Hierba, btnE2Garde,
            btnE2Oregano, btnE2Kalanchoe, btnE2Lantana, btnE2LenSu, btnE2toE3, btnE2toEnci;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_enci_pt2);

        // Botón Dalia
        btnE2Dalia = findViewById(R.id.btnE2Dalia);
        btnE2Dalia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intEtoAl = new Intent(EnciPt2.this, PlantaE.class);
                PKpl = 11;
                intEtoAl.putExtra("PKpl", PKpl);
                startActivity(intEtoAl);
            }
        });

        // Botón Jazmín
        btnE2Jazmin = findViewById(R.id.btnE2Jazmin);
        btnE2Jazmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intEtoAl = new Intent(EnciPt2.this, PlantaE.class);
                PKpl = 12;
                intEtoAl.putExtra("PKpl", PKpl);
                startActivity(intEtoAl);
            }
        });

        // Botón Clavel
        btnE2Clavel = findViewById(R.id.btnE2Clavel);
        btnE2Clavel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intEtoAl = new Intent(EnciPt2.this, PlantaE.class);
                PKpl = 13;
                intEtoAl.putExtra("PKpl", PKpl);
                startActivity(intEtoAl);
            }
        });

        // Botón Ruda
        btnE2Ruda = findViewById(R.id.btnE2Ruda);
        btnE2Ruda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intEtoAl = new Intent(EnciPt2.this, PlantaE.class);
                PKpl = 14;
                intEtoAl.putExtra("PKpl", PKpl);
                startActivity(intEtoAl);
            }
        });

        // Botón Hierbabuena
        btnE2Hierba = findViewById(R.id.btnE2Hierba);
        btnE2Hierba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intEtoAl = new Intent(EnciPt2.this, PlantaE.class);
                PKpl = 15;
                intEtoAl.putExtra("PKpl", PKpl);
                startActivity(intEtoAl);
            }
        });

        // Botón Gardenia
        btnE2Garde = findViewById(R.id.btnE2Garde);
        btnE2Garde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intEtoAl = new Intent(EnciPt2.this, PlantaE.class);
                PKpl = 16;
                intEtoAl.putExtra("PKpl", PKpl);
                startActivity(intEtoAl);
            }
        });

        // Botón Oregano
        btnE2Oregano = findViewById(R.id.btnE2Oregano);
        btnE2Oregano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intEtoAl = new Intent(EnciPt2.this, PlantaE.class);
                PKpl = 17;
                intEtoAl.putExtra("PKpl", PKpl);
                startActivity(intEtoAl);
            }
        });

        // Botón Kalanchoe
        btnE2Kalanchoe = findViewById(R.id.btnE2Kalanchoe);
        btnE2Kalanchoe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intEtoAl = new Intent(EnciPt2.this, PlantaE.class);
                PKpl = 18;
                intEtoAl.putExtra("PKpl", PKpl);
                startActivity(intEtoAl);
            }
        });

        // Botón Lantana
        btnE2Lantana = findViewById(R.id.btnE2Lantana);
        btnE2Lantana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intEtoAl = new Intent(EnciPt2.this, PlantaE.class);
                PKpl = 19;
                intEtoAl.putExtra("PKpl", PKpl);
                startActivity(intEtoAl);
            }
        });

        // Botón Lengua de Suegra
        btnE2LenSu = findViewById(R.id.btnE2LenSu);
        btnE2LenSu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intEtoAl = new Intent(EnciPt2.this, PlantaE.class);
                PKpl = 20;
                intEtoAl.putExtra("PKpl", PKpl);
                startActivity(intEtoAl);
            }
        });


        // Botón Sugerir Planta
        btnE2toE3 = findViewById(R.id.btnE2toE3);
        btnE2toE3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intEtoSp = new Intent(EnciPt2.this, EnciPt3.class);
                startActivity(intEtoSp);
                Toast.makeText(EnciPt2.this, "Abriendo Tercera Parte", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        //Botón Regreso
        btnE2toEnci = findViewById(R.id.btnE2toEnci);
        btnE2toEnci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intEstoE = new Intent(EnciPt2.this, Enci.class);
                startActivity(intEstoE);
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