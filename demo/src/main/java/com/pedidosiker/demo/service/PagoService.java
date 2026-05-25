package com.pedidosiker.demo.service;

import com.pedidosiker.demo.model.Pago;
import com.pedidosiker.demo.model.PagoTarjeta;
import com.pedidosiker.demo.model.Pedido;
import com.pedidosiker.demo.repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    public Pago procesarPago(Pedido pedido, String metodo, String numeroTarjeta) {
        // 1. Validar estrictamente que solo se permita EFECTIVO o TARJETA
        String metodoNormalizado = metodo.toUpperCase();
        if (!metodoNormalizado.equals("EFECTIVO") && !metodoNormalizado.equals("TARJETA")) {
            throw new IllegalArgumentException("Método de pago no permitido. Solo se acepta 'EFECTIVO' o 'TARJETA'.");
        }

        Pago pago;

        // 2. Si es Tarjeta, instanciamos la subclase PagoTarjeta para guardar el número
        if (metodoNormalizado.equals("TARJETA")) {
            if (numeroTarjeta == null || numeroTarjeta.trim().isEmpty()) {
                throw new IllegalArgumentException("El número de tarjeta es obligatorio para pagos con tarjeta.");
            }
            PagoTarjeta pagoTarjeta = new PagoTarjeta();
            pagoTarjeta.setNumeroTarjeta(numeroTarjeta);
            pago = pagoTarjeta; // Polimorfismo
        } else {
            pago = new Pago();
        }

        // 3. Asignar los campos comunes del Pago
        pago.setMonto(pedido.getTotal());
        pago.setMetodo(metodoNormalizado);
        pago.setEstado("COMPLETADO");
        pago.setFecha(new java.util.Date()); // Opcional: seteamos la fecha actual de una vez

        return pagoRepository.save(pago);
    }

    public boolean verificarPago(int id) {
        return pagoRepository.existsById(id);
    }

    public boolean reembolsarPago(int id) {
        Pago p = pagoRepository.findById(id).orElse(null);
        if (p != null) {
            p.setEstado("REEMBOLSADO");
            pagoRepository.save(p);
            return true;
        }
        return false;
    }

    public Pago procesarPagoDirecto(Pago pago) {
        return pagoRepository.save(pago);
    }
}