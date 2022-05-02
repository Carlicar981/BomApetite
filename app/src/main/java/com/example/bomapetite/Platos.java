package com.example.bomapetite;

import java.io.Serializable;

public class Platos implements Serializable {
    private String nombre;
    private String descipcion;
    private int foto;
    private String tipo;
    private String categoria;
    private int cantidad;
    private float precio;
    private int totalCarrito;

    public Platos(String nombre, String descipcion, int foto, String tipo, String categoria, int cantidad, float precio, int totalCarrito) {
        this.nombre = nombre;
        this.descipcion = descipcion;
        this.foto = foto;
        this.tipo = tipo;
        this.categoria = categoria;
        this.cantidad = cantidad;
        this.precio = precio;
        this.totalCarrito = totalCarrito;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getTotalCarrito() {
        return totalCarrito;
    }

    public void setTotalCarrito(int totalCarrito) {
        this.totalCarrito = totalCarrito;
    }

    public float precio(){
        return precio*cantidad;
    }
    public String getPrecioCantidad(){
            return String.valueOf(precio*cantidad);
    }
    public String getPrecio() {
        return String.valueOf(precio);
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescipcion() {
        return descipcion;
    }

    public void setDescipcion(String descipcion) {
        this.descipcion = descipcion;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCantidad() {
        return String.valueOf(cantidad);
    }
    public int obtenerCantidad(){return cantidad;}
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
