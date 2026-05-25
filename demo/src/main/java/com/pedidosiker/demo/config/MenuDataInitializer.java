package com.pedidosiker.demo.config;

import com.pedidosiker.demo.model.Opcion;
import com.pedidosiker.demo.repository.OpcionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MenuDataInitializer implements CommandLineRunner {

    @Autowired
    private OpcionRepository opcionRepository;

    @Override
    public void run(String... args) throws Exception {
        if (opcionRepository.count() == 0) {
            // Level 1: Root
            Opcion root = new Opcion();
            root.setNombre("Mi Aplicación");
            root.setPadreOpcionId(null);
            root.setIcono("fas fa-home");
            root.setOrden(1);
            root = opcionRepository.save(root);

            final Long rootId = root.getId();

            // Level 2: Submenus
            Opcion clientes = createOpcion("Clientes", rootId, null, "fas fa-users", 1);
            Opcion productos = createOpcion("Productos", rootId, null, "fas fa-box", 2);
            Opcion pedidos = createOpcion("Pedidos", rootId, null, "fas fa-shopping-cart", 3);

            // Level 3: Clientes Submenus
            createOpcion("Crear Cliente", clientes.getId(), "/clientes/crear", "fas fa-user-plus", 1);
            createOpcion("Editar Cliente", clientes.getId(), "/clientes/editar", "fas fa-user-edit", 2);
            createOpcion("Eliminar Cliente", clientes.getId(), "/clientes/eliminar", "fas fa-user-times", 3);

            // Level 3: Productos Submenus
            createOpcion("Crear Producto", productos.getId(), "/productos/crear", "fas fa-plus-circle", 1);
            createOpcion("Categorías", productos.getId(), "/productos/categorias", "fas fa-tags", 2);
            createOpcion("Inventario", productos.getId(), "/productos/inventario", "fas fa-warehouse", 3);

            // Level 3: Pedidos Submenus
            createOpcion("Crear Pedido", pedidos.getId(), "/pedidos/crear", "fas fa-cart-plus", 1);
            createOpcion("Historial", pedidos.getId(), "/pedidos/historial", "fas fa-history", 2);
            createOpcion("Reportes", pedidos.getId(), "/pedidos/reportes", "fas fa-chart-bar", 3);
            
            System.out.println("🌱 Datos de menú dinámico inicializados con éxito.");
        } else {
            System.out.println("🌿 La tabla opciones ya tiene datos, omitiendo inicialización.");
        }
    }

    private Opcion createOpcion(String nombre, Long padreId, String ruta, String icono, Integer orden) {
        Opcion opcion = new Opcion();
        opcion.setNombre(nombre);
        opcion.setPadreOpcionId(padreId);
        opcion.setRuta(ruta);
        opcion.setIcono(icono);
        opcion.setOrden(orden);
        return opcionRepository.save(opcion);
    }
}
