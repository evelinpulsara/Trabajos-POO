package com.universidad.api_sistema.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity // <--- ESTO ES LO QUE FALTABA
public class Estudiante extends Persona implements Autenticable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // JPA necesita una llave primaria numérica
    
    private String codigo;

    public Estudiante() {} // Constructor vacío obligatorio

    public Estudiante(String nombre, String correo, String codigo) {
        super(nombre, correo);
        this.codigo = codigo;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    @Override
    public boolean login(String usuario, String password) { return true; }
}