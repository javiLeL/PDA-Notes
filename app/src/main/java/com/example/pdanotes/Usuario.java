package com.example.pdanotes;

import java.math.BigInteger;
import java.security.MessageDigest;

public class Usuario {
    private String correo, nombre, password;

    public Usuario(String correo, String nombre, String password) {
        this.correo = correo;
        this.nombre = nombre;
        this.password = getMD5(password);
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = getMD5(password);
    }
    /**
     * Codigo que "encripta" en MD5.
     * @param input
     * @return
     */
    public static String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}