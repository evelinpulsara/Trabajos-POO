const express = require('express');
const router = express.Router();
const db = require('../db');
const { verifyToken } = require('../auth');

router.get('/', verifyToken, async (req, res) => {
  try {
    const result = await db.query('SELECT * FROM clientes ORDER BY creado_en DESC');
    res.json(result.rows);
  } catch (err) {
    console.error('Error obteniendo clientes:', err);
    res.status(500).json({ message: 'Error interno en el servidor.' });
  }
});

router.get('/:id', verifyToken, async (req, res) => {
  const { id } = req.params;
  try {
    const result = await db.query('SELECT * FROM clientes WHERE id = $1', [id]);
    if (result.rows.length === 0) {
      return res.status(404).json({ message: 'Cliente no encontrado.' });
    }
    res.json(result.rows[0]);
  } catch (err) {
    console.error('Error obteniendo cliente:', err);
    res.status(500).json({ message: 'Error interno en el servidor.' });
  }
});

router.post('/', verifyToken, async (req, res) => {
  const { nombre, email, telefono } = req.body;
  try {
    const result = await db.query(
      'INSERT INTO clientes (nombre, email, telefono) VALUES ($1, $2, $3) RETURNING *',
      [nombre, email, telefono]
    );
    res.status(201).json(result.rows[0]);
  } catch (err) {
    console.error('Error creando cliente:', err);
    res.status(500).json({ message: 'Error interno en el servidor.' });
  }
});

router.put('/:id', verifyToken, async (req, res) => {
  const { id } = req.params;
  const { nombre, email, telefono } = req.body;
  try {
    const result = await db.query(
      'UPDATE clientes SET nombre = $1, email = $2, telefono = $3 WHERE id = $4 RETURNING *',
      [nombre, email, telefono, id]
    );
    if (result.rows.length === 0) {
      return res.status(404).json({ message: 'Cliente no encontrado.' });
    }
    res.json(result.rows[0]);
  } catch (err) {
    console.error('Error actualizando cliente:', err);
    res.status(500).json({ message: 'Error interno en el servidor.' });
  }
});

router.delete('/:id', verifyToken, async (req, res) => {
  const { id } = req.params;
  try {
    const result = await db.query('DELETE FROM clientes WHERE id = $1 RETURNING *', [id]);
    if (result.rows.length === 0) {
      return res.status(404).json({ message: 'Cliente no encontrado.' });
    }
    res.json({ message: 'Cliente eliminado con éxito.' });
  } catch (err) {
    console.error('Error eliminando cliente:', err);
    res.status(500).json({ message: 'Error interno en el servidor.' });
  }
});

module.exports = router;
