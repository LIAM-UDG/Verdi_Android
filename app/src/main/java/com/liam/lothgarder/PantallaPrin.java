package com.liam.lothgarder;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class PantallaPrin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pantalla_prin);


        Button bpPtoU = findViewById(R.id.bpPtoU);
        bpPtoU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intPPerUs = new Intent(PantallaPrin.this, PerfilUs.class);
                startActivity(intPPerUs);

            }
        });

        Button bpPtoE = findViewById(R.id.bpPtoE);
        bpPtoE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intPEnci = new Intent(PantallaPrin.this, Enci.class);
                startActivity(intPEnci);

            }
        });

        Button bpPtoSop = findViewById(R.id.bpPtoSop);
        bpPtoSop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intPSop = new Intent(PantallaPrin.this, Soporte.class);
                startActivity(intPSop);

            }
        });

        Button bpPtoPU = findViewById(R.id.bpPtoPU);
        bpPtoPU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intPtoPu = new Intent(PantallaPrin.this, Plantas.class);
                startActivity(intPtoPu);
            }
        });

        Button bpPtoMain = findViewById(R.id.bpPtoMain);
        bpPtoMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intPtoMain = new Intent(PantallaPrin.this, MainActivity.class);
                startActivity(intPtoMain);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}