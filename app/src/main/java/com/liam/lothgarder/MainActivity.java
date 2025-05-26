package com.liam.lothgarder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    //Funcion principal
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //Accion del boton para cambiar de la pantalla main al ingreso de usuario (si tiene cuenta)
        Button bmMtoIn = findViewById(R.id.bmMtoIn);
        bmMtoIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intMtoInUs = new Intent(MainActivity.this, InUsuario.class);
                startActivity(intMtoInUs);
            }
        });

        //Accion del boton para de la pantalla main al registro de usuario (para crear cuenta)
        Button bmMtoNew = findViewById(R.id.bmMtoNew);
        bmMtoNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intMtoRegis = new Intent(MainActivity.this, NewUsuario.class);
                startActivity(intMtoRegis);
            }
        });

        Button bmMtoPrin = findViewById(R.id.bmMtoPrin);
        bmMtoPrin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intMtoPrin = new Intent(MainActivity.this, PantallaPrin.class);
                startActivity(intMtoPrin);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    }
}