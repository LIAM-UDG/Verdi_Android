package com.liam.lothgarder;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class Alertas extends AppCompatActivity {

    private EditText etBuscar;
    private LinearLayout llAlertasContainer;
    private Button btnBack;
    private Spinner spinnerFilter;
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
        btnBack = findViewById(R.id.btn_back);
        spinnerFilter = findViewById(R.id.spinner_filter);
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
                filtrarAlertas(s.toString(), spinnerFilter.getSelectedItem().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Funcionalidad del Spinner para filtrar por categoría
        spinnerFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = parent.getItemAtPosition(position).toString();
                filtrarAlertas(etBuscar.getText().toString(), selectedCategory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No hacer nada si no se selecciona nada
            }
        });

        // Click listeners para cada alerta
        setupAlertaClickListeners();
    }

    private void setupAlertaClickListeners() {
        for (int i = 0; i < alertaViews.size() && i < alertas.size(); i++) {
            final int index = i;
            alertaViews.get(i).setOnClickListener(v -> mostrarDetalleAlerta(alertas.get(index)));
        }
    }

    private void filtrarAlertas(String busqueda, String categoria) {
        String busquedaLower = busqueda.toLowerCase().trim();
        String categoriaLower = categoria.toLowerCase().trim();

        for (int i = 0; i < alertaViews.size() && i < alertas.size(); i++) {
            LinearLayout alertaView = alertaViews.get(i);
            AlertaItem alerta = alertas.get(i);

            boolean coincideBusqueda = alerta.getTitulo().toLowerCase().contains(busquedaLower) ||
                    alerta.getCategoria().toLowerCase().contains(busquedaLower);

            boolean coincideCategoria = categoriaLower.equals("filtro") || // Si se selecciona "Filtro", mostrar todas
                    alerta.getCategoria().toLowerCase().equals(categoriaLower);

            // Mostrar la alerta solo si coincide con la búsqueda Y la categoría
            alertaView.setVisibility(coincideBusqueda && coincideCategoria ? View.VISIBLE : View.GONE);
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