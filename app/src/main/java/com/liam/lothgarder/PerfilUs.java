package com.liam.lothgarder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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

public class PerfilUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_perfil_us);

        //Creacion de la instancia de las preferencias(informacion de sesion)
        SharedPreferences preferences = getSharedPreferences("guardarSesion", Context.MODE_PRIVATE);
        //Llamada al metodo de buscar usuario para mostrar su informacion usando la info de la prefencia de correo
        buscarUsuario("http://10.116.133.114:80/lothgarder/buscarU.php?correo="+preferences.getString("Correo",""));

        //Accion de Boton para salir de Perfil Usuario a la Pantalla Principal
        Button buUtoPrin = findViewById(R.id.buUtoPrin);
        buUtoPrin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PerfilUs.this, "Botón presionado", Toast.LENGTH_LONG).show();
                Intent intRtoPrin = new Intent(PerfilUs.this, PantallaPrin.class);
                startActivity(intRtoPrin);
            }
        });

        /*
        PRUEBAS/PROVICIONALES
        Button buBus = findViewById(R.id.buBus);
        buBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView eNomPU = findViewById(R.id.euNomPU);
                EditText edCorr = findViewById(R.id.edCorr);

                Toast.makeText(PerfilUs.this, "Buscando usuario", Toast.LENGTH_LONG).show();
                SharedPreferences preferences = getSharedPreferences("guardarSesion", Context.MODE_PRIVATE);

                buscarUsuario("http://10.116.133.114:80/lothgarder/buscarU.php?correo="+preferences.getString("Correo",""));
                //buscarUsuario("http://10.0.2.2/lothgarder/buscar.php?correo="+edCorr.getText().toString());
            }
        });

        Modo de variable
        TextView eNomPU = findViewById(R.id.eNomPU);
        TextView eEdadPU = findViewById(R.id.eEdadPU);
        TextView eCorrPU = findViewById(R.id.eCorrPU);
        TextView eContraPU = findViewById(R.id.eContraPU);

        String nombrePU = getIntent().getExtras().getString("Nombre");
        String edadPU = getIntent().getExtras().getString("Edad");
        String correoPU = getIntent().getExtras().getString("Correo");
        String contraPU = getIntent().getExtras().getString("Contra");

        eNomPU.setText(nombrePU);
        eEdadPU.setText(edadPU);
        eCorrPU.setText(correoPU);
        eContraPU.setText(contraPU);*/

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    //Metodo para buscar usuario
    private void buscarUsuario(String URL) {

        TextView eNomPU = findViewById(R.id.euNomPU);
        TextView eEdadPU = findViewById(R.id.euEdadPU);
        TextView eCorrPU = findViewById(R.id.euCorrPU);
        TextView eContraPU = findViewById(R.id.euContraPU);

        //Creacion de la peticion
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int reco = 0; reco < response.length(); reco++) {
                    try {

                        jsonObject = response.getJSONObject(reco);
                        eCorrPU.setText(jsonObject.getString("Correo"));
                        eNomPU.setText(jsonObject.getString("Nombre"));
                        eEdadPU.setText(jsonObject.getString("Edad"));
                        eContraPU.setText(jsonObject.getString("Contraseña"));

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