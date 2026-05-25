package com.pedidosiker.demo.controller;

import com.pedidosiker.demo.model.Producto;
import com.pedidosiker.demo.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/listar")
    public ResponseEntity<List<Producto>> listarProductos(@RequestParam(required = false) String filtro) {
        if (filtro != null) {
            return ResponseEntity.ok(productoService.buscarPorNombre(filtro));
        }
        // Si no hay filtro, podrías implementar un findAll en el service
        return ResponseEntity.ok(productoService.buscarPorNombre("")); 
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProducto(@PathVariable int id) {
        // Asumiendo que implementamos un buscarPorId en ProductoService
        return ResponseEntity.ok(null); // Aquí llamarías a productoService.buscarPorId(id)
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Producto>> buscarProducto(@RequestParam String nombre) {
        return ResponseEntity.ok(productoService.buscarPorNombre(nombre));
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearProducto(@RequestBody Producto datos) {
        productoService.agregarProducto(datos);
        return ResponseEntity.ok("Producto creado");
    }
}