const { Pool } = require('pg');
require('dotenv').config();

const connectionString = process.env.DATABASE_URL || 'postgres://evelin:CmLF1TVciRx4vNSObPmGvpfT9Fd0ooZ3@dpg-d7psefjeo5us73ej40q0-a.oregon-postgres.render.com:5432/iker_db';

const pool = new Pool({
  connectionString: connectionString,
  ssl: {
    rejectUnauthorized: false
  }
});

// Test connection and run migrations/seeds
const initDb = async () => {
  const client = await pool.connect();
  try {
    console.log('🔌 Conectado a la base de datos de PostgreSQL en Render.');

    // 1. Crear tabla usuarios
    await client.query(`
      CREATE TABLE IF NOT EXISTS usuarios (
        id SERIAL PRIMARY KEY,
        email VARCHAR(100) UNIQUE NOT NULL,
        password_hash VARCHAR(255) NOT NULL
      );
    `);

    // 2. Crear tabla opciones
    await client.query(`
      CREATE TABLE IF NOT EXISTS opciones (
        id SERIAL PRIMARY KEY,
        nombre VARCHAR(100) NOT NULL,
        padre_opcion_id INTEGER REFERENCES opciones(id) ON DELETE SET NULL,
        ruta VARCHAR(255),
        icono VARCHAR(50),
        orden INTEGER DEFAULT 0,
        activo BOOLEAN DEFAULT TRUE,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
      );
    `);

    // 3. Crear tabla clientes
    await client.query(`
      CREATE TABLE IF NOT EXISTS clientes (
        id SERIAL PRIMARY KEY,
        nombre VARCHAR(100) NOT NULL,
        email VARCHAR(100) NOT NULL,
        telefono VARCHAR(50) NOT NULL,
        creado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP
      );
    `);

    // 4. Crear tabla productos
    await client.query(`
      CREATE TABLE IF NOT EXISTS productos (
        id SERIAL PRIMARY KEY,
        nombre VARCHAR(100) NOT NULL,
        precio DECIMAL(10,2) NOT NULL,
        stock INTEGER NOT NULL
      );
    `);

    // Semilla de usuarios (admin@demo.com / admin123)
    const userRes = await client.query('SELECT count(*) FROM usuarios');
    if (parseInt(userRes.rows[0].count) === 0) {
      const bcrypt = require('bcryptjs');
      const hash = await bcrypt.hash('admin123', 10);
      await client.query('INSERT INTO usuarios (email, password_hash) VALUES ($1, $2)', ['admin@demo.com', hash]);
      console.log('👤 Usuario admin@demo.com / admin123 creado por defecto.');
    }

    // Semilla de opciones
    const opcRes = await client.query('SELECT count(*) FROM opciones');
    if (parseInt(opcRes.rows[0].count) === 0) {
      // Nivel 1: Raíz
      const rootRes = await client.query(
        "INSERT INTO opciones (nombre, padre_opcion_id, ruta, icono, orden) VALUES ('Mi Aplicación', NULL, '/', 'fas fa-home', 1) RETURNING id"
      );
      const rootId = rootRes.rows[0].id;

      // Nivel 2: Opciones principales
      const cliRes = await client.query(
        "INSERT INTO opciones (nombre, padre_opcion_id, ruta, icono, orden) VALUES ('Clientes', $1, '/clientes', 'fas fa-users', 1) RETURNING id",
        [rootId]
      );
      const prodRes = await client.query(
        "INSERT INTO opciones (nombre, padre_opcion_id, ruta, icono, orden) VALUES ('Productos', $1, '/productos', 'fas fa-box', 2) RETURNING id",
        [rootId]
      );
      const adminRes = await client.query(
        "INSERT INTO opciones (nombre, padre_opcion_id, ruta, icono, orden) VALUES ('Menú Admin', $1, '/opciones-admin', 'fas fa-cogs', 3) RETURNING id",
        [rootId]
      );

      const clientesId = cliRes.rows[0].id;
      const productosId = prodRes.rows[0].id;

      // Nivel 3: Subopciones
      await client.query(
        "INSERT INTO opciones (nombre, padre_opcion_id, ruta, icono, orden) VALUES ('Crear Cliente', $1, '/clientes', 'fas fa-user-plus', 1)",
        [clientesId]
      );
      const catRes = await client.query(
        "INSERT INTO opciones (nombre, padre_opcion_id, ruta, icono, orden) VALUES ('Categorías', $1, '/productos', 'fas fa-tags', 1) RETURNING id",
        [productosId]
      );
      const categoriasId = catRes.rows[0].id;

      // Nivel 4: Subopción de Categorías
      await client.query(
        "INSERT INTO opciones (nombre, padre_opcion_id, ruta, icono, orden) VALUES ('Nueva Categoría', $1, '/productos', 'fas fa-plus-circle', 1)",
        [categoriasId]
      );

      console.log('🌱 Opciones de menú jerárquico inicializadas con éxito (Niveles 1 a 4).');
    }

    // Semilla de clientes
    const cliRes = await client.query('SELECT count(*) FROM clientes');
    if (parseInt(cliRes.rows[0].count) === 0) {
      await client.query("INSERT INTO clientes (nombre, email, telefono) VALUES ('Maria Gomez', 'maria@example.com', '555-0192')");
      await client.query("INSERT INTO clientes (nombre, email, telefono) VALUES ('Juan Perez', 'juan@example.com', '555-4839')");
      console.log('👥 Clientes de ejemplo insertados.');
    }

    // Semilla de productos
    const prodRes = await client.query('SELECT count(*) FROM productos');
    if (parseInt(prodRes.rows[0].count) === 0) {
      await client.query("INSERT INTO productos (nombre, precio, stock) VALUES ('Laptop Asus', 899.99, 15)");
      await client.query("INSERT INTO productos (nombre, precio, stock) VALUES ('Mouse Inalámbrico', 25.50, 120)");
      console.log('📦 Productos de ejemplo insertados.');
    }

  } catch (err) {
    console.error('❌ Error al inicializar la base de datos:', err);
  } finally {
    client.release();
  }
};

module.exports = {
  query: (text, params) => pool.query(text, params),
  pool,
  initDb
};
