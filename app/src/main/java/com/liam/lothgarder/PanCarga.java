package com.liam.lothgarder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PanCarga extends AppCompatActivity {

    //Definicion global
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pan_carga);

        //Paso de la barra de progreso de xml a java
        progressBar = findViewById(R.id.pbcCarga);
        progressBar.setVisibility(View.VISIBLE);

        //Carga de programa
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Instancia de las preferencias(informacion de sesion) del usuario
                SharedPreferences preferences = getSharedPreferences("guardarSesion", Context.MODE_PRIVATE);
                boolean sesion = preferences.getBoolean("Sesion", false);
                //Evaluacion de si la sesion esta activa
                if (sesion) {
                    //Inicio de la pantalla principal si el usuario esta logeado
                    Intent intCartoP = new Intent(getApplicationContext(), PantallaPrin.class);
                    startActivity(intCartoP);
                    finish();
                } else {
                    //Inicio de la pantalla main donde se puede acceder a registro o incio de sesion
                    Intent intCartoM = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intCartoM);
                    finish();
                }
            }
        }, 2000);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}