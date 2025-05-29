package com.liam.lothgarder;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_planta_e);

        Intent intent = getIntent();
        int PKpl = intent.getIntExtra("PKpl",0);

        //Llamada al metodo de buscar usuario para mostrar su informacion usando la info de la prefencia de correo
        buscarPlantaE("http://10.116.133.114:80/lothgarder/buscarP.php?planta="+PKpl);

        Button bplePletoEn = findViewById(R.id.bplePletoEn);
        bplePletoEn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intPletoEn = new Intent(PlantaE.this, Enci.class);
                startActivity(intPletoEn);
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

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

                        String nombre = jsonObject.getString("Nombre");
                        String desc = jsonObject.getString("Descripcion");
                        String cuid = jsonObject.getString("Cuidados");
                        String usos = jsonObject.getString("Uso");

                        String imagenBase64 = jsonObject.getString("Imagen");
                        nombre = nombre.replace("\\n", "\n\n");  // Reemplaza \\n por salto de línea real
                        desc = desc.replace("\\n", "\n\n");
                        cuid = cuid.replace("\\n", "\n\n");
                        usos = usos.replace("\\n", "\n\n");

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
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.e("Volley", "Error: " + error.toString());
                //error.printStackTrace();
                Toast.makeText(getApplicationContext(), "ERROR DE CONEXIÓN", Toast.LENGTH_SHORT).show();
            }
        }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

}