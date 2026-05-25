# 🚀 Sistema de Pedidos - Menú Dinámico Jerárquico

Aplicación completa con:
- **Backend**: Node.js/Express + JWT + PostgreSQL
- **Frontend**: Angular 17+
- **Base de Datos**: PostgreSQL (con pgAdmin)

## 📋 Características

✅ Menú dinámico jerárquico con niveles infinitos (recursividad)
✅ Autenticación JWT segura
✅ CRUD completo de Clientes
✅ CRUD completo de Productos
✅ CRUD completo de Opciones de Menú (editable desde frontend y Postman)
✅ Diseño moderno con colores morados claros
✅ Sidebar colapsable
✅ Responsive Design

## 🛠️ Requisitos previos

- Node.js (v18+)
- PostgreSQL
- pgAdmin (opcional, para administrar la BD)

## 📦 Instalación y Ejecución

### 1. Backend

```bash
cd backend
npm install
npm start
```

El backend se ejecutará en **http://localhost:3000**

### 2. Frontend

```bash
cd frontend
npm install
npm start
```

El frontend se ejecutará en **http://localhost:4200**

## 🔐 Credenciales de acceso

- **Email**: admin@demo.com
- **Contraseña**: admin123

## 📊 Base de Datos

La aplicación se conecta automáticamente a una base de datos PostgreSQL en Render y crea todas las tablas y datos de ejemplo al iniciar.

### Tablas creadas automáticamente:

1. **usuarios**: Para autenticación
2. **opciones**: Menú dinámico (relación recursiva)
3. **clientes**: CRUD de clientes
4. **productos**: CRUD de productos

## 📁 Estructura del Proyecto

```
demo/
├── backend/
│   ├── routes/
│   │   ├── auth.js          # Rutas de autenticación
│   │   ├── opciones.js      # Rutas de menú
│   │   ├── clientes.js      # Rutas de clientes
│   │   └── productos.js     # Rutas de productos
│   ├── db.js                # Conexión y migraciones BD
│   ├── auth.js              # JWT - generar y verificar tokens
│   ├── index.js             # Servidor principal
│   ├── package.json
│   └── .env
└── frontend/
    ├── src/
    │   ├── app/
    │   │   ├── components/
    │   │   │   ├── sidebar/      # Sidebar colapsable
    │   │   │   └── menu-item/    # Componente recursivo
    │   │   ├── pages/
    │   │   │   ├── login/         # Pantalla de login
    │   │   │   ├── home/          # Página de inicio
    │   │   │   ├── clientes/      # CRUD clientes
    │   │   │   ├── productos/     # CRUD productos
    │   │   │   └── opciones-admin/# CRUD menú
    │   │   ├── services/
    │   │   │   ├── auth.service.ts
    │   │   │   └── api.service.ts
    │   │   ├── guards/
    │   │   │   └── auth.guard.ts
    │   │   ├── models/
    │   │   │   └── opcion.model.ts
    │   │   ├── app.module.ts
    │   │   ├── app-routing.module.ts
    │   │   └── app.component.ts
    │   └── styles.css
    └── package.json
```

## 🔌 Endpoints de la API

### Autenticación
- `POST /api/auth/login` - Iniciar sesión

### Opciones de Menú
- `GET /api/opciones` - Obtener todas las opciones
- `GET /api/opciones/menu` - Obtener menú en formato árbol
- `POST /api/opciones` - Crear opción
- `PUT /api/opciones/:id` - Actualizar opción
- `DELETE /api/opciones/:id` - Eliminar opción

### Clientes
- `GET /api/clientes` - Obtener todos los clientes
- `POST /api/clientes` - Crear cliente
- `PUT /api/clientes/:id` - Actualizar cliente
- `DELETE /api/clientes/:id` - Eliminar cliente

### Productos
- `GET /api/productos` - Obtener todos los productos
- `POST /api/productos` - Crear producto
- `PUT /api/productos/:id` - Actualizar producto
- `DELETE /api/productos/:id` - Eliminar producto

## 🎨 Diseño

- **Fondo general**: `#f3e5f5` (morado claro)
- **Sidebar**: Gradiente de `#6a1b9a` a `#9c27b0`
- **Texto sidebar**: Blanco
- **Botones**: Redondeados con sombras suaves

## 📝 Notas importantes

1. La base de datos se inicializa automáticamente al iniciar el backend
2. Se crea un usuario administrador por defecto: `admin@demo.com` / `admin123`
3. El menú se carga dinámicamente desde la base de datos
4. El menú soporta niveles infinitos mediante recursividad
5. Todas las rutas excepto `/login` requieren token JWT

## 🚀 Listo para usar!

1. Inicia el backend: `cd backend && npm start`
2. Inicia el frontend: `cd frontend && npm start`
3. Abre tu navegador en **http://localhost:4200**
4. Inicia sesión con: `admin@demo.com` / `admin123`

¡Disfruta de tu aplicación! 💜
