package com.pedidosiker.demo.repository;

import com.pedidosiker.demo.model.Pedido;
import com.pedidosiker.demo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    // Para obtener pedidos de un usuario específico
    List<Pedido> findByUsuario(Usuario usuario);
}