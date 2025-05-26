package com.liam.lothgarder;

import android.annotation.SuppressLint;
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

public class SugerirP extends AppCompatActivity {

    private ImageView imgPlanta;
    private Button btnSubirFoto;
    private EditText etNombre;
    private EditText etDescripcion;
    private Button btnContinuar;
    private Button bnpNptoPrin;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sugerir_p); // Cambiado a activity_sugerir_p

        // Inicializar los elementos del layout
        imgPlanta = findViewById(R.id.imgPlanta);
        btnSubirFoto = findViewById(R.id.btnSubirFoto);
        etNombre = findViewById(R.id.etNombre);
        etDescripcion = findViewById(R.id.etDescripcion);
        btnContinuar = findViewById(R.id.btnContinuar);
        bnpNptoPrin = findViewById(R.id.bnpNptoPrin);

        // Listener para el botón Subir Foto (simulando la acción)
        btnSubirFoto.setOnClickListener(v -> {
            Toast.makeText(this, "Funcionalidad de subir foto simulada", Toast.LENGTH_SHORT).show();
            // Aquí podrías implementar la selección de una foto en el futuro
        });

        // Listener para el botón Continuar
        btnContinuar.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString().trim();
            String descripcion = etDescripcion.getText().toString().trim();

            // Validar que los campos no estén vacíos
            if (nombre.isEmpty()) {
                etNombre.setError("Por favor, ingresa el nombre de la planta");
                etNombre.requestFocus();
                return;
            }
            if (descripcion.isEmpty()) {
                etDescripcion.setError("Por favor, ingresa una descripción");
                etDescripcion.requestFocus();
                return;
            }

            // Mostrar un mensaje con los datos ingresados
            Toast.makeText(this, "Planta sugerida:\nNombre: " + nombre + "\nDescripción: " + descripcion, Toast.LENGTH_LONG).show();

            // Pasar los datos a la pantalla Plantas y regresar
            Intent intent = new Intent(SugerirP.this, Plantas.class);
            intent.putExtra("nombre_planta", nombre);
            intent.putExtra("descripcion_planta", descripcion);
            startActivity(intent);
            finish(); // Cierra la actividad actual
        });

        // Listener para el botón Atrás
        bnpNptoPrin.setOnClickListener(v -> {
            Toast.makeText(this, "Regresando...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SugerirP.this, Plantas.class);
            startActivity(intent);
            finish(); // Cierra la actividad actual
        });

        // Configuración de los insets para edge-to-edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}