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
    EditText edepNombre;
    Spinner spnEpAmbiente, spnEpEstadoP;
    String nombreP;

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
        edepNombre = findViewById(R.id.edepNombre);
        spnEpAmbiente = findViewById(R.id.spnEpAmbiente);
        spnEpEstadoP = findViewById(R.id.spnEpEstadoP);


        ArrayAdapter<String> aaTAmbiente = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        aaTAmbiente.addAll("Selecciona una ambiente","Interior", "Exterior", "Sombra", "Luz");
        spnEpAmbiente.setAdapter(aaTAmbiente);

        ArrayAdapter<String> aaEstadoP = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        aaEstadoP.addAll("Selecciona un estado","Muy Saludable", "Saludable", "Regular", "Malo", "Pésimo");
        spnEpEstadoP.setAdapter(aaEstadoP);

        Toast.makeText(EditarP.this, "Pantalla nueva planta", Toast.LENGTH_LONG).show();

        btnContinuar.setOnClickListener(v -> {
            String ambienteSeleccionado = spnEpAmbiente.getSelectedItem().toString();
            String estadoSeleccionado = spnEpEstadoP.getSelectedItem().toString();

                if (ambienteSeleccionado.equals("Selecciona una planta")) {
                    Toast.makeText(EditarP.this, "Por favor selecciona un ambiente válido", Toast.LENGTH_SHORT).show();
                } else {
                    if (estadoSeleccionado.equals("Selecciona un estado")) {
                        Toast.makeText(EditarP.this, "Por favor selecciona un estado válido", Toast.LENGTH_SHORT).show();
                    } else {

                        //Extraccion del link de dominio desde strings.xml
                        String link_domain = getString(R.string.link_domain);

                        editarPlanta(link_domain + "?accion=editarP");

                        Intent intEdiPtoPs = new Intent(EditarP.this, Plantas.class);
                        startActivity(intEdiPtoPs);

                        finish();

                        /*
                        Funcion local
                        editarPlanta("http://192.168.137.128:80/lothgarder/editarP.php");
                         */
                    }
                }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    //Metodo para guardar planta
    private void editarPlanta(String URL){
        //Creacion de la peticion al servidor con el metodo POST
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Respuesta del servidor: " + response, Toast.LENGTH_LONG).show();

                /*Pasar a pantalla principal tras registrar
                Intent intRtoP = new Intent(NewUsuario.this, InUsuario.class);
                startActivity(intRtoP);
                Toast.makeText(getApplicationContext(), "Operación exitosa",Toast.LENGTH_LONG).show();*/
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(),Toast.LENGTH_LONG).show();
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                //Definicion local de editText
                EditText edApodo = findViewById(R.id.edepNombre);
                //Definicion local del spinner
                spnEpAmbiente = findViewById(R.id.spnEpAmbiente);
                spnEpEstadoP = findViewById(R.id.spnEpEstadoP);
                //Llamada de sesion
                SharedPreferences preferences = getSharedPreferences("guardarSesion", Context.MODE_PRIVATE);

                //Metodo Map que manda los datos de la peticion al servidor extrallendo informacion del editText para guardar en la base
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("nombre", nombreP);
                parametros.put("apodo", edApodo.getText().toString());
                parametros.put("estado", spnEpEstadoP.getSelectedItem().toString());
                parametros.put("lugar", spnEpAmbiente.getSelectedItem().toString());
                parametros.put("usuario", preferences.getString("Correo",""));

                return parametros;
                //return super.getParams();
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

