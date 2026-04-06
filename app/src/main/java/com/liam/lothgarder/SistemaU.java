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

public class SistemaU extends AppCompatActivity {

    private Button bsuSutoNS, bsuSutoEst, bsuSutoPrin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sistema_u);

        //Accion de boton de Agregar Sistema
        bsuSutoNS = findViewById(R.id.bsuAgre);
        bsuSutoNS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intPtoEst = new Intent(SistemaU.this, NuevoSis.class);
                startActivity(intPtoEst);
            }
        });

        //Accion de boton de Estadisticas de Sistema
        bsuSutoEst = findViewById(R.id.bsuSutoEst);
        bsuSutoEst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intPtoEst = new Intent(SistemaU.this, Estadisticas.class);
                startActivity(intPtoEst);
            }
        });

        //Accion de boton de para regresar al Inicio
        bsuSutoPrin = findViewById(R.id.bsuSutoPrin);
        bsuSutoPrin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intPtoEst = new Intent(SistemaU.this, PantallaPrin.class);
                startActivity(intPtoEst);
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