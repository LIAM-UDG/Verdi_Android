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

    //Definicion de variables globales
    private Button bmMtoIn, bmMtoNew;

    //Funcion principal
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //Accion del boton para cambiar de la pantalla main al ingreso de usuario (si tiene cuenta)
        bmMtoIn = findViewById(R.id.bmMtoIn);
        bmMtoIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intMtoInUs = new Intent(MainActivity.this, InUsuario.class);
                startActivity(intMtoInUs);

            }
        });

        //Accion del boton para de la pantalla main al registro de usuario (para crear cuenta)
        bmMtoNew = findViewById(R.id.bmMtoNew);
        bmMtoNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intMtoRegis = new Intent(MainActivity.this, NewUsuario.class);
                startActivity(intMtoRegis);

            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    }
}