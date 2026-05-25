package com.pedidosiker.demo.repository;

import com.pedidosiker.demo.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Integer> {
    // Aquí podrías buscar por transaccionId si lo necesitas
}