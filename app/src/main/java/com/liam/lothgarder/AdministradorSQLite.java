package com.liam.lothgarder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class AdministradorSQLite extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "VerdiDB";
    private static final int DATABASE_VERSION = 1;

    public AdministradorSQLite(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Metodo para crear la tabla de fotos en SQLite
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE fotos (" +
                "id_foto INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ruta_foto TEXT, " +
                "id_referencia INTEGER, " +
                "tipo_foto TEXT)");
    }

    //Metodo para actualizar el ID de Planta
    public void actualizarIdPu(int idServidor, String tipo) {
        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "UPDATE fotos SET id_referencia = " + idServidor +
                " WHERE id_foto = (SELECT MAX(id_foto) FROM fotos WHERE id_referencia = 0 AND tipo_foto = '" + tipo + "')";
        db.execSQL(sql);
        db.close();
    }

    //Metodo para eliminar la DB
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS fotos");
        onCreate(db);
    }
}
