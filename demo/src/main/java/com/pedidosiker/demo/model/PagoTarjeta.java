package com.pedidosiker.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.DiscriminatorValue;

@Entity
@DiscriminatorValue("TARJETA")
public class PagoTarjeta extends Pago {
    private String numeroTarjeta;

    public PagoTarjeta() {}

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }
}