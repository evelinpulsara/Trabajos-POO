package com.pedidosiker.demo.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Envio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String direccion;
    private String estado;
    
    @Temporal(TemporalType.DATE)
    private Date fechaEnvio;
    
    @Temporal(TemporalType.DATE)
    private Date fechaEntrega;

    // Constructor vacío (obligatorio para JPA)
    public Envio() {}

    // Constructor con parámetros
    public Envio(int id, String direccion, String estado, Date fechaEnvio, Date fechaEntrega) {
        this.id = id;
        this.direccion = direccion;
        this.estado = estado;
        this.fechaEnvio = fechaEnvio;
        this.fechaEntrega = fechaEntrega;
    }

    // Métodos de lógica del diagrama
    public int calcularTiempoEntrega() {
        // Lógica básica de ejemplo: retornar 3 días
        return 3;
    }

    public void actualizarEstado(String nuevoEstado) {
        this.estado = nuevoEstado;
    }

    // --- Getters y Setters ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(Date fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }
}