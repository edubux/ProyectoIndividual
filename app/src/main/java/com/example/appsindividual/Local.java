package com.example.appsindividual;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

public class Local {

    public String nombre;
    public String tipo;
    public String ubicacion;
    public  String detalle;
    public String nombreCreador;
    public String uidCreador;
    public String emailCreador;
    public  String estado;
    public StorageReference imagen;


    public Local(String nombre, String tipo, String ubicacion, String detalle,StorageReference imagen) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.ubicacion = ubicacion;
        this.detalle = detalle;
        this.imagen=imagen;
    }

    public Local(String nombre, String tipo, String ubicacion, String detalle, String nombreCreador,
                 String uidCreador, String emailCreador, String estado, StorageReference imagen) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.ubicacion = ubicacion;
        this.detalle = detalle;
        this.nombreCreador = nombreCreador;
        this.uidCreador = uidCreador;
        this.emailCreador = emailCreador;
        this.estado = estado;
        this.imagen=imagen;
    }

    public Local() {
    }

    public StorageReference getImagen() {
        return imagen;
    }

    public void setImagen(StorageReference imagen) {
        this.imagen = imagen;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }



    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getNombreCreador() {
        return nombreCreador;
    }

    public void setNombreCreador(String nombreCreador) {
        this.nombreCreador = nombreCreador;
    }

    public String getUidCreador() {
        return uidCreador;
    }

    public void setUidCreador(String uidCreador) {
        this.uidCreador = uidCreador;
    }

    public String getEmailCreador() {
        return emailCreador;
    }

    public void setEmailCreador(String emailCreador) {
        this.emailCreador = emailCreador;
    }
}
