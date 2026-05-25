package com.universidad.api_sistema.controller;

import com.universidad.api_sistema.model.Estudiante;
import com.universidad.api_sistema.service.RegistroService;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteController {
    private final RegistroService service;

    public EstudianteController(RegistroService service) {
        this.service = service;
    }
    @PostMapping
    public Map<String, String> crear(@RequestBody Estudiante estudiante) {
        Estudiante guardado = service.guardarEstudiante(estudiante);
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("estado", "Éxito");
        respuesta.put("mensaje", "Estudiante guardado");
        respuesta.put("notificacion", service.enviarCorreo(guardado.getNombre()));
        return respuesta;
    }
}