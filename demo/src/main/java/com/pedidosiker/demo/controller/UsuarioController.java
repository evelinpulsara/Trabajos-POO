package com.pedidosiker.demo.controller;

import com.pedidosiker.demo.model.Usuario;
import com.pedidosiker.demo.model.Cliente;
import com.pedidosiker.demo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@RequestBody Cliente datos) {
        return ResponseEntity.ok(usuarioService.registrarCliente(datos));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam int contrasena) {
        Usuario user = usuarioService.iniciarSesion(email, contrasena);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(401).body("Credenciales incorrectas");
    }

    @GetMapping("/perfil/{id}")
    public ResponseEntity<?> obtenerPerfil(@PathVariable int id) {
        Usuario user = usuarioService.buscarPorId(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/actualizar")
    public ResponseEntity<?> actualizarPerfil(@RequestBody Usuario datos) {
        usuarioService.actualizarUsuario(datos);
        return ResponseEntity.ok("Perfil actualizado correctamente");
    }
}