# 📋 Sistema de Menú Dinámico Jerárquico

## 📚 Descripción General

Este proyecto implementa un **menú dinámico jerárquico completo** con:
- **Backend**: Spring Boot + PostgreSQL
- **Frontend**: Angular 17+
- **Base de Datos**: PostgreSQL con tabla recursiva

## 🗂️ Estructura de Carpetas

```
demo/
├── src/
│   └── main/
│       ├── java/com/pedidosiker/demo/
│       │   ├── controller/
│       │   │   └── OpcionController.java      # Controlador REST
│       │   ├── model/
│       │   │   └── Opcion.java                 # Entidad JPA
│       │   ├── repository/
│       │   │   └── OpcionRepository.java       # Repositorio
│       │   ├── service/
│       │   │   └── OpcionService.java          # Lógica de negocio
│       │   └── dto/
│       │       └── OpcionTreeDTO.java           # DTO para árbol
│       └── resources/
│           ├── application.properties           # Configuración BD
│           └── db/
│               └── menu_opciones.sql            # Script SQL
└── frontend/                                    # Proyecto Angular
    ├── src/
    │   ├── app/
    │   │   ├── components/
    │   │   │   ├── sidebar/                    # Sidebar principal
    │   │   │   └── menu-item/                  # Componente recursivo ⭐
    │   │   ├── services/
    │   │   │   └── menu.service.ts             # Servicio API
    │   │   ├── models/
    │   │   │   └── opcion.model.ts             # Interface TypeScript
    │   │   ├── pages/
    │   │   │   └── home/                       # Página de inicio
    │   │   ├── app.component.ts/html/css
    │   │   ├── app.module.ts
    │   │   └── app-routing.module.ts
    │   ├── index.html
    │   ├── main.ts
    │   └── styles.css
    ├── package.json
    ├── angular.json
    └── tsconfig.json
```

---

## 🔧 Explicación Técnica Paso a Paso

### 1. Base de Datos: Tabla Recursiva

**Archivo**: `src/main/resources/db/menu_opciones.sql`

La tabla `opciones` usa una **llave foránea recursiva**:
- `padre_opcion_id` → hace referencia a `id` de la misma tabla
- Permite `NULL` para elementos raíz (sin padre)
- Soporta niveles infinitos mediante autorelación

```sql
CREATE TABLE opciones (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    padre_opcion_id INTEGER,  -- ← Recursivo
    ruta VARCHAR(255),
    icono VARCHAR(50),
    orden INTEGER,
    activo BOOLEAN,
    CONSTRAINT fk_opcion_padre FOREIGN KEY (padre_opcion_id) REFERENCES opciones(id)
);
```

---

### 2. Backend: Spring Boot

#### Entidad JPA (`Opcion.java`)
- Mapea la tabla de BD
- Tiene relación `@ManyToOne` consigo misma (padre)
- Tiene relación `@OneToMany` consigo misma (hijos)

#### Repositorio (`OpcionRepository.java`)
- `findByActivoTrueOrderByOrdenAscIdAsc()`: Obtiene todas las opciones activas ordenadas
- `findRootOptions()`: Obtiene solo las opciones raíz (sin padre)

#### Servicio (`OpcionService.java`) ⭐ IMPORTANTE
El método `getMenuHierarchy()` **construye el árbol**:

```java
public List<OpcionTreeDTO> getMenuHierarchy() {
    // 1. Obtener todas las opciones de BD
    List<Opcion> allOptions = findAll();
    
    // 2. Convertir a DTO y guardar en un Map por ID
    Map<Long, OpcionTreeDTO> optionMap = allOptions.stream()
        .map(this::convertToDTO)
        .collect(Collectors.toMap(OpcionTreeDTO::getId, dto -> dto));
    
    // 3. Construir la jerarquía
    List<OpcionTreeDTO> rootOptions = new ArrayList<>();
    for (OpcionTreeDTO dto : optionMap.values()) {
        if (dto.getPadreOpcionId() == null) {
            // Es raíz
            rootOptions.add(dto);
        } else {
            // Buscar padre y agregarse como hijo
            OpcionTreeDTO padre = optionMap.get(dto.getPadreOpcionId());
            if (padre != null) {
                padre.addHijo(dto);
            }
        }
    }
    return rootOptions;
}
```

#### Controlador (`OpcionController.java`)
- `GET /api/opciones`: Devuelve todas las opciones en formato plano
- `GET /api/opciones/menu`: Devuelve el árbol jerárquico ✨

---

### 3. Frontend: Angular

#### Modelo (`opcion.model.ts`)
Interface TypeScript con tipado fuerte:
```typescript
export interface Opcion {
  id: number;
  nombre: string;
  padreOpcionId: number | null;
  ruta: string | null;
  icono: string | null;
  orden: number;
  hijos: Opcion[];  // ← Recursivo!
}
```

#### Servicio (`menu.service.ts`)
Consume la API REST usando `HttpClient`:
```typescript
getMenuHierarchy(): Observable<Opcion[]> {
  return this.http.get<Opcion[]>(`${this.apiUrl}/menu`);
}
```

#### Componente Recursivo (`menu-item.component.ts`) ⭐ EL CORAZÓN

**¿Cómo funciona la recursividad?**

El componente se **llama a sí mismo** en su propio template:

1. Recibe una `opcion` como `@Input()`
2. Si tiene hijos, se renderiza a sí mismo para cada hijo
3. Así sucesivamente hasta que no haya más niveles

**Código clave**:
```typescript
@Component({
  selector: 'app-menu-item',  // ← Este selector
  // ...
})
export class MenuItemComponent {
  @Input() opcion!: Opcion;
  @Input() nivel = 0;
  expandido = false;
  
  tieneHijos(): boolean {
    return this.opcion.hijos && this.opcion.hijos.length > 0;
  }
}
```

**Template HTML (recursivo)**:
```html
<div class="menu-item">
  <div (click)="toggleExpandido(); navegar()">
    {{ opcion.nombre }}
  </div>
  
  <!-- RECURSIÓN: el componente se llama a sí mismo! -->
  <div *ngIf="tieneHijos() && expandido">
    <app-menu-item 
      *ngFor="let hijo of opcion.hijos" 
      [opcion]="hijo" 
      [nivel]="nivel + 1"
    ></app-menu-item>
  </div>
</div>
```

#### Sidebar (`sidebar.component.ts`)
- Contenedor principal del menú
- Carga el menú desde el servicio al inicializar
- Tiene funcionalidad de colapsar/expandir sidebar

#### Diseño (CSS)
- Colores crema cálidos: `#FFF8E7`, `#FFEFD5`, `#8B7355`
- Gradientes suaves
- Animaciones fluidas
- Responsivo

---

## 🚀 Cómo Ejecutar

### Paso 1: Ejecutar el Script SQL

Conecta a tu PostgreSQL (via pgAdmin o psql) y ejecuta:
```bash
# En pgAdmin: Abre Query Tool y carga src/main/resources/db/menu_opciones.sql
```

Esto creará la tabla y los datos de ejemplo.

### Paso 2: Iniciar el Backend (Spring Boot)

```bash
cd /Users/evelin/Proyectos/demo
./mvnw spring-boot:run
```

El backend estará en: `http://localhost:8080`

Prueba la API:
- `GET http://localhost:8080/api/opciones` (todas las opciones)
- `GET http://localhost:8080/api/opciones/menu` (árbol jerárquico) ✨

### Paso 3: Instalar y Ejecutar el Frontend (Angular)

```bash
cd /Users/evelin/Proyectos/demo/frontend

# Instalar dependencias (primera vez)
npm install

# Iniciar servidor de desarrollo
npm start
# O con Angular CLI: ng serve --open
```

El frontend estará en: `http://localhost:4200`

---

## ✅ Buenas Prácticas Aplicadas

1. **Tipado Fuerte**: Interfaces TypeScript, entidades JPA tipadas
2. **Arquitectura Limpia**: Separación Controller → Service → Repository
3. **DTOs**: Objetos de transferencia de datos para la API
4. **Recursividad**: Componente reutilizable para niveles infinitos
5. **Modularidad**: Componentes pequeños y con responsabilidad única
6. **Nombres Claros**: Variables, métodos y clases con nombres descriptivos
7. **Responsive Design**: CSS adaptable a diferentes tamaños de pantalla
8. **Manejo de Errores**: Loading states, manejo de errores en API

---

## 🎨 Ejemplo de Datos en el Menú

```
🌸 Mi Aplicación
├── 👥 Clientes
│   ├── ➕ Crear Cliente
│   ├── ✏️ Editar Cliente
│   └── ❌ Eliminar Cliente
├── 📦 Productos
│   ├── ➕ Crear Producto
│   ├── 🏷️ Categorías
│   └── 🏭 Inventario
└── 🛒 Pedidos
    ├── ➕ Crear Pedido
    ├── 📜 Historial
    └── 📊 Reportes
```

---

## 📞 Endpoints de la API

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/api/opciones` | Obtener todas las opciones (plano) |
| GET | `/api/opciones/menu` | Obtener menú jerárquico (árbol) |
| GET | `/api/opciones/{id}` | Obtener opción por ID |
| POST | `/api/opciones` | Crear nueva opción |
| PUT | `/api/opciones/{id}` | Actualizar opción |
| DELETE | `/api/opciones/{id}` | Eliminar opción |

---

¡Listo! Ahora tienes un sistema de menú dinámico completo y profesional. 🎉
