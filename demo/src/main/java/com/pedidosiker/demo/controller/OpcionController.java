package com.pedidosiker.demo.controller;

import com.pedidosiker.demo.dto.OpcionTreeDTO;
import com.pedidosiker.demo.model.Opcion;
import com.pedidosiker.demo.service.OpcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/opciones")
@CrossOrigin(origins = "*")
public class OpcionController {

    @Autowired
    private OpcionService opcionService;

    @GetMapping
    public ResponseEntity<List<Opcion>> getAllOpciones() {
        List<Opcion> opciones = opcionService.findAll();
        return ResponseEntity.ok(opciones);
    }

    @GetMapping("/menu")
    public ResponseEntity<List<OpcionTreeDTO>> getMenuHierarchy() {
        List<OpcionTreeDTO> menu = opcionService.getMenuHierarchy();
        return ResponseEntity.ok(menu);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Opcion> getOpcionById(@PathVariable Long id) {
        return opcionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Opcion> createOpcion(@RequestBody Opcion opcion) {
        Opcion nuevaOpcion = opcionService.save(opcion);
        return ResponseEntity.ok(nuevaOpcion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Opcion> updateOpcion(@PathVariable Long id, @RequestBody Opcion opcion) {
        return opcionService.findById(id)
                .map(opcionExistente -> {
                    opcionExistente.setNombre(opcion.getNombre());
                    opcionExistente.setPadreOpcionId(opcion.getPadreOpcionId());
                    opcionExistente.setRuta(opcion.getRuta());
                    opcionExistente.setIcono(opcion.getIcono());
                    opcionExistente.setOrden(opcion.getOrden());
                    opcionExistente.setActivo(opcion.getActivo());
                    Opcion opcionActualizada = opcionService.save(opcionExistente);
                    return ResponseEntity.ok(opcionActualizada);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOpcion(@PathVariable Long id) {
        if (opcionService.findById(id).isPresent()) {
            opcionService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
