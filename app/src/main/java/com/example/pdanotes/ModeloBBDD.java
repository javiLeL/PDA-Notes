package com.example.pdanotes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ModeloBBDD {
    public SQLiteDatabase getCon(Context context){
        SQLite conBBDD = new SQLite(context, "BBDD_PDA_Notes", null, 1);
        SQLiteDatabase sqLiteDatabase = conBBDD.getWritableDatabase();
        return  sqLiteDatabase;
    }
    void deleteEventos(Context context, int id){
        String sentencia = "DELETE FROM eventos WHERE id="+id;
        SQLiteDatabase sqLiteDatabase = this.getCon(context);
        try{
            sqLiteDatabase.execSQL(sentencia);
        }catch (Exception e){
        }
    }
    void deleteNotas(Context context, int id){
        String sentencia = "DELETE FROM notas WHERE id="+id;
        SQLiteDatabase sqLiteDatabase = this.getCon(context);
        try{
            sqLiteDatabase.execSQL(sentencia);
        }catch (Exception e){
        }
    }
    void updateEvento(Context context, Evento dato){
        String sentencia = "UPDATE eventos SET titulo='"+dato.getTitulo()+"', tipo='"+dato.getTipo()+"', fecha='"+dato.getFecha().toString()+"', hora='"+dato.getHora().toString()+"', descripcion='"+dato.getDescripcion()+"' WHERE id="+dato.getId();
        SQLiteDatabase sqLiteDatabase = this.getCon(context);
        try{
            sqLiteDatabase.execSQL(sentencia);
        }catch (Exception e){
        }
    }
    List<Evento> selectEventos(Context context, String correo, String pass){
        List<Evento> resultado = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getCon(context);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT id, titulo, tipo, fecha, hora, descripcion FROM eventos WHERE correo=?", new String[]{correo});
        if(cursor.moveToFirst()){
            do{
                Integer id = cursor.getInt(0);
                String titulo = cursor.getString(1);
                String tipo = cursor.getString(2);
                String fechaG = cursor.getString(3);
                String horaG = cursor.getString(4);
                String descripcion = cursor.getString(5);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    LocalDate fecha = LocalDate.of(Integer.parseInt(fechaG.split("-")[0]),  Integer.parseInt(fechaG.split("-")[1]), Integer.parseInt(fechaG.split("-")[2]));
                    LocalTime hora = LocalTime.of(Integer.parseInt(horaG.split(":")[0]), Integer.parseInt(horaG.split(":")[1]));
                    resultado.add(new Evento(id, titulo, tipo, fecha, hora, descripcion, correo));
                }
            }while (cursor.moveToNext());
        }
        cursor.close();
        return resultado;
    }
    boolean insertarEvento(Context context, Evento datos){
        boolean resultado;
        String sentencia = "INSERT INTO eventos(titulo, tipo, fecha, hora, descripcion, correo) VALUES('"+datos.getTitulo()+"', '"+datos.getTipo()+"', '"+datos.getFecha().toString()+"', '"+datos.getHora().toString()+"', '"+datos.getDescripcion()+"', '"+datos.getCorreo()+"')";
        SQLiteDatabase sqLiteDatabase = this.getCon(context);
        try{
            sqLiteDatabase.execSQL(sentencia);
            resultado = true;
        }catch (Exception e){
            resultado = false;
        }
        return resultado;
    }

    void updateNota(Context context, int id, Nota nota){
        String sentencia = "UPDATE notas SET titulo='"+nota.getTitulo()+"', nota='"+nota.getNota()+"', correo='"+nota.getCorreo()+"' WHERE id="+id;
        SQLiteDatabase sqLiteDatabase = this.getCon(context);
        try{
            sqLiteDatabase.execSQL(sentencia);
        }catch (Exception e){
        }
    }
    List<Nota> selectNotas(Context context, String correo, String pass){
        List<Nota> resultado = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getCon(context);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT id, titulo, nota FROM notas WHERE correo=?", new String[]{correo});
        if(cursor.moveToFirst()){
            do{
                Integer id = cursor.getInt(0);
                String titulo = cursor.getString(1);
                String nota = cursor.getString(2);
                resultado.add(new Nota(id, titulo, Nota.decrypt(nota, pass, pass), correo));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return resultado;
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
