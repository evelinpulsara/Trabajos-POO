const express = require('express');
const router = express.Router();
const db = require('../db');
const { verifyToken } = require('../auth');

router.get('/', verifyToken, async (req, res) => {
  try {
    const result = await db.query('SELECT * FROM productos ORDER BY id DESC');
    res.json(result.rows);
  } catch (err) {
    console.error('Error obteniendo productos:', err);
    res.status(500).json({ message: 'Error interno en el servidor.' });
  }
});

router.get('/:id', verifyToken, async (req, res) => {
  const { id } = req.params;
  try {
    const result = await db.query('SELECT * FROM productos WHERE id = $1', [id]);
    if (result.rows.length === 0) {
      return res.status(404).json({ message: 'Producto no encontrado.' });
    }
    res.json(result.rows[0]);
  } catch (err) {
    console.error('Error obteniendo producto:', err);
    res.status(500).json({ message: 'Error interno en el servidor.' });
  }
});

router.post('/', verifyToken, async (req, res) => {
  const { nombre, precio, stock } = req.body;
  try {
    const result = await db.query(
      'INSERT INTO productos (nombre, precio, stock) VALUES ($1, $2, $3) RETURNING *',
      [nombre, precio, stock]
    );
    res.status(201).json(result.rows[0]);
  } catch (err) {
    console.error('Error creando producto:', err);
    res.status(500).json({ message: 'Error interno en el servidor.' });
  }
});

router.put('/:id', verifyToken, async (req, res) => {
  const { id } = req.params;
  const { nombre, precio, stock } = req.body;
  try {
    const result = await db.query(
      'UPDATE productos SET nombre = $1, precio = $2, stock = $3 WHERE id = $4 RETURNING *',
      [nombre, precio, stock, id]
    );
    if (result.rows.length === 0) {
      return res.status(404).json({ message: 'Producto no encontrado.' });
    }
    res.json(result.rows[0]);
  } catch (err) {
    console.error('Error actualizando producto:', err);
    res.status(500).json({ message: 'Error interno en el servidor.' });
  }
});

router.delete('/:id', verifyToken, async (req, res) => {
  const { id } = req.params;
  try {
    const result = await db.query('DELETE FROM productos WHERE id = $1 RETURNING *', [id]);
    if (result.rows.length === 0) {
      return res.status(404).json({ message: 'Producto no encontrado.' });
    }
    res.json({ message: 'Producto eliminado con éxito.' });
  } catch (err) {
    console.error('Error eliminando producto:', err);
    res.status(500).json({ message: 'Error interno en el servidor.' });
  }
});

module.exports = router;
