package com.pedidosiker.demo.service;

import com.pedidosiker.demo.model.Usuario;
import com.pedidosiker.demo.model.Cliente;
import com.pedidosiker.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Cliente registrarCliente(Cliente datos) {
        return usuarioRepository.save(datos);
    }

    public Usuario iniciarSesion(String email, int contrasena) {
        Optional<Usuario> user = usuarioRepository.findByEmail(email);
        if (user.isPresent() && user.get().getContrasena() == contrasena) {
            return user.get();
        }
        return null;
    }

    public Usuario buscarPorId(int id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public void actualizarUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    public boolean eliminarUsuario(int id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }
}