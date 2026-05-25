package com.pedidosiker.demo.controller;

import com.pedidosiker.demo.model.Pedido;
import com.pedidosiker.demo.model.Usuario;
import com.pedidosiker.demo.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping("/realizar")
    public ResponseEntity<?> realizarPedido(@RequestBody Pedido pedido) {
        try {
            // Validar que se haya ingresado un método de pago
            if (pedido.getMetodoPago() == null) {
                return ResponseEntity.badRequest().body("El campo 'metodoPago' es obligatorio.");
            }
            return ResponseEntity.ok(pedidoService.crearPedido(pedido));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al procesar el pedido: " + e.getMessage());
        }
    }

    // Manejador específico para cuando envían valores no válidos en el Enum (como "Daviplata")
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleInvalidEnum(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body("Método de pago no permitido. Solo se acepta 'EFECTIVO' o 'TARJETA'.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> verPedido(@PathVariable int id) {
        // Lógica para ver un pedido específico
        return ResponseEntity.ok("Detalle del pedido " + id);
    }

    @DeleteMapping("/cancelar/{id}")
    public ResponseEntity<?> cancelarPedido(@PathVariable int id) {
        if (pedidoService.cancelarPedido(id)) {
            return ResponseEntity.ok("Pedido cancelado");
        }
        return ResponseEntity.badRequest().body("No se pudo cancelar");
    }

    @PostMapping("/historial")
    public ResponseEntity<?> historialPedido(@RequestBody Usuario usuario) {
        return ResponseEntity.ok(pedidoService.obtenerPedidosUsuario(usuario));
    }
}