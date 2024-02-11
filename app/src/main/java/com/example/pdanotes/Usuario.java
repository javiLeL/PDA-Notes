package com.example.pdanotes;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Clase DTO
 * @author JaviLeL
 * @version 1.0.1
 */
public class Usuario {
    private String correo, nombre, telefono, password;

    /**
     * Construcotor con todos los datos
     * @param correo
     * @param nombre
     * @param telefono
     * @param password
     */
    public Usuario(String correo, String nombre, String telefono, String password) {
        this.correo = correo;
        this.nombre = nombre;
        this.telefono = telefono;
        this.password = getMD5(password);
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
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
