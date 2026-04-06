package com.liam.lothgarder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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


public class NewUsuario extends AppCompatActivity {

    //Definicion de variables globales
    private RequestQueue requestQueue;
    private EditText edNom, edEdad, edCor ,edCon;
    private Button brRtoP, brGuar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_usuario);

        Toast.makeText(NewUsuario.this, "Pantalla usuario nuevo", Toast.LENGTH_LONG).show();

        //Accion del boton para cambiar de la pantalla de registro a la main
        brRtoP = findViewById(R.id.brRtoP);
        brRtoP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NewUsuario.this, "Llendo a la pantalla de inicio", Toast.LENGTH_SHORT).show();
                Intent intRMain = new Intent(NewUsuario.this, MainActivity.class);
                startActivity(intRMain);
                finish();
            }
        });

        //Accion del boton para guardar el registro del nuevo usuario
        brGuar = findViewById(R.id.brGuar);
        brGuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Paso de la componentes de xml a java
                edNom = findViewById(R.id.edrNom);
                edCor = findViewById(R.id.edrCor);
                edCon = findViewById(R.id.edrCon);
                //Extraccion de los string de los campos del xml
                String nombre = edNom.getText().toString().trim();
                String correo = edCor.getText().toString().trim();
                String contrasena = edCon.getText().toString();

                // Patrón Regex: Mínimo 8 caracteres, al menos 1 mayúscula, 1 minúscula, 1 número y 1 especial.
                String patronC = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*-]).{8,}$";
                //Patrón para evaluar el nombre valido, todas las letras y sin numeros
                String nombreV = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ][a-zA-ZáéíóúÁÉÍÓÚñÑ ]{1,99}$";

                //Funcion para validar que el nombre sea valido
                if (!nombre.matches(nombreV)) {
                    Toast.makeText(NewUsuario.this, "Nombre inválido.", Toast.LENGTH_LONG).show();
                    edNom.setError("Nombre inválido solo se permiten letras");
                    return;
                }

                //Funcion para validar el correo, que contenga los componentes de uno
                if (correo.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
                    Toast.makeText(NewUsuario.this, "Introduce un correo electrónico válido.", Toast.LENGTH_LONG).show();
                    edCor.setError("Correo inválido");
                    return;
                }

                //Funcion para validar que la contraseña sea segura
                if (!contrasena.matches(patronC)) {
                    //Ventana emergente para indicar el formato de contraseña
                    new androidx.appcompat.app.AlertDialog.Builder(NewUsuario.this)
                            .setTitle("Contraseña poco segura")
                            .setMessage("La contraseña debe tener:\n\n" +
                                    "• Mínimo 8 caracteres.\n" +
                                    "• Al menos 1 mayúscula y 1 minúscula.\n" +
                                    "• Al menos 1 número.\n" +
                                    "• Al menos 1 carácter especial (!@#$%^&*).")
                            .setPositiveButton("Aceptar", null)
                            .show();
                    edCon.setError("Contraseña débil");
                    return;
                }

                //Extraccion del link de dominio desde strings.xml
                String link_domain = getString(R.string.link_domain);

                //Llamado a la funcion para guardar usuario base en la nube
                guardarUsuario(link_domain + "?accion=insertarU");

            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    //Funcion para guardar el usuario
    private void guardarUsuario(String URL){
        //Creacion de la peticion al servidor con el metodo POST
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Respuesta del servidor: " + response, Toast.LENGTH_LONG).show();
                //Pasar a pantalla de ingresar usuario tras registrar
                Intent intRtoP = new Intent(NewUsuario.this, InUsuario.class);
                startActivity(intRtoP);

                finish();
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error: " +  error.toString(),Toast.LENGTH_LONG).show();
                Log.e("Volley Error", error.toString());
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                //Definicion local de editText contenedor del que se extrae la informacion
                edNom = findViewById(R.id.edrNom);
                edEdad = findViewById(R.id.edrEdad);
                edCor = findViewById(R.id.edrCor);
                edCon = findViewById(R.id.edrCon);

                //Metodo Map que manda los datos de la peticion al servidor extrallendo informacion del editText para guardar en la base
                Map<String, String> parametros = new HashMap<String, String>();
                //Dentro de map ademas de mandar los datos tambien los extra en cadena del editext
                parametros.put("correo", edCor.getText().toString());
                parametros.put("nombre", edNom.getText().toString());
                parametros.put("edad", edEdad.getText().toString());
                parametros.put("contrasena", edCon.getText().toString());

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