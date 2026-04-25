package com.liam.lothgarder;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class PantallaPrin extends AppCompatActivity {

    private Button bpPtoU, bpPtoE, bpPtoSop, bpPtoPU, bpPtoEst, bpPtoMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pantalla_prin);

        //Llamada a la funcion de permiso de notifiaciones
        permisoNoti();

        //Accion de Boton para pasar de Pantalla Principal a Perfil de Usuario
        bpPtoU = findViewById(R.id.bpPtoU);
        bpPtoU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);

                Intent intPPerUs = new Intent(PantallaPrin.this, PerfilUs.class);
                startActivity(intPPerUs);

                v.postDelayed(() -> v.setEnabled(true), 1000); // 1 segundo
            }
        });

        //Accion de Boton para pasar de Pantalla principal a Enciclopedia
        bpPtoE = findViewById(R.id.bpPtoE);
        bpPtoE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intPEnci = new Intent(PantallaPrin.this, Enci.class);
                startActivity(intPEnci);
            }
        });

        //Accion de Boton de Pantalla principal a Plantas de Usuario
        bpPtoPU = findViewById(R.id.bpPtoPU);
        bpPtoPU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intPtoPu = new Intent(PantallaPrin.this, Plantas.class);
                startActivity(intPtoPu);
            }
        });

        //Accion de Boton de Pantalla principal a Sistema de Usuario
        bpPtoEst = findViewById(R.id.bpPtoEst);
        bpPtoEst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intPtoEst = new Intent(PantallaPrin.this, SistemaU.class);
                startActivity(intPtoEst);
            }
        });

        //Accion de Boton de Pantalla principal a Soporte
        bpPtoSop = findViewById(R.id.bpPtoSop);
        bpPtoSop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intPSop = new Intent(PantallaPrin.this, Soporte.class);
                startActivity(intPSop);
            }
        });

        //Botón de pantalla principal a Logros
        ImageView bpPtoL = findViewById(R.id.bpPtoL);
        bpPtoL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intPtoL = new Intent(PantallaPrin.this, LogrosP.class);
                startActivity(intPtoL);
            }
        });

        //Botón de pantalla principal a Búsqueda
        ImageView bpPtoB = findViewById(R.id.bpPtoB);
        bpPtoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PantallaPrin.this, "Click en búsqueda", Toast.LENGTH_SHORT).show();
                Intent intPtoB = new Intent(PantallaPrin.this, Busqueda.class);
                startActivity(intPtoB);
            }
        });

        //Accion de Boton para salir de tu sesion
        bpPtoMain = findViewById(R.id.bpPtoMain);
        bpPtoMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("guardarSesion", Context.MODE_PRIVATE);
                preferences.edit().clear().commit();

                Intent intent = new Intent(PantallaPrin.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    //Funcion para pedir permiso de notificaciones
    private void permisoNoti() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) !=
                    PackageManager.PERMISSION_GRANTED) {

                // Lanza el diálogo del sistema
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }
    }

    //Funcion de evaluacion de permision de notifiacion
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 101) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Las alertas están activadas", Toast.LENGTH_SHORT).show();
                // El usuario aceptó: Aquí podrías enviar la primera notificación de prueba
                //lanzarNotificacion();
            } else {
                // El usuario rechazó
                Toast.makeText(this, "Las alertas están desactivadas", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void lanzarNotificacion() {
        Intent intent = new Intent(this, PantallaPrin.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        // FLAG_IMMUTABLE es obligatorio en versiones modernas de Android
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE
        );

        //Pasarle el PendingIntent al Builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "Canal_Fallas")
                .setSmallIcon(R.drawable.planta)
                .setContentTitle("Alerta de Prueba")
                .setContentText("Toca aquí para ver los detalles.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setColor(Color.GREEN)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        //Mostrar la notificación
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            notificationManager.notify(2, builder.build());
        }
    }

}