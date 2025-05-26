package com.liam.lothgarder;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class NuevaP extends AppCompatActivity {

    private ImageView imgPlanta;
    private Button btnSubirFoto;
    private EditText etNombre;
    private Button btnSeleccionarPlanta;
    private EditText etEspecie;
    private Button btnSeleccionarPlantaEspecie;
    private Button btnSeleccionarTiempo;
    private Button btnSeleccionarAmbiente;
    private Button btnContinuar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_nueva_p);

        // Inicializar los elementos
        imgPlanta = findViewById(R.id.imgPlanta);
        btnSubirFoto = findViewById(R.id.btnSubirFoto);
        etNombre = findViewById(R.id.etNombre);
        btnSeleccionarPlanta = findViewById(R.id.btnSeleccionarPlanta);
        etEspecie = findViewById(R.id.etEspecie);
        btnSeleccionarPlantaEspecie = findViewById(R.id.btnSeleccionarPlantaEspecie);
        btnSeleccionarTiempo = findViewById(R.id.btnSeleccionarTiempo);
        btnSeleccionarAmbiente = findViewById(R.id.btnSeleccionarAmbiente);
        btnContinuar = findViewById(R.id.btnContinuar);

        // Listener para Subir Foto (simulación)
        btnSubirFoto.setOnClickListener(v -> {
            Toast.makeText(this, "Funcionalidad de subir foto simulada", Toast.LENGTH_SHORT).show();
        });

        // Listener para Seleccionar Planta
        btnSeleccionarPlanta.setOnClickListener(v -> {
            Toast.makeText(this, "Selecciona una planta (simulado)", Toast.LENGTH_SHORT).show();
            etNombre.setText("Planta Ejemplo");
        });

        // Listener para Seleccionar Planta (Especie)
        btnSeleccionarPlantaEspecie.setOnClickListener(v -> {
            Toast.makeText(this, "Selecciona una especie (simulado)", Toast.LENGTH_SHORT).show();
            etEspecie.setText("Especie Ejemplo");
        });

        // Listener para Seleccionar Tiempo
        btnSeleccionarTiempo.setOnClickListener(v -> {
            Toast.makeText(this, "Selecciona el tiempo (simulado)", Toast.LENGTH_SHORT).show();
        });

        // Listener para Seleccionar Ambiente
        btnSeleccionarAmbiente.setOnClickListener(v -> {
            Toast.makeText(this, "Selecciona el ambiente (simulado)", Toast.LENGTH_SHORT).show();
        });

        // Listener para Continuar
        btnContinuar.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString().trim();
            String especie = etEspecie.getText().toString().trim();

            if (nombre.isEmpty()) {
                etNombre.setError("Por favor, ingresa el nombre");
                etNombre.requestFocus();
                return;
            }
            if (especie.isEmpty()) {
                etEspecie.setError("Por favor, ingresa la especie");
                etEspecie.requestFocus();
                return;
            }

            Toast.makeText(this, "Planta registrada:\nNombre: " + nombre + "\nEspecie: " + especie, Toast.LENGTH_LONG).show();

            // Regresar a Plantas con los datos
            Intent intent = new Intent(NuevaP.this, Plantas.class);
            intent.putExtra("nombre_planta", nombre);
            intent.putExtra("especie_planta", especie);
            startActivity(intent);
            finish();
        });

        // Configuración de insets para edge-to-edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}