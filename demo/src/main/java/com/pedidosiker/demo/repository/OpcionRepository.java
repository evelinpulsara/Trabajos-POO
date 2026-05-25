package com.pedidosiker.demo.repository;

import com.pedidosiker.demo.model.Opcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpcionRepository extends JpaRepository<Opcion, Long> {

    List<Opcion> findByActivoTrueOrderByOrdenAscIdAsc();

    List<Opcion> findByPadreOpcionIdAndActivoTrueOrderByOrdenAscIdAsc(Long padreOpcionId);

    @Query("SELECT o FROM Opcion o WHERE o.padreOpcionId IS NULL AND o.activo = true ORDER BY o.orden ASC, o.id ASC")
    List<Opcion> findRootOptions();
}
