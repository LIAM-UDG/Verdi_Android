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

public class Plantas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_plantas);

        Button bplNu = findViewById(R.id.bplNu);
        bplNu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Plantas.this, "Avanzando a registrar planta...", Toast.LENGTH_SHORT).show();
                Intent intPltoPU = new Intent(Plantas.this, NuevaP.class);
                startActivity(intPltoPU);
            }
        });

        Button bplPltoPrin = findViewById(R.id.bplPltoPrin);
        bplPltoPrin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Plantas.this, "Regresando al inicio...", Toast.LENGTH_SHORT).show();
                Intent intPltoPrin = new Intent(Plantas.this, PantallaPrin.class);
                startActivity(intPltoPrin);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}