package com.example.pdanotes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLite extends SQLiteOpenHelper {

    final String TABLA_USUARIOS = "CREATE TABLE usuarios(correo TEXT PRIMARY KEY, nombre VARCHAR(50), password VARCHAR(32))";
    final String TABLA_NOTAS = "CREATE TABLE notas(id INTEGER PRIMARY KEY AUTOINCREMENT, titulo TEXT, nota LONGTEXT, correo TEXT, FOREIGN KEY (correo) REFERENCES usuarios(correo) ON DELETE CASCADE)";
    final String TABLA_EVETOS = "CREATE TABLE eventos(id INTEGER PRIMARY KEY AUTOINCREMENT, titulo TEXT, tipo TEXT, fecha DATE, hora TIME, correo TEXT, descripcion TEXT, FOREIGN KEY (correo) REFERENCES usuarios(correo) ON DELETE CASCADE)";
    public SQLite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(TABLA_USUARIOS);
            db.execSQL(TABLA_NOTAS);
            db.execSQL(TABLA_EVETOS);
        }catch (Exception e){

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
