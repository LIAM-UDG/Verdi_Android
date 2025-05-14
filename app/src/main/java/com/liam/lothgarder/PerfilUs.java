package com.liam.lothgarder;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PerfilUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_perfil_us);

        TextView eNomPU = findViewById(R.id.eNomPU);
        TextView eEdadPU = findViewById(R.id.eEdadPU);
        TextView eCorrPU = findViewById(R.id.eCorrPU);
        TextView eContraPU = findViewById(R.id.eContraPU);

        String nombrePU = getIntent().getExtras().getString("Nombre");
        String edadPU = getIntent().getExtras().getString("Edad");
        String correoPU = getIntent().getExtras().getString("Correo");
        String contraPU = getIntent().getExtras().getString("Contra");

        eNomPU.setText(nombrePU);
        eEdadPU.setText(edadPU);
        eCorrPU.setText(correoPU);
        eContraPU.setText(contraPU);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}