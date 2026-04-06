package com.liam.lothgarder;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PlantaE extends AppCompatActivity {

    //Definicion de variables globales
    private int PKpl;
    private Button bplePletoEn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_planta_e);

        Intent intent = getIntent();
        //Extraccion de la informacion del ID e la planta mediante el intent
        PKpl = intent.getIntExtra("PKpl",0);

        //Extraccion del link de dominio desde strings.xml
        String link_domain = getString(R.string.link_domain);

        //Llamado a la funcion para buscar la planta de enciclopedia
        buscarPlantaE(link_domain + "?accion=buscarP&planta="+PKpl);

        //Accion de boton para regresar a la enciclopedia
        bplePletoEn = findViewById(R.id.bplePletoEn);
        bplePletoEn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intPletoEn = new Intent(PlantaE.this, Enci.class);
                startActivity(intPletoEn);
                finish();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    //Funcion para buscar la planta de enciclopedia
    private void buscarPlantaE(String URL) {

        TextView epleTitulo = findViewById(R.id.epleTitulo);
        TextView epleDescText = findViewById(R.id.epleDescText);
        TextView epleCuidadText = findViewById(R.id.epleCuidadText);
        TextView epleUsoText = findViewById(R.id.epleUsoText);
        ImageView imPlan = findViewById(R.id.imPlan);


        //Creacion de la peticion
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int reco = 0; reco < response.length(); reco++) {
                    try {
                        jsonObject = response.getJSONObject(reco);

                        //Extraccion del texto del servidor
                        String nombre = jsonObject.getString("Nombre");
                        String desc = jsonObject.getString("Descripcion");
                        String cuid = jsonObject.getString("Cuidados");
                        String usos = jsonObject.getString("Uso");
                        String imagenBase64 = jsonObject.getString("Imagen");

                        nombre = nombre.replace("\\n", "\n\n");  // Reemplaza \\n por salto de línea real
                        desc = desc.replace("\\n", "\n\n");
                        cuid = cuid.replace("\\n", "\n\n");
                        usos = usos.replace("\\n", "\n\n");

                        //Asignacion del texto cargado a la etiqueta
                        epleTitulo.setText(nombre);
                        epleDescText.setText(desc);
                        epleCuidadText.setText(cuid);
                        epleUsoText.setText(usos);

                        // Decodificar imagen base64bits
                        byte[] imagenBytes = Base64.decode(imagenBase64, Base64.DEFAULT);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(imagenBytes, 0, imagenBytes.length);

                        imPlan.setImageBitmap(bitmap);

                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse != null) {
                    int statusCode = error.networkResponse.statusCode;
                    String responseBody = "";
                    if (error.networkResponse.data != null) {
                        responseBody = new String(error.networkResponse.data);
                    }
                    Log.e("VolleyError", "Código de error: " + statusCode);
                    Log.e("VolleyError", "Respuesta del servidor: " + responseBody);
                } else if (error.getCause() != null) {
                    Log.e("VolleyError", "Causa del error: " + error.getCause().getMessage());
                } else {
                    Log.e("VolleyError", "Error desconocido: " + error.toString());
                }
                Toast.makeText(getApplicationContext(), "ERROR DE CONEXIÓN", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public java.util.Map<String, String> getHeaders() {
                java.util.Map<String, String> headers = new java.util.HashMap<>();
                headers.put("User-Agent", "Mozilla/5.0 (Android)");
                headers.put("Accept", "application/json");
                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

}