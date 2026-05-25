package com.pedidosiker.demo.model;

import jakarta.persistence.*;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
    private String email;
    private int contrasena;
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    private String telefono;
    private String direccion;

    // Nuevo atributo para restringir el método de pago en BD y Postman
    @Enumerated(EnumType.STRING)
    private MetodoPago metodoPago;

    public Usuario() {}

    public Usuario(int id, String nombre, String email, int contrasena, Date fechaRegistro, String telefono, String direccion, MetodoPago metodoPago) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.contrasena = contrasena;
        this.fechaRegistro = fechaRegistro;
        this.telefono = telefono;
        this.direccion = direccion;
        this.metodoPago = metodoPago;
    }

    // Métodos lógicos del diagrama
    public boolean autenticar() { return true; }
    public void actualizarPerfil() { }
    protected boolean validarEmail() { return email != null && email.contains("@"); }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getContrasena() { return contrasena; }
    public void setContrasena(int contrasena) { this.contrasena = contrasena; }

    public Date getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(Date fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public MetodoPago getMetodoPago() { return metodoPago; }
    public void setMetodoPago(MetodoPago metodoPago) { this.metodoPago = metodoPago; }
}