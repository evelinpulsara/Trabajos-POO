package com.pedidosiker.demo.controller;

import com.pedidosiker.demo.model.Pago;
import com.pedidosiker.demo.model.PagoTarjeta;
import com.pedidosiker.demo.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @PostMapping("/procesar")
    public ResponseEntity<?> procesarPago(@RequestBody Pago pago) {
        try {
            // Validamos el método de pago que viene en el cuerpo del JSON
            if (pago.getMetodo() == null) {
                return ResponseEntity.badRequest().body("El método de pago es obligatorio.");
            }

            String metodoNormalizado = pago.getMetodo().toUpperCase();
            if (!metodoNormalizado.equals("EFECTIVO") && !metodoNormalizado.equals("TARJETA")) {
                return ResponseEntity.badRequest().body("Método de pago no permitido. Solo se acepta 'EFECTIVO' o 'TARJETA'.");
            }

            // Si es tarjeta, validamos que traiga el número dentro del objeto
            if (metodoNormalizado.equals("TARJETA")) {
                if (!(pago instanceof PagoTarjeta) || ((PagoTarjeta) pago).getNumeroTarjeta() == null || ((PagoTarjeta) pago).getNumeroTarjeta().trim().isEmpty()) {
                    return ResponseEntity.badRequest().body("El número de tarjeta es obligatorio para pagos con tarjeta.");
                }
            }

            pago.setMetodo(metodoNormalizado);
            pago.setEstado("COMPLETADO");
            if (pago.getFecha() == null) {
                pago.setFecha(new java.util.Date());
            }

            // Guardamos directamente el objeto que ya viene mapeado correctamente
            return ResponseEntity.ok(pagoService.procesarPagoDirecto(pago));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al procesar el pago: " + e.getMessage());
        }
    }

    @GetMapping("/verificar/{id}")
    public ResponseEntity<?> verificarEstadoPago(@PathVariable int id) {
        return ResponseEntity.ok(pagoService.verificarPago(id));
    }

    @PostMapping("/reembolsar/{id}")
    public ResponseEntity<?> solicitarReembolso(@PathVariable int id) {
        if (pagoService.reembolsarPago(id)) {
            return ResponseEntity.ok("Reembolso procesado");
        }
        return ResponseEntity.badRequest().body("Error en el reembolso");
    }
}