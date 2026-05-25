const jwt = require('jsonwebtoken');

const JWT_SECRET = process.env.JWT_SECRET || 'super_secret_purple_key_123';

const generateToken = (user) => {
  return jwt.sign({ id: user.id, email: user.email }, JWT_SECRET, {
    expiresIn: '24h'
  });
};

const verifyToken = (req, res, next) => {
  const authHeader = req.headers['authorization'];
  const token = authHeader || req.headers['x-access-token'];
  
  if (!token) {
    return res.status(403).json({ message: 'Acceso denegado. No se proporcionó token.' });
  }

  try {
    const tokenString = token.startsWith('Bearer ') ? token.slice(7, token.length) : token;
    const decoded = jwt.verify(tokenString, JWT_SECRET);
    req.userId = decoded.id;
    req.userEmail = decoded.email;
    next();
  } catch (err) {
    return res.status(401).json({ message: 'Token inválido o expirado.' });
  }
};

module.exports = {
  generateToken,
  verifyToken
};
