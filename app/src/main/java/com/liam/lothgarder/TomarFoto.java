package com.liam.lothgarder;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.core.UseCaseGroup;
import androidx.camera.core.ViewPort;
import androidx.camera.view.PreviewView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.common.util.concurrent.ListenableFuture;
import androidx.camera.lifecycle.ProcessCameraProvider;
import java.util.concurrent.ExecutionException;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TomarFoto extends AppCompatActivity {

    ImageCapture imageCapture;
    PreviewView vpTFPrevi;
    FloatingActionButton btnTFCapturar;
    private int idRelacionadoActual = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tomar_foto);

        ActivityCompat.requestPermissions(this, new String[]{
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, 101);

        vpTFPrevi = findViewById(R.id.vpTFPrevi);
        idRelacionadoActual = getIntent().getIntExtra("ID_PLANTA_EXTRA", 0);

        //Llamado a la funcion para iniciar la camara
        iniciarCam();

        //Funcion de boton para tomar foto
        btnTFCapturar = findViewById(R.id.btnTFCapturar);
        btnTFCapturar.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                //Llamado a la función para tomar la foto
                tomarFoto();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    //Funcion para iniciar la camara
    private void iniciarCam() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();

                ViewPort viewPort = vpTFPrevi.getViewPort();

                Preview preview = new Preview.Builder().build();
                preview.setSurfaceProvider(vpTFPrevi.getSurfaceProvider());

                imageCapture = new ImageCapture.Builder()
                        .setTargetRotation(getWindowManager().getDefaultDisplay().getRotation())
                        .setTargetAspectRatio(androidx.camera.core.AspectRatio.RATIO_4_3)
                        .build();

                CameraSelector cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;
                cameraProvider.unbindAll();

                UseCaseGroup useCaseGroup = new UseCaseGroup.Builder()
                        .addUseCase(preview)
                        .addUseCase(imageCapture)
                        .setViewPort(viewPort)
                        .build();

                cameraProvider.bindToLifecycle(this, cameraSelector, useCaseGroup);

            } catch (Exception e) {
                Log.e("Verdi", "Error: " + e.getMessage());
            }
        }, ContextCompat.getMainExecutor(this));
    }

    //Funcion para toma la foto
    private void tomarFoto() {
        if (imageCapture == null) return;

        String nombreArchivo = "Verdi_" + System.currentTimeMillis();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, nombreArchivo);
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");

        ImageCapture.OutputFileOptions outputOptions = new ImageCapture.OutputFileOptions
                .Builder(getContentResolver(), MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                .build();

        imageCapture.takePicture(outputOptions, ContextCompat.getMainExecutor(this),
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults results) {
                        String rutaFinal = results.getSavedUri().toString();
                        //Llamado a la funcion para registrar la ruta de la imagen en sqlite
                        registrarEnSQLite(rutaFinal);
                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        Log.e("Verdi", "Error específico: " + exception.getMessage());
                        Toast.makeText(TomarFoto.this, "Error: " + exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    //Función para registrar la ruta en sqlite
    private void registrarEnSQLite(String ruta) {
        try {
            AdministradorSQLite admin = new AdministradorSQLite(this);
            SQLiteDatabase db = admin.getWritableDatabase();

            db.delete("fotos", "tipo_foto = ? AND id_referencia = ?", new String[]{"PLANTA", "0"});

            ContentValues registro = new ContentValues();
            registro.put("ruta_foto", ruta);
            registro.put("id_referencia", idRelacionadoActual);
            registro.put("tipo_foto", "PLANTA");

            long resultado = db.insertOrThrow("fotos", null, registro); // insertOrThrow lanza error si falla
            db.close();

            Log.d("Verdi", "Inserción exitosa en ID: " + resultado);
            Toast.makeText(this, "Foto tomada", Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Log.e("Verdi", "ERROR AL GUARDAR: " + e.getMessage());
            Toast.makeText(this, "Error DB: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}