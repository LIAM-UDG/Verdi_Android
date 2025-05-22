package com.liam.lothgarder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.Toast;

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

        //Escuchador de boton para ir al inicio
        Button bmMtoPrin = findViewById(R.id.bmMtoPrin);
        bmMtoPrin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "Botón presionado", Toast.LENGTH_LONG).show();

                Intent intPprin = new Intent(MainActivity.this, PantallaPrin.class);
                startActivity(intPprin);

            }
        });

        Button bmMtoIn = findViewById(R.id.bmMtoIn);
        bmMtoIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intMtoInUs = new Intent(MainActivity.this, InUsuario.class);
                startActivity(intMtoInUs);
            }
        });

        Button bmMtoNew = findViewById(R.id.bmMtoNew);
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