package com.liam.lothgarder;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class Alertas extends AppCompatActivity {

    private EditText etBuscar;
    private LinearLayout llAlertasContainer;
    private Button btnBorrarTodo;
    private ImageButton btnBack;
    private List<LinearLayout> alertaViews;
    private List<AlertaItem> alertas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alertas);

        initViews();
        initData();
        setupListeners();
    }

    private void initViews() {
        etBuscar = findViewById(R.id.et_buscar);
        llAlertasContainer = findViewById(R.id.ll_alertas_container);
        btnBorrarTodo = findViewById(R.id.btn_borrar_todo);
        btnBack = findViewById(R.id.btn_back);
        alertaViews = new ArrayList<>();
    }

    private void initData() {
        alertas = new ArrayList<>();
        alertas.add(new AlertaItem("Fallo sensor", "03/29/2025", "Sensores"));
        alertas.add(new AlertaItem("Fallo de obstrucción", "03/29/2025", "Terminal IoT"));
        alertas.add(new AlertaItem("Fallo en extracción de agua", "03/29/2025", "Sensores"));

        // Guardar referencias a las vistas de alertas existentes
        for (int i = 0; i < llAlertasContainer.getChildCount(); i++) {
            View child = llAlertasContainer.getChildAt(i);
            if (child instanceof LinearLayout) {
                alertaViews.add((LinearLayout) child);
            }
        }
    }

    private void setupListeners() {
        // Botón de regreso
        btnBack.setOnClickListener(v -> finish());

        // Funcionalidad de búsqueda
        etBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filtrarAlertas(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Botón borrar todo
        btnBorrarTodo.setOnClickListener(v -> mostrarDialogoBorrarTodo());

        // Click listeners para cada alerta
        setupAlertaClickListeners();
    }

    private void setupAlertaClickListeners() {
        for (int i = 0; i < alertaViews.size() && i < alertas.size(); i++) {
            final int index = i;
            alertaViews.get(i).setOnClickListener(v -> mostrarDetalleAlerta(alertas.get(index)));
        }
    }

    private void filtrarAlertas(String busqueda) {
        String busquedaLower = busqueda.toLowerCase().trim();

        for (int i = 0; i < alertaViews.size() && i < alertas.size(); i++) {
            LinearLayout alertaView = alertaViews.get(i);
            AlertaItem alerta = alertas.get(i);

            boolean coincide = alerta.getTitulo().toLowerCase().contains(busquedaLower) ||
                    alerta.getCategoria().toLowerCase().contains(busquedaLower);

            alertaView.setVisibility(coincide ? View.VISIBLE : View.GONE);
        }
    }

    private void mostrarDetalleAlerta(AlertaItem alerta) {
        String mensaje = String.format("Tipo: %s\nFecha: %s\nCategoría: %s",
                alerta.getTitulo(),
                alerta.getFecha(),
                alerta.getCategoria());

        new AlertDialog.Builder(this)
                .setTitle("Detalle de Alerta")
                .setMessage(mensaje)
                .setPositiveButton("Cerrar", null)
                .setNeutralButton("Resolver", (dialog, which) -> {
                    Toast.makeText(this, "Alerta marcada como resuelta", Toast.LENGTH_SHORT).show();
                })
                .show();
    }

    private void mostrarDialogoBorrarTodo() {
        new AlertDialog.Builder(this)
                .setTitle("Confirmar")
                .setMessage("¿Estás seguro de que quieres borrar todas las alertas?")
                .setPositiveButton("Sí", (dialog, which) -> borrarTodasLasAlertas())
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void borrarTodasLasAlertas() {
        // Ocultar todas las alertas
        for (LinearLayout alertaView : alertaViews) {
            alertaView.setVisibility(View.GONE);
        }

        // Limpiar datos
        alertas.clear();

        Toast.makeText(this, "Todas las alertas han sido borradas", Toast.LENGTH_SHORT).show();

        // Opcional: regresar a la pantalla anterior después de borrar
        // finish();
    }

    // Clase interna para representar una alerta
    private static class AlertaItem {
        private String titulo;
        private String fecha;
        private String categoria;

        public AlertaItem(String titulo, String fecha, String categoria) {
            this.titulo = titulo;
            this.fecha = fecha;
            this.categoria = categoria;
        }

        public String getTitulo() { return titulo; }
        public String getFecha() { return fecha; }
        public String getCategoria() { return categoria; }
    }
}