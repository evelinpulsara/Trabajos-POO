package com.pedidosiker.demo.model;

import jakarta.persistence.*;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_pago")
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "tipo_pago" // Este campo en el JSON de Postman dirá si es TARJETA o PAGO normal
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = PagoTarjeta.class, name = "TARJETA")
})
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double monto;
    private Date fecha;
    private String metodo;
    private String estado;
    private String transaccionId;

    public Pago() {}

    public boolean procesarPago() { return true; }
    public boolean reembolsar() { return true; }
    public boolean validarTarjeta() { return true; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public String getMetodo() { return metodo; }
    public void setMetodo(String metodo) { this.metodo = metodo; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getTransaccionId() { return transaccionId; }
    public void setTransaccionId(String transaccionId) { this.transaccionId = transaccionId; }
}