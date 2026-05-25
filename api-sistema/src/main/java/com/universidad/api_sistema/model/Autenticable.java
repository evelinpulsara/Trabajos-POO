package com.universidad.api_sistema.model;

public interface Autenticable {
    boolean login(String usuario, String password);
}
