package com.example.pdanotes.bbdd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * @author JaviLeL
 * @version 1.0.1
 */
public class SQLite extends SQLiteOpenHelper {
    // Se crean todas las bases de datos con sus respectivas sentencias
    // Para los usuarios
    final String TABLA_USUARIOS = "CREATE TABLE usuarios(correo TEXT PRIMARY KEY, nombre TEXT, telefono TEXT, password VARCHAR(32))";
    // Para las notas
    final String TABLA_NOTAS = "CREATE TABLE notas(id INTEGER PRIMARY KEY AUTOINCREMENT, titulo TEXT, nota LONGTEXT, correo TEXT, FOREIGN KEY (correo) REFERENCES usuarios(correo) ON DELETE CASCADE)";
    // Para los eventos
    final String TABLA_EVETOS = "CREATE TABLE eventos(id INTEGER PRIMARY KEY AUTOINCREMENT, titulo TEXT, tipo TEXT, fecha DATE, hora TIME, correo TEXT, descripcion TEXT, FOREIGN KEY (correo) REFERENCES usuarios(correo) ON DELETE CASCADE)";

    /**
     * Metodo esencial para el funcionamiento de SQLite
     * @param context
     * @param name
     * @param factory
     * @param version
     */
    public SQLite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * Se lanzan las sentencias de cada una de las tablas de la base de datos
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(TABLA_USUARIOS);
            db.execSQL(TABLA_NOTAS);
            db.execSQL(TABLA_EVETOS);
        }catch (Exception e){

        }
    }

    /**
     * Metodo esencial para el funcionamiento de SQLite
     * @param db The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
