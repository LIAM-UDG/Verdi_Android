package com.liam.lothgarder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class PantallaPrin extends AppCompatActivity {

    private Button bpPtoU, bpPtoE, bpPtoSop, bpPtoPU, bpPtoEst, bpPtoMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pantalla_prin);

        //Accion de Boton para pasar de Pantalla Principal a Perfil de Usuario
        bpPtoU = findViewById(R.id.bpPtoU);
        bpPtoU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intPPerUs = new Intent(PantallaPrin.this, PerfilUs.class);
                startActivity(intPPerUs);
            }
        });

        //Accion de Boton para pasar de Pantalla principal a Enciclopedia
        bpPtoE = findViewById(R.id.bpPtoE);
        bpPtoE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intPEnci = new Intent(PantallaPrin.this, Enci.class);
                startActivity(intPEnci);
            }
        });

        //Accion de Boton de Pantalla principal a Plantas de Usuario
        bpPtoPU = findViewById(R.id.bpPtoPU);
        bpPtoPU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intPtoPu = new Intent(PantallaPrin.this, Plantas.class);
                startActivity(intPtoPu);
            }
        });

        //Accion de Boton de Pantalla principal a Sistema de Usuario
        bpPtoEst = findViewById(R.id.bpPtoEst);
        bpPtoEst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intPtoEst = new Intent(PantallaPrin.this, SistemaU.class);
                startActivity(intPtoEst);
            }
        });

        //Accion de Boton de Pantalla principal a Soporte
        bpPtoSop = findViewById(R.id.bpPtoSop);
        bpPtoSop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intPSop = new Intent(PantallaPrin.this, Soporte.class);
                startActivity(intPSop);
            }
        });

        //Botón de pantalla principal a Logros
        ImageView bpPtoL = findViewById(R.id.bpPtoL);
        bpPtoL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intPtoL = new Intent(PantallaPrin.this, LogrosP.class);
                startActivity(intPtoL);
            }
        });

        //Botón de pantalla principal a Búsqueda
        ImageView bpPtoB = findViewById(R.id.bpPtoB);
        bpPtoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PantallaPrin.this, "Click en búsqueda", Toast.LENGTH_SHORT).show();
                Intent intPtoB = new Intent(PantallaPrin.this, Busqueda.class);
                startActivity(intPtoB);
            }
        });

        //Botón de pantalla princial a Alertas
        ImageView bpPtoA = findViewById(R.id.bpPtoA);
        bpPtoA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PantallaPrin.this, "Click en búsqueda", Toast.LENGTH_SHORT).show();
                Intent intPtoA = new Intent(PantallaPrin.this, Alertas.class);
                startActivity(intPtoA);
            }
        });

        //Accion de Boton para salir de tu sesion
        bpPtoMain = findViewById(R.id.bpPtoMain);
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