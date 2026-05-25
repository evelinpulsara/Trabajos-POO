const express = require('express');
const router = express.Router();
const db = require('../db');
const { verifyToken } = require('../auth');

const buildTree = (opciones) => {
  const map = new Map();
  const roots = [];
  
  opciones.forEach(op => {
    map.set(op.id, { ...op, hijos: [] });
  });
  
  opciones.forEach(op => {
    if (op.padre_opcion_id === null) {
      roots.push(map.get(op.id));
    } else {
      const padre = map.get(op.padre_opcion_id);
      if (padre) {
        padre.hijos.push(map.get(op.id));
      }
    }
  });
  
  return roots;
};

router.get('/', verifyToken, async (req, res) => {
  try {
    const result = await db.query('SELECT * FROM opciones WHERE activo = true ORDER BY orden ASC, id ASC');
    res.json(result.rows);
  } catch (err) {
    console.error('Error obteniendo opciones:', err);
    res.status(500).json({ message: 'Error interno en el servidor.' });
  }
});

router.get('/menu', verifyToken, async (req, res) => {
  try {
    const result = await db.query('SELECT * FROM opciones WHERE activo = true ORDER BY orden ASC, id ASC');
    const menu = buildTree(result.rows);
    res.json(menu);
  } catch (err) {
    console.error('Error obteniendo menú:', err);
    res.status(500).json({ message: 'Error interno en el servidor.' });
  }
});

router.post('/', verifyToken, async (req, res) => {
  const { nombre, padre_opcion_id, ruta, icono, orden } = req.body;
  try {
    const result = await db.query(
      'INSERT INTO opciones (nombre, padre_opcion_id, ruta, icono, orden) VALUES ($1, $2, $3, $4, $5) RETURNING *',
      [nombre, padre_opcion_id, ruta, icono, orden || 0]
    );
    res.status(201).json(result.rows[0]);
  } catch (err) {
    console.error('Error creando opción:', err);
    res.status(500).json({ message: 'Error interno en el servidor.' });
  }
});

router.put('/:id', verifyToken, async (req, res) => {
  const { id } = req.params;
  const { nombre, padre_opcion_id, ruta, icono, orden, activo } = req.body;
  try {
    const result = await db.query(
      'UPDATE opciones SET nombre = $1, padre_opcion_id = $2, ruta = $3, icono = $4, orden = $5, activo = $6, updated_at = CURRENT_TIMESTAMP WHERE id = $7 RETURNING *',
      [nombre, padre_opcion_id, ruta, icono, orden, activo, id]
    );
    if (result.rows.length === 0) {
      return res.status(404).json({ message: 'Opción no encontrada.' });
    }
    res.json(result.rows[0]);
  } catch (err) {
    console.error('Error actualizando opción:', err);
    res.status(500).json({ message: 'Error interno en el servidor.' });
  }
});

router.delete('/:id', verifyToken, async (req, res) => {
  const { id } = req.params;
  try {
    const result = await db.query('DELETE FROM opciones WHERE id = $1 RETURNING *', [id]);
    if (result.rows.length === 0) {
      return res.status(404).json({ message: 'Opción no encontrada.' });
    }
    res.json({ message: 'Opción eliminada con éxito.' });
  } catch (err) {
    console.error('Error eliminando opción:', err);
    res.status(500).json({ message: 'Error interno en el servidor.' });
  }
});

module.exports = router;
