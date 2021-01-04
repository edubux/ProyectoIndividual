package com.example.appsindividual;

public class Local {

    public String nombre;
    public String tipo;
    public String ubicacion;
    public  String detalle;
    public String nombreCreador;
    public String uidCreador;
    public String emailCreador;
    public  String estado;


    public Local(String nombre, String tipo, String ubicacion, String detalle) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.ubicacion = ubicacion;
        this.detalle = detalle;
    }

    public Local(String nombre, String tipo, String ubicacion, String detalle, String nombreCreador,
                 String uidCreador, String emailCreador, String estado) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.ubicacion = ubicacion;
        this.detalle = detalle;
        this.nombreCreador = nombreCreador;
        this.uidCreador = uidCreador;
        this.emailCreador = emailCreador;
        this.estado = estado;
    }

    public Local() {
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
