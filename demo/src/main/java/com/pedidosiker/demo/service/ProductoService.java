package com.pedidosiker.demo.service;

import com.pedidosiker.demo.model.Producto;
import com.pedidosiker.demo.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public void agregarProducto(Producto producto) {
        productoRepository.save(producto);
    }

    public void actualizarStock(int id, int cantidad) {
        Producto p = productoRepository.findById(id).orElse(null);
        if (p != null) {
            p.actualizarStock(cantidad);
            productoRepository.save(p);
        }
    }

    public List<Producto> buscarPorCategoria(int categoriaId) {
        return productoRepository.findByCategoriaId(categoriaId);
    }

    public List<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }
}