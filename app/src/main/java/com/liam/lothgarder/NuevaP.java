package com.liam.lothgarder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

public class NuevaP extends AppCompatActivity{

    RequestQueue requestQueue;
    ImageView imgPlanta;
    Button btnSubirFoto, btnContinuar;
    EditText etNombre;
    Spinner spnTipoP, spnAmbiente, spnEstadoP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_nueva_p);

        // Vistas
        imgPlanta = findViewById(R.id.imgPlanta);
        btnSubirFoto = findViewById(R.id.btnSubirFoto);
        btnContinuar = findViewById(R.id.btnContinuar);
        etNombre = findViewById(R.id.etNombre);
        spnTipoP = findViewById(R.id.spnTipoP);
        spnAmbiente = findViewById(R.id.spnAmbiente);
        spnEstadoP = findViewById(R.id.spnEstadoP);

        //El ArrayAdapter es para meterlo dentro del Spinner del XML
        ArrayAdapter<String> aaTPlanta = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        aaTPlanta.addAll("Selecciona una planta","Lavanda", "Sabila", "Menta", "Manzanilla", "Albahaca", "Romero", "Planta araña", "Bugambilia", "Cactus", "Orquidea");
        spnTipoP.setAdapter(aaTPlanta);

        ArrayAdapter<String> aaTAmbiente = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        aaTAmbiente.addAll("Selecciona una ambiente","Interior", "Exterior", "Sombra", "Luz");
        spnAmbiente.setAdapter(aaTAmbiente);

        ArrayAdapter<String> aaEstadoP = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        aaEstadoP.addAll("Selecciona un estado","Muy Saludable", "Saludable", "Regular", "Malo", "Pésimo");
        spnEstadoP.setAdapter(aaEstadoP);

        Toast.makeText(NuevaP.this, "Pantalla nueva planta", Toast.LENGTH_LONG).show();

        btnSubirFoto.setOnClickListener(v -> {
            Toast.makeText(NuevaP.this, "Funcionalidad subir foto pendiente", Toast.LENGTH_SHORT).show();
            // Aquí agregarás el selector de imagen o cámara
        });

        btnContinuar.setOnClickListener(v -> {
            String plantaSeleccionada = spnTipoP.getSelectedItem().toString();
            String ambienteSeleccionado = spnAmbiente.getSelectedItem().toString();
            String estadoSeleccionado = spnEstadoP.getSelectedItem().toString();

            if (plantaSeleccionada.equals("Selecciona una planta")) {
                Toast.makeText(NuevaP.this, "Por favor selecciona una planta válido", Toast.LENGTH_SHORT).show();
            } else {
                if (ambienteSeleccionado.equals("Selecciona una planta")) {
                    Toast.makeText(NuevaP.this, "Por favor selecciona un ambiente válido", Toast.LENGTH_SHORT).show();
                } else {
                    if (estadoSeleccionado.equals("Selecciona un estado")) {
                        Toast.makeText(NuevaP.this, "Por favor selecciona un estado válido", Toast.LENGTH_SHORT).show();
                    } else {
                        guardarPlanta("https://app-d9fd7517-b3e4-4e1e-8fba-66483bfb6711.cleverapps.io//?accion=insertarP");
                        /*
                        Guardado en local
                        guardarPlanta("http:// 192.168.137.128:80/lothgarder/insertarP.php");
                         */
                        Intent intNptoPs = new Intent(NuevaP.this, Plantas.class);
                        startActivity(intNptoPs);
                    }
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
    private void guardarPlanta(String URL){
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
                EditText edApodo = findViewById(R.id.etNombre);
                //Definicion local del spinner
                spnTipoP = findViewById(R.id.spnTipoP);
                spnAmbiente = findViewById(R.id.spnAmbiente);
                spnEstadoP = findViewById(R.id.spnEstadoP);
                //Llamada de sesion
                SharedPreferences preferences = getSharedPreferences("guardarSesion", Context.MODE_PRIVATE);

                //Metodo Map que manda los datos de la peticion al servidor extrallendo informacion del editText para guardar en la base
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("nombre", spnTipoP.getSelectedItem().toString());
                parametros.put("apodo", edApodo.getText().toString());
                parametros.put("estado", spnEstadoP.getSelectedItem().toString());
                parametros.put("lugar", spnAmbiente.getSelectedItem().toString());
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


    /*private void guardarPlanta(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, response -> {
            Toast.makeText(getApplicationContext(), "Respuesta del servidor: " + response, Toast.LENGTH_LONG).show();

            // Podrías limpiar campos o cerrar actividad
            Intent intRtoMain = new Intent(NuevaP.this, MainActivity.class);
            startActivity(intRtoMain);

        }, error -> Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show()) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();

                String nombre = etNombre.getText().toString();

                // Por ahora no tienes descripción, cuidados ni uso en el XML,
                // si quieres agregar esos campos, crea EditText y recupéralos aquí.
                // En este ejemplo, sólo mando nombre

                parametros.put("nombre", nombre);

                // Por ejemplo, podrías enviar un campo vacío o default para los demás:
                parametros.put("descripcion", "");
                parametros.put("cuidados", "");
                parametros.put("uso", "");

                // Imagen y ambiente no están enviándose en esta versión.

                return parametros;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }*/

}

