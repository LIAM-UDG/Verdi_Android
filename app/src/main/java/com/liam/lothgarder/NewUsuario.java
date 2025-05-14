package com.liam.lothgarder;

import android.content.Intent;
import android.content.SyncRequest;
import android.os.Bundle;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.DefaultRetryPolicy;


public class NewUsuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_usuario);
        Toast.makeText(NewUsuario.this, "Pantalla usuario nuevo", Toast.LENGTH_LONG).show();

        /*Definicion local
        EditText edNom = findViewById(R.id.edNom);
        EditText edEdad = findViewById(R.id.edEdad);
        EditText edCor = findViewById(R.id.edCor);
        EditText edCon = findViewById(R.id.edCon);
        */

        Button bMain = findViewById(R.id.bMain);
        bMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NewUsuario.this, "Botón presionado", Toast.LENGTH_LONG).show();
                Intent intRMain = new Intent(NewUsuario.this, MainActivity.class);
                startActivity(intRMain);
            }
        });

        Button bGuaNU = findViewById(R.id.bGuaNU);
        bGuaNU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Guardar datos en base?
                //Cambiar la IP si se reinicia la computadora
                ejecutarSerivcio("http://192.168.68.103:80/lothgarder/insertar.php");
                /*String caNom = edNom.getText().toString();
                String caEdad = edEdad.getText().toString();
                String caCor = edCor.getText().toString();
                String caCon = edCon.getText().toString();*/

                //Toast.makeText(NewUsuario.this, "Usuario Guardado", Toast.LENGTH_LONG).show();
                /*Intent intRGua = new Intent(NewUsuario.this, PerfilUs.class);
                intRGua.putExtra("Nombre", caNom);
                intRGua.putExtra("Edad", caEdad);
                intRGua.putExtra("Correo", caCor);
                intRGua.putExtra("Contra", caCon);

                startActivity(intRGua);*/
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void ejecutarSerivcio(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Respuesta del servidor: " + response, Toast.LENGTH_LONG).show();
                EditText edError = findViewById(R.id.error);
                edError.setText(response);
                //Toast.makeText(getApplicationContext(), "Operación exitosa",Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(),Toast.LENGTH_LONG).show();
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                //Definicion global?
                EditText edNom = findViewById(R.id.edNom);
                EditText edEdad = findViewById(R.id.edEdad);
                EditText edCor = findViewById(R.id.edCor);
                EditText edCon = findViewById(R.id.edCon);

                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("correo", edCor.getText().toString());
                parametros.put("nombre", edNom.getText().toString());
                parametros.put("edad", edEdad.getText().toString());
                parametros.put("contrasena", edCon.getText().toString());

                return parametros;
                //return super.getParams();
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

}