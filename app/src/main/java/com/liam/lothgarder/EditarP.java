package com.liam.lothgarder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class EditarP extends AppCompatActivity{

    RequestQueue requestQueue;
    ImageView imgPlanta;
    Button btnContinuar;
    EditText edepApodo;
    Spinner spnEpAmbiente, spnEpEstadoP;
    String nombreP, ambienteSeleccionado, estadoSeleccionado, apodo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_editar_p);

        Intent intent = getIntent();
        nombreP = intent.getStringExtra("nombrePSel");

        // Vistas
        imgPlanta = findViewById(R.id.imgPlanta);
        btnContinuar = findViewById(R.id.btnContinuar);
        edepApodo = findViewById(R.id.edepNombre);
        spnEpAmbiente = findViewById(R.id.spnEpAmbiente);
        spnEpEstadoP = findViewById(R.id.spnEpEstadoP);

        ArrayAdapter<String> aaTAmbiente = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        aaTAmbiente.addAll("Selecciona un ambiente","Interior", "Exterior", "Sombra", "Luz");
        spnEpAmbiente.setAdapter(aaTAmbiente);

        ArrayAdapter<String> aaEstadoP = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        aaEstadoP.addAll("Selecciona un estado","Muy Saludable", "Saludable", "Regular", "Malo", "Pésimo");
        spnEpEstadoP.setAdapter(aaEstadoP);

        Toast.makeText(EditarP.this, "Pantalla nueva planta", Toast.LENGTH_LONG).show();

        btnContinuar.setOnClickListener(v -> {

            ambienteSeleccionado = spnEpAmbiente.getSelectedItem().toString();
            estadoSeleccionado = spnEpEstadoP.getSelectedItem().toString();
            apodo = edepApodo.getText().toString();

                if (!apodo.isEmpty() || !estadoSeleccionado.equals("Selecciona un estado") || !ambienteSeleccionado.equals("Selecciona un ambiente")){
                    String link_domain = getString(R.string.link_domain);

                    if (estadoSeleccionado.equals("Selecciona un estado")){
                        estadoSeleccionado = "";
                    }

                    if (ambienteSeleccionado.equals("Selecciona un ambiente")){
                        ambienteSeleccionado = "";
                    }

                    //Llamado a la funcion para editar la planta de usuario
                    editarPlanta(link_domain + "?accion=editarP");

                    Intent intEdiPtoPs = new Intent(EditarP.this, Plantas.class);
                    startActivity(intEdiPtoPs);
                    finish();
                } else {
                    Toast.makeText(EditarP.this, "Debes realizar al menos un cambio", Toast.LENGTH_LONG).show();
                }

        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    //Funcion para editar planta de usuario
    private void editarPlanta(String URL){
        //Creacion de la peticion al servidor con el metodo POST
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(), "Respuesta del servidor: " + response, Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(),Toast.LENGTH_LONG).show();
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Intent intent = getIntent();
                int idP;
                //Llamada de sesion
                SharedPreferences preferences = getSharedPreferences("guardarSesion", Context.MODE_PRIVATE);
                idP = intent.getIntExtra("id", 0);

                //Metodo Map que manda los datos de la peticion al servidor extrallendo informacion del editText para guardar en la base
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("idPu", String.valueOf(idP));
                parametros.put("nombre", nombreP);
                parametros.put("apodo", apodo);
                parametros.put("estado", estadoSeleccionado);
                parametros.put("lugar", ambienteSeleccionado);
                parametros.put("usuario", preferences.getString("Correo",""));

                return parametros;
            }
        };

        //Extension de tiempo de espera para la respuesta del servidor
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000, // Timeout en ms
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}

