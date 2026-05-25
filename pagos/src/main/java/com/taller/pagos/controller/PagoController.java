package com.taller.pagos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.taller.pagos.model.Pago;
import com.taller.pagos.service.PagoService;

@RestController
@RequestMapping("/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @PostMapping
    public Pago crearPago(@RequestBody Pago pago) {
        pagoService.registrarPago(pago);
        return pago;
    }

    @GetMapping
    public List<Pago> listarPagos() {
        return pagoService.listarPagos();
    }
}