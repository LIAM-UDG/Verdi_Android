package com.liam.lothgarder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class PantallaPrin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pantalla_prin);

        //Accion de Boton para pasar de Pantalla Principal a Perfil de Usuario
        Button bpPtoU = findViewById(R.id.bpPtoU);
        bpPtoU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intPPerUs = new Intent(PantallaPrin.this, PerfilUs.class);
                startActivity(intPPerUs);

            }
        });

        //Accion de Boton para pasar de Pantalla principal a Enciclopedia
        Button bpPtoE = findViewById(R.id.bpPtoE);
        bpPtoE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intPEnci = new Intent(PantallaPrin.this, Enci.class);
                startActivity(intPEnci);

            }
        });

        //Accion de Boton de Pantalla principal a Soporte
        Button bpPtoSop = findViewById(R.id.bpPtoSop);
        bpPtoSop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intPSop = new Intent(PantallaPrin.this, Soporte.class);
                startActivity(intPSop);

            }
        });

        //Accion de Boton de Pantalla principal a Plantas de Usuario
        Button bpPtoPU = findViewById(R.id.bpPtoPU);
        bpPtoPU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intPtoPu = new Intent(PantallaPrin.this, Plantas.class);
                startActivity(intPtoPu);
            }
        });

        //Accion de Boton para salir de tu sesion
        Button bpPtoMain = findViewById(R.id.bpPtoMain);
        bpPtoMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("guardarSesion", Context.MODE_PRIVATE);
                preferences.edit().clear().commit();

                Intent intPtoMain = new Intent(PantallaPrin.this, MainActivity.class);
                startActivity(intPtoMain);
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