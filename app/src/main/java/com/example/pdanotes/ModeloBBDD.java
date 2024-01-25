package com.example.pdanotes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ModeloBBDD {
    public SQLiteDatabase getCon(Context context){
        SQLite conBBDD = new SQLite(context, "BBDD_PDA_Notes", null, 1);
        SQLiteDatabase sqLiteDatabase = conBBDD.getWritableDatabase();
        return  sqLiteDatabase;
    }
    boolean insertarNotas(Context context, Nota datos){
        boolean resultado;
        String sentencia = "INSERT INTO notas (titulo, nota, correo) VALUES('"+datos.getTitulo()+"', '"+datos.getNota()+"', '"+datos.getCorreo()+"')";
        SQLiteDatabase sqLiteDatabase = this.getCon(context);
        try{
            sqLiteDatabase.execSQL(sentencia);
            resultado = true;
        }catch (Exception e){
            resultado = false;
        }
        return resultado;
    }
    boolean insertarUsuario(Context context, Usuario datos){
        boolean resultado;
        String sentencia = "INSERT INTO usuarios (correo, nombre, password) VALUES('"+datos.getCorreo()+"', '"+datos.getNombre()+"', '"+datos.getPassword()+"')";
        SQLiteDatabase sqLiteDatabase = this.getCon(context);
        try{
            sqLiteDatabase.execSQL(sentencia);
            resultado = true;
        }catch (Exception e){
            resultado = false;
        }
        return resultado;
    }
    String[] selectUsuario(Context context, String correo){
        String[] resultado = new String[2];
        SQLiteDatabase sqLiteDatabase = this.getCon(context);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT nombre, password FROM usuarios WHERE correo=?", new String[]{correo});
        if(cursor.moveToFirst()){
            do{
                for(int i=0;i<resultado.length;i++) {
                    resultado[i] = cursor.getString(i);
                }
            }while (cursor.moveToNext());
        }
        cursor.close();
        return resultado;
    }
}
