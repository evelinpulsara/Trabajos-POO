package com.pedidosiker.demo.model;

import jakarta.persistence.Entity;

@Entity
public class Cliente extends Usuario {
    private int puntos;

    public Cliente() {}

    public void acumularPuntos(int puntos) { this.puntos += puntos; }
    public double canjearPuntos() { return (double) puntos; }

    public int getPuntos() { return puntos; }
    public void setPuntos(int puntos) { this.puntos = puntos; }
}