package com.liam.lothgarder;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NuevaP extends AppCompatActivity{

    //Definicion de variables globales
    private RequestQueue requestQueue;
    ImageView imgNPPlanta;
    private Button btnNPSubFoto, btnNPConti, btnNPtoP;
    EditText edNPApodo;
    private Spinner spnNPTipoP, spnNPAmbi, spnNPEstadoP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_nueva_p);

        //Paso de la componentes de xml a java
        imgNPPlanta = findViewById(R.id.imgNPPlanta);
        btnNPSubFoto = findViewById(R.id.btnNPSubFoto);
        btnNPtoP = findViewById(R.id.btnNPtoP);
        btnNPConti = findViewById(R.id.btnNPConti);
        edNPApodo = findViewById(R.id.edNPApodo);
        spnNPTipoP = findViewById(R.id.spnNPTipoP);
        spnNPAmbi = findViewById(R.id.spnNPAmbi);
        spnNPEstadoP = findViewById(R.id.spnNPRiegoP);

        //El ArrayAdapter es para meterlo dentro del Spinner del XML
        //ArrayAdapter<String> aaTPlanta = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        //aaTPlanta.addAll("Selecciona una planta","Lavanda", "Sabila", "Menta", "Manzanilla", "Albahaca", "Romero", "Planta araña", "Bugambilia", "Cactus", "Orquidea");
        //spnNPTipoP.setAdapter(aaTPlanta);

        //ArrayAdapter<String> aaTAmbiente = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        //aaTAmbiente.addAll("Selecciona una ambiente","Interior", "Exterior", "Sombra", "Luz");
        //spnNPAmbi.setAdapter(aaTAmbiente);

        //ArrayAdapter<String> aaEstadoP = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        //aaEstadoP.addAll("Selecciona un estado","Muy Saludable", "Saludable", "Regular", "Malo", "Pésimo");
        //spnNPEstadoP.setAdapter(aaEstadoP);

        //Accion de boton para subir o tomar foto
        btnNPSubFoto.setOnClickListener(v -> {
            Intent intNPtoTF= new Intent(NuevaP.this, TomarFoto.class);
            startActivity(intNPtoTF);

        });

        //Llamado a la función para eliminar fotos anteriores
        limpiarFs();

        //LLamado a la funcion para carga vista de foto
        cargarVistaF(0);

        //Accion de boton de cambiar de pantalla de nueva planta a plantas
        btnNPtoP.setOnClickListener( v -> {
            Intent intNPls= new Intent(NuevaP.this, Plantas.class);
            startActivity(intNPls);
            finish();
        });

        //Boton de continuar para guardar la planta
        btnNPConti.setOnClickListener(v -> {
            String plantaSeleccionada = spnNPTipoP.getSelectedItem().toString();
            String ambienteSeleccionado = spnNPAmbi.getSelectedItem().toString();
            String estadoSeleccionado = spnNPEstadoP.getSelectedItem().toString();

            if (plantaSeleccionada.equals("Selecciona una planta")) {
                Toast.makeText(NuevaP.this, "Por favor selecciona una planta válido", Toast.LENGTH_SHORT).show();
            } else {
                if (ambienteSeleccionado.equals("Selecciona una planta")) {
                    Toast.makeText(NuevaP.this, "Por favor selecciona un ambiente válido", Toast.LENGTH_SHORT).show();
                } else {
                    if (estadoSeleccionado.equals("Selecciona un tipo de riego")) {
                        Toast.makeText(NuevaP.this, "Por favor selecciona un estado válido", Toast.LENGTH_SHORT).show();
                    } else {

                        //Extraccion del link de dominio desde strings.xml
                        String link_domain = getString(R.string.link_domain);

                        //Llamado a la funcion para guardar la planta
                        guardarPlanta(link_domain + "?accion=insertarP");

                        Intent intNptoPs = new Intent(NuevaP.this, Plantas.class);
                        startActivity(intNptoPs);
                        finish();
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

    //Funcion para cargar la vista previa al tomar foto
    @Override
    protected void onResume() {
        super.onResume();
        cargarVistaF(0);
    }

    //Función para eliminar fotos anteriores
    private void limpiarFs() {
        try {
            AdministradorSQLite admin = new AdministradorSQLite(this);
            SQLiteDatabase db = admin.getWritableDatabase();

            db.delete("fotos", "tipo_foto = ? AND id_referencia = ?",
                    new String[]{"PLANTA", "0"});

            db.close();

            Log.d("Verdi", "Fotos temporales eliminadas");
        } catch (Exception e) {
            Log.e("Verdi", "Error limpiando fotos: " + e.getMessage());
        }
    }

    //Funcion para cargar foto
    private void cargarVistaF(int idPlanta) {
        try {
            AdministradorSQLite admin = new AdministradorSQLite(this);
            SQLiteDatabase db = admin.getReadableDatabase();

            // Consulta: buscamos la ruta donde el id_referencia coincida
            Cursor cursor = db.rawQuery("SELECT ruta_foto FROM fotos WHERE id_referencia = ? AND tipo_foto = 'PLANTA' LIMIT 1",
                    new String[]{String.valueOf(idPlanta)});

            if (cursor.moveToFirst()) {
                String rutaUri = cursor.getString(0);

                Uri uriFoto = Uri.parse(rutaUri);

                imgNPPlanta.setImageURI(uriFoto);

                imgNPPlanta.setScaleType(ImageView.ScaleType.CENTER_CROP);

                Log.d("Verdi", "Foto cargada desde: " + rutaUri);
            } else {
                Log.d("Verdi", "No se encontró foto para el ID: " + idPlanta);
                imgNPPlanta.setImageDrawable(Drawable.createFromPath("gallery_thumb"));
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            Log.e("Verdi", "Error al cargar foto local: " + e.getMessage());
        }
    }

    //Funcion para guardar planta
    private void guardarPlanta(String URL){
        //Creacion de la peticion al servidor con el metodo POST
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //Respuesta del servidor
                    JSONObject jsonResponse = new JSONObject(response);

                    if (jsonResponse.getString("status").equals("success")) {
                        int idAsignado = jsonResponse.getInt("id_planta");

                        //Vinculo de ID y ruta
                        AdministradorSQLite admin = new AdministradorSQLite(NuevaP.this);
                        //Uso de le metodo actualizar ID
                        admin.actualizarIdPu(idAsignado, "PLANTA");

                        Toast.makeText(NuevaP.this, "Planta registrada", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e("Verdi", "Error al procesar JSON: " + e.getMessage());

                    Toast.makeText(getApplicationContext(), "Error en respuesta: " + response, Toast.LENGTH_LONG).show();
                }
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
                edNPApodo = findViewById(R.id.edNPApodo);
                //Definicion local del spinner
                spnNPTipoP = findViewById(R.id.spnNPTipoP);
                spnNPAmbi = findViewById(R.id.spnNPAmbi);
                spnNPEstadoP = findViewById(R.id.spnNPRiegoP);
                //Llamada de sesion
                SharedPreferences preferences = getSharedPreferences("guardarSesion", Context.MODE_PRIVATE);

                //Metodo Map que manda los datos de la peticion al servidor extrallendo informacion del editText para guardar en la base
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("nombre", spnNPTipoP.getSelectedItem().toString());
                parametros.put("apodo", edNPApodo.getText().toString());
                parametros.put("estado", spnNPEstadoP.getSelectedItem().toString());
                parametros.put("lugar", spnNPAmbi.getSelectedItem().toString());
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

