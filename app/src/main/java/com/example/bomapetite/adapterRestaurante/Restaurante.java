package com.example.bomapetite.adapterRestaurante;

import java.io.Serializable;

public class Restaurante implements Serializable {
    private String nombre;
    private String info;
    private String descripcion;
    private int foto;

    public Restaurante() {
    }

    public Restaurante(String nombre, String info,String descripcion, int foto) {
        this.nombre = nombre;
        this.info = info;
        this.descripcion=descripcion;
        this.foto = foto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getInfo() {
        return info;
    }
    public String getDescipcion() {
        return descripcion;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }
}
