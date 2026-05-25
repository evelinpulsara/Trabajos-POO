package com.taller.pagos.model;

import java.time.LocalDate;

public class Pago {

    private Long id;
    private double monto;
    private LocalDate fecha;
    private String estado;
    private String pedido;

    public Pago(Long id, double monto, String pedido) {
        this.id = id;
        this.monto = monto;
        this.pedido = pedido;
        this.fecha = LocalDate.now();
        this.estado = "PENDIENTE";
        validarMonto();
    }

    // GETTERS públicos (solo lectura)
    public Long getId() {
        return id;
    }

    public double getMonto() {
        return monto;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getEstado() {
        return estado;
    }

    public String getPedido() {
        return pedido;
    }

    // 🔒 Método interno de validación
    private void validarMonto() {
        if (this.monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que cero");
        }
    }

    // 🔥 Método público obligatorio
    public void procesarPago() {
        this.estado = "PROCESADO";
    }
}