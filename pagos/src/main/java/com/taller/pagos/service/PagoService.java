package com.taller.pagos.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.taller.pagos.model.Pago;

@Service
public class PagoService {

    private List<Pago> listaPagos = new ArrayList<>();

    public void registrarPago(Pago pago) {
        pago.procesarPago();
        listaPagos.add(pago);
    }

    public List<Pago> listarPagos() {
        return new ArrayList<>(listaPagos); // 🔒 protegemos la lista
    }

    // default (sin modificador) → solo visible dentro del paquete
    Pago buscarPorId(Long id) {
        return listaPagos.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}