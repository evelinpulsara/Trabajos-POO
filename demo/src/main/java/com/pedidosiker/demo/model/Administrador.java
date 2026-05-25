package com.pedidosiker.demo.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Administrador extends Usuario {
    private String rol;
    @ElementCollection
    private List<String> permisos;

    public Administrador() {}

    public void gestionarProducto() {}
    public void gestionarUsuario() {}
    public void verReportes() {}
    protected boolean validarPermiso(String accion) { return true; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public List<String> getPermisos() { return permisos; }
    public void setPermisos(List<String> permisos) { this.permisos = permisos; }
}