-- =============================================
-- SCRIPT PARA CREAR TABLA DE OPCIONES DE MENÚ
-- =============================================

-- Crear tabla opciones
CREATE TABLE IF NOT EXISTS opciones (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    padre_opcion_id INTEGER,
    ruta VARCHAR(255),
    icono VARCHAR(50),
    orden INTEGER DEFAULT 0,
    activo BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    -- Llave foránea recursiva
    CONSTRAINT fk_opcion_padre
        FOREIGN KEY (padre_opcion_id)
        REFERENCES opciones(id)
        ON DELETE SET NULL
);

-- Índice para mejorar consultas por padre
CREATE INDEX IF NOT EXISTS idx_opciones_padre ON opciones(padre_opcion_id);

-- =============================================
-- DATOS DE EJEMPLO
-- =============================================

-- Nivel 1: Raíz
INSERT INTO opciones (nombre, padre_opcion_id, ruta, icono, orden) VALUES
('Mi Aplicación', NULL, NULL, 'fas fa-home', 1);

-- Obtener el ID de "Mi Aplicación" para usar como padre
-- Nivel 2: Opciones principales
WITH app_id AS (SELECT id FROM opciones WHERE nombre = 'Mi Aplicación')
INSERT INTO opciones (nombre, padre_opcion_id, ruta, icono, orden) VALUES
('Clientes', (SELECT id FROM app_id), NULL, 'fas fa-users', 1),
('Productos', (SELECT id FROM app_id), NULL, 'fas fa-box', 2),
('Pedidos', (SELECT id FROM app_id), NULL, 'fas fa-shopping-cart', 3);

-- Nivel 3: Subopciones de Clientes
WITH clientes_id AS (SELECT id FROM opciones WHERE nombre = 'Clientes')
INSERT INTO opciones (nombre, padre_opcion_id, ruta, icono, orden) VALUES
('Crear Cliente', (SELECT id FROM clientes_id), '/clientes/crear', 'fas fa-user-plus', 1),
('Editar Cliente', (SELECT id FROM clientes_id), '/clientes/editar', 'fas fa-user-edit', 2),
('Eliminar Cliente', (SELECT id FROM clientes_id), '/clientes/eliminar', 'fas fa-user-times', 3);

-- Nivel 3: Subopciones de Productos
WITH productos_id AS (SELECT id FROM opciones WHERE nombre = 'Productos')
INSERT INTO opciones (nombre, padre_opcion_id, ruta, icono, orden) VALUES
('Crear Producto', (SELECT id FROM productos_id), '/productos/crear', 'fas fa-plus-circle', 1),
('Categorías', (SELECT id FROM productos_id), '/productos/categorias', 'fas fa-tags', 2),
('Inventario', (SELECT id FROM productos_id), '/productos/inventario', 'fas fa-warehouse', 3);

-- Nivel 3: Subopciones de Pedidos
WITH pedidos_id AS (SELECT id FROM opciones WHERE nombre = 'Pedidos')
INSERT INTO opciones (nombre, padre_opcion_id, ruta, icono, orden) VALUES
('Crear Pedido', (SELECT id FROM pedidos_id), '/pedidos/crear', 'fas fa-cart-plus', 1),
('Historial', (SELECT id FROM pedidos_id), '/pedidos/historial', 'fas fa-history', 2),
('Reportes', (SELECT id FROM pedidos_id), '/pedidos/reportes', 'fas fa-chart-bar', 3);

-- =============================================
-- VERIFICAR DATOS
-- =============================================
SELECT * FROM opciones ORDER BY orden, id;
