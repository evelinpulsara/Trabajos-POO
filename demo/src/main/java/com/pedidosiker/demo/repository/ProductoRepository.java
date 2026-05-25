package com.pedidosiker.demo.repository;

import com.pedidosiker.demo.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    // Para buscar por nombre como indica el diagrama de Service
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
    
    // Para buscar por categoría
    List<Producto> findByCategoriaId(int categoriaId);
}