const express = require('express');
const cors = require('cors');
const { initDb } = require('./db');
const authRoutes = require('./routes/auth');
const opcionesRoutes = require('./routes/opciones');
const clientesRoutes = require('./routes/clientes');
const productosRoutes = require('./routes/productos');

const app = express();
const PORT = process.env.PORT || 3000;

app.use(cors());
app.use(express.json());

app.use('/api/auth', authRoutes);
app.use('/api/opciones', opcionesRoutes);
app.use('/api/clientes', clientesRoutes);
app.use('/api/productos', productosRoutes);

app.get('/', (req, res) => {
  res.json({ message: 'API Sistema de Pedidos funcionando 🚀' });
});

const startServer = async () => {
  try {
    await initDb();
    app.listen(PORT, () => {
      console.log(`✅ Servidor corriendo en http://localhost:${PORT}`);
    });
  } catch (error) {
    console.error('❌ Error al iniciar el servidor:', error);
  }
};

startServer();
