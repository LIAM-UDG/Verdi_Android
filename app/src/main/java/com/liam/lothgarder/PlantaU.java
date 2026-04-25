package com.liam.lothgarder;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class PlantaU extends AppCompatActivity {

    private Button btnPlUElimin, btnPlUEditar, btnPlUtoP;
    private String nombreP, correoU;
    SharedPreferences preferences;
    int idP;
    ImageView imgPlUPlanta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_planta_u);

        Intent intent = getIntent();
        imgPlUPlanta = findViewById(R.id.imgPlUPlanta);

        nombreP = intent.getStringExtra("nombre");
        idP = intent.getIntExtra("id", 0);

        preferences = getSharedPreferences("guardarSesion", Context.MODE_PRIVATE);
        correoU = preferences.getString("Correo","");

        //Extraccion del link de dominio desde strings.xml
        String link_domain = getString(R.string.link_domain);
        Toast.makeText(getApplicationContext(), "Id: " + idP, Toast.LENGTH_SHORT).show();

        //Llamada a la funcion para buscar planta de usuario
        buscarPlantaU(link_domain + "?accion=buscarPU" + "&idPu=" + idP + "&correo=" + correoU);
        //Llamada a la funcion para cargar foto
        cargarFLocal(idP);

        //Accion de boton para eliminar planta de usuario
        btnPlUElimin = findViewById(R.id.btnPlUElimin);
        btnPlUElimin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Extraccion del link de dominio desde strings.xml
                String link_domain = getString(R.string.link_domain);

                //Llamada a la funcion para eliminar planta de usuario
                eliminarPlanta(link_domain + "?accion=eliminarPU");

                Intent intPlutoPs = new Intent(PlantaU.this, Plantas.class);
                startActivity(intPlutoPs);
                finish();
            }
        });

        //Accion de boton para editar planta de usuario
        btnPlUEditar = findViewById(R.id.btnPlUEditar);
        btnPlUEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intPlutoEp = new Intent(PlantaU.this, EditarP.class);
                intPlutoEp.putExtra("id", idP);
                intPlutoEp.putExtra("nombrePSel", nombreP);
                startActivity(intPlutoEp);
            }
        });

        //Accion de boton para regresar al inicio
        btnPlUtoP = findViewById(R.id.btnPlUtoP);
        btnPlUtoP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intPlutoPrin = new Intent(PlantaU.this, PantallaPrin.class);
                startActivity(intPlutoPrin);
                finish();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    //Funcion para buscar planta de usuario
    private void buscarPlantaU(String URL) {

        TextView eNomPlU = findViewById(R.id.etPlUTitulo);
        TextView eApodoPlU = findViewById(R.id.etPlUApodoV);
        TextView etPlUHumedV = findViewById(R.id.etPlUHumedV);
        TextView etPlUTempV = findViewById(R.id.etPlUTempV);
        TextView eEstadoPlU = findViewById(R.id.etPlUEstadoV);
        TextView eLugarPlU = findViewById(R.id.etPlULugarV);

        //Creacion de la peticion
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int reco = 0; reco < response.length(); reco++) {
                    try {

                        jsonObject = response.getJSONObject(reco);
                        eNomPlU.setText(jsonObject.getString("Nombre"));
                        eApodoPlU.setText(jsonObject.getString("Apodo"));
                        etPlUHumedV.setText(jsonObject.getString("Humedad"));
                        eEstadoPlU.setText(jsonObject.getString("Estado"));
                        eLugarPlU.setText(jsonObject.getString("Lugar"));
                        etPlUTempV.setText(jsonObject.getString("Temperatura"));

                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Para ver error en cado de fallo
                Log.e("Volley", "Error: " + error.toString());
                //error.printStackTrace();
                Toast.makeText(getApplicationContext(), "ERROR DE CONEXIÓN", Toast.LENGTH_SHORT).show();
            }
        }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    //Funcion para cargar foto
    private void cargarFLocal(int idPlanta) {
        try {
            AdministradorSQLite admin = new AdministradorSQLite(this);
            SQLiteDatabase db = admin.getReadableDatabase();

            // Consulta: buscamos la ruta donde el id_referencia coincida
            Cursor cursor = db.rawQuery("SELECT ruta_foto FROM fotos WHERE id_referencia = ? AND tipo_foto = 'PLANTA' LIMIT 1",
                    new String[]{String.valueOf(idPlanta)});

            if (cursor.moveToFirst()) {
                String rutaUri = cursor.getString(0);

                Uri uriFoto = Uri.parse(rutaUri);

                imgPlUPlanta.setImageURI(uriFoto);

                imgPlUPlanta.setScaleType(ImageView.ScaleType.CENTER_CROP);

                Log.d("Verdi", "Foto cargada desde: " + rutaUri);
            } else {
                Log.d("Verdi", "No se encontró foto para el ID: " + idPlanta);
                imgPlUPlanta.setImageDrawable(Drawable.createFromPath("gallery_thumb"));
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            Log.e("Verdi", "Error al cargar foto local: " + e.getMessage());
        }
    }

    //Función para eliminar planta
    private void eliminarPlanta(String URL){
        //Creacion de la peticion al servidor con el metodo POST
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Llamado ala funcion para borrar el campo de la foto de SQLite
                eliminarFLocal(idP);

                Toast.makeText(getApplicationContext(), "Operación exitosa",Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(),Toast.LENGTH_LONG).show();
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                //Metodo Map que manda los datos de la peticion al servidor extrallendo informacion del editText para guardar en la base
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("idPu", String.valueOf(idP));
                parametros.put("usuario", correoU);

                return parametros;
            }
        };

        //Extension de tiempo de espera para la respuesta del servidor
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000, // Timeout en ms
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    //Funcion para eliminar la foto
    private void eliminarFLocal(int idPlanta) {
        try {
            AdministradorSQLite admin = new AdministradorSQLite(this);
            SQLiteDatabase db = admin.getWritableDatabase();

            // Borramos la fila donde el id_referencia sea el de esta planta
            int filasBorradas = db.delete("fotos", "id_referencia = ? AND tipo_foto = 'PLANTA'",
                    new String[]{String.valueOf(idPlanta)});

            db.close();
            Log.d("Verdi", "Registros de fotos eliminados en SQLite: " + filasBorradas);
        } catch (Exception e) {
            Log.e("Verdi", "Error al eliminar foto local: " + e.getMessage());
        }
    }

}