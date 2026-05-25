package com.pedidosiker.demo.repository;

import com.pedidosiker.demo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    // Método útil para el login que mencionas en tu diagrama
    Optional<Usuario> findByEmail(String email);
}