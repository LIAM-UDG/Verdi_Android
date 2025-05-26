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

public class LogrosS extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_logros_s);

        Button blsLstoLp = findViewById(R.id.blsLstoLp);
        blsLstoLp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intLstoLp = new Intent(LogrosS.this, LogrosP.class);
                startActivity(intLstoLp);
            }
        });

        Button blsLstoPrin = findViewById(R.id.blsLstoPrin);
        blsLstoPrin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intLstoPrin = new Intent(LogrosS.this, PantallaPrin.class);
                startActivity(intLstoPrin);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}