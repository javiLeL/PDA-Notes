package com.example.pdanotes;

import java.time.LocalDate;
import java.time.LocalTime;

public class Evento {
    private Integer id;
    private String titulo, tipo, descripcion, correo;
    private  LocalDate fecha;
    private LocalTime hora;

    public Evento(String titulo, String tipo, LocalDate fecha, LocalTime hora, String descripcion, String correo) {
        this.titulo = titulo;
        this.tipo = tipo;
        this.fecha = fecha;
        this.hora = hora;
        this.descripcion = descripcion;
        this.correo = correo;
    }

    public Evento(Integer id, String titulo, String tipo, LocalDate fecha, LocalTime hora, String descripcion, String correo) {
        this.id = id;
        this.titulo = titulo;
        this.tipo = tipo;
        this.correo = correo;
        this.fecha = fecha;
        this.hora = hora;
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }
}
