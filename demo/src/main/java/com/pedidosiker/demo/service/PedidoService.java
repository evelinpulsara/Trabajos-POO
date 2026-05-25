package com.pedidosiker.demo.service;

import com.pedidosiker.demo.model.Pedido;
import com.pedidosiker.demo.model.Usuario;
import com.pedidosiker.demo.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public Pedido crearPedido(Pedido pedido) {
        // En tu diagrama dice generarNumeroPedido, usaremos un UUID simple
        return pedidoRepository.save(pedido);
    }

    public boolean procesarPedido(Pedido pedido) {
        pedido.setEstado("PROCESADO");
        pedidoRepository.save(pedido);
        return true;
    }

    public boolean cancelarPedido(int id) {
        Pedido p = pedidoRepository.findById(id).orElse(null);
        if (p != null) {
            p.setEstado("CANCELADO");
            pedidoRepository.save(p);
            return true;
        }
        return false;
    }

    public List<Pedido> obtenerPedidosUsuario(Usuario usuario) {
        return pedidoRepository.findByUsuario(usuario);
    }

    public void actualizarEstado(int id, String estado) {
        Pedido p = pedidoRepository.findById(id).orElse(null);
        if (p != null) {
            p.setEstado(estado);
            pedidoRepository.save(p);
        }
    }

    private String generarNumeroPedido() {
        return "PED-" + UUID.randomUUID().toString().substring(0, 8);
    }
}