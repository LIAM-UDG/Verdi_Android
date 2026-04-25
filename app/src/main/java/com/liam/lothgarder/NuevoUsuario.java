package com.liam.lothgarder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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


public class NuevoUsuario extends AppCompatActivity {

    //Definicion de variables globales
    private RequestQueue requestQueue;
    private EditText edNUNombre, edNUEdad, edNUCorreo ,edNUContra;
    private Button btnRtoM, btnNUGuar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_nuevo_usuario);

        Toast.makeText(NuevoUsuario.this, "Pantalla Nuevo Usuario", Toast.LENGTH_SHORT).show();

        //Accion del boton para cambiar de la pantalla de registro a la main
        btnRtoM = findViewById(R.id.btnRtoM);
        btnRtoM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intRMain = new Intent(NuevoUsuario.this, MainActivity.class);
                startActivity(intRMain);
                finish();
            }
        });

        //Accion del boton para guardar el registro del nuevo usuario
        btnNUGuar = findViewById(R.id.btnNUGuar);
        btnNUGuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Paso de la componentes de xml a java
                edNUNombre = findViewById(R.id.edNUNombre);
                edNUCorreo = findViewById(R.id.edNUCorreo);
                edNUContra = findViewById(R.id.edNUContra);
                //Extraccion de los string de los campos del xml
                String nombre = edNUNombre.getText().toString().trim();
                String correo = edNUCorreo.getText().toString().trim();
                String contrasena = edNUContra.getText().toString();

                // Patrón Regex: Mínimo 8 caracteres, al menos 1 mayúscula, 1 minúscula, 1 número y 1 especial.
                String patronC = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*-]).{8,}$";
                //Patrón para evaluar el nombre valido, todas las letras y sin numeros
                String nombreV = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ][a-zA-ZáéíóúÁÉÍÓÚñÑ ]{1,99}$";

                //Funcion para validar que el nombre sea valido
                if (!nombre.matches(nombreV)) {
                    Toast.makeText(NuevoUsuario.this, "Nombre inválido.", Toast.LENGTH_LONG).show();
                    edNUNombre.setError("Nombre inválido solo se permiten letras");
                    return;
                }

                //Funcion para validar el correo, que contenga los componentes de uno
                if (correo.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
                    Toast.makeText(NuevoUsuario.this, "Introduce un correo electrónico válido.", Toast.LENGTH_LONG).show();
                    edNUCorreo.setError("Correo inválido");
                    return;
                }

                //Funcion para validar que la contraseña sea segura
                if (!contrasena.matches(patronC)) {
                    //Ventana emergente para indicar el formato de contraseña
                    new androidx.appcompat.app.AlertDialog.Builder(NuevoUsuario.this)
                            .setTitle("Contraseña poco segura")
                            .setMessage("La contraseña debe tener:\n\n" +
                                    "• Mínimo 8 caracteres.\n" +
                                    "• Al menos 1 mayúscula y 1 minúscula.\n" +
                                    "• Al menos 1 número.\n" +
                                    "• Al menos 1 carácter especial (!@#$%^&*).")
                            .setPositiveButton("Aceptar", null)
                            .show();
                    edNUContra.setError("Contraseña débil");
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
                Toast.makeText(getApplicationContext(), "Respuesta: " + response, Toast.LENGTH_LONG).show();
                //Pasar a pantalla de ingresar usuario tras registrar
                Intent intRtoIn = new Intent(NuevoUsuario.this, InUsuario.class);
                startActivity(intRtoIn);
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
                edNUNombre = findViewById(R.id.edNUNombre);
                edNUEdad = findViewById(R.id.edNUEdad);
                edNUCorreo = findViewById(R.id.edNUCorreo);
                edNUContra = findViewById(R.id.edNUContra);

                //Metodo Map que manda los datos de la peticion al servidor extrallendo informacion del editText para guardar en la base
                Map<String, String> parametros = new HashMap<String, String>();
                //Dentro de map ademas de mandar los datos tambien los extra en cadena del editext
                parametros.put("correo", edNUCorreo.getText().toString());
                parametros.put("nombre", edNUNombre.getText().toString());
                parametros.put("edad", edNUEdad.getText().toString());
                parametros.put("contrasena", edNUContra.getText().toString());

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