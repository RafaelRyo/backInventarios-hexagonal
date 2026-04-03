# Proyecto Inventario - Arquitectura Hexagonal

Repo de habilidad, Estructura Hexagonal, para el desarrollo de habilidades en el ámbito laboral y personal.

## 📋 Descripción

Este proyecto implementa una arquitectura hexagonal (ports and adapters) para gestionar un sistema de inventario con productos. La arquitectura permite que el dominio sea independiente de frameworks y tecnologías externas, facilitando la testabilidad, mantenibilidad y escalabilidad.

---

## 🏗️ Estructura del Proyecto Recomendada (Opción 2 - Híbrida)

Esta es la estructura que se aconseja implementar para mantener claridad y orden en la arquitectura hexagonal:

```
src/main/java/com/empresa/inventario/
│
├── 📁 models/                              (Dominio - Entidades Puras)
│   ├── Inventario.java
│   └── Producto.java
│
├── 📁 ports/                               (Puertos - Contratos)
│   ├── ProductoUseCase.java                (Puerto entrada)
│   ├── InventarioUseCase.java              (Puerto entrada)
│   ├── ProductoRepositoryPort.java         (Puerto salida)
│   └── InventarioRepositoryPort.java       (Puerto salida)
│
├── 📁 service/                             (Casos de Uso - Aplicación)
│   └── 📁 impl/
│       ├── ProductoServiceImpl.java
│       └── InventarioServiceImpl.java
│
├── 📁 repository/                          (Adaptadores de Salida)
│   ├── 📁 entity/                          (Entidades de Persistencia)
│   │   ├── ProductoEntity.java
│   │   └── InventarioEntity.java
│   │
│   ├── 📁 jpa/                             (Repositorios JPA)
│   │   └── ProductoRepository.java         (extends JpaRepository)
│   │
│   ├── 📁 mongo/                           (Repositorios MongoDB)
│   │   └── InventarioRepository.java       (extends MongoRepository)
│   │
│   └── 📁 adapter/                         (Adaptadores del Puerto)
│       ├── ProductoRepositoryAdapter.java
│       └── InventarioRepositoryAdapter.java
│
├── 📁 controller/                          (Adaptadores de Entrada - Web)
│   ├── ProductoController.java
│   └── InventarioController.java
│
├── 📁 config/                              (Configuración)
│   └── WebConfig.java
│
└── InventarioApplication.java              (Punto de entrada)
```

---

## 🔄 Flujo de la Arquitectura Hexagonal

```
┌─────────────────────────────────────────────────────────────────┐
│                    ADAPTADORES DE ENTRADA                       │
│                     (HTTP REST/Controllers)                      │
│                                                                   │
│    ProductoController ────────┬────── InventarioController       │
└────────────────────────────────┼────────────────────────────────┘
                                 │
                    (Implementan Puertos de Entrada)
                                 │
┌────────────────────────────────▼────────────────────────────────┐
│                      PUERTOS DE ENTRADA                          │
│                    (Casos de Uso / Use Cases)                   │
│                                                                   │
│    ProductoUseCase ──────────┬────── InventarioUseCase           │
└────────────────────────────────┼────────────────────────────────┘
                                 │
┌────────────────────────────────▼────────────────────────────────┐
│                        DOMINIO (NÚCLEO)                          │
│                   (Lógica de Negocio Pura)                      │
│                                                                   │
│    ProductoServiceImpl ────────┬────── InventarioServiceImpl       │
│                               │                                   │
│                         (Usa Puertos de Salida)                  │
└────────────────────────────────┼────────────────────────────────┘
                                 │
┌────────────────────────────────▼────────────────────────────────┐
│                      PUERTOS DE SALIDA                           │
│                   (Interfaces de Dependencias)                  │
│                                                                   │
│  ProductoRepositoryPort ──────┬────── InventarioRepositoryPort   │
└────────────────────────────────┼────────────────────────────────┘
                                 │
┌────────────────────────────────▼────────────────────────────────┐
│                   ADAPTADORES DE SALIDA                          │
│            (Implementaciones de Persistencia)                    │
│                                                                   │
│  ProductoRepositoryAdapter────┬────── InventarioRepositoryAdapter│
│         (JPA)                  │           (MongoDB)             │
└────────────────────────────────┼────────────────────────────────┘
                                 │
┌────────────────────────────────▼────────────────────────────────┐
│                    BASES DE DATOS                                │
│                                                                   │
│    MySQL (Productos) ────────────── MongoDB (Inventario)        │
└─────────────────────────────────────────────────────────────────┘
```

---

## 🎯 Componentes Clave

### 📦 Dominio (`models/`)
- **Entidades Puras**: `Inventario.java`, `Producto.java`
- **Sin dependencias** de frameworks (JPA, MongoDB, Spring)
- Contienen solo lógica de negocio básica
- Fáciles de testear

### 🔌 Puertos (`ports/`)

#### Puertos de Entrada (Use Cases)
- `ProductoUseCase.java`: Define qué operaciones puedo hacer con productos
- `InventarioUseCase.java`: Define qué operaciones puedo hacer con inventarios
- El dominio los **implementa** (no depende de ellos)

#### Puertos de Salida
- `ProductoRepositoryPort.java`: Contrato para acceso a datos de productos
- `InventarioRepositoryPort.java`: Contrato para acceso a datos de inventarios
- El dominio los **usa** (depende de abstracciones, no implementaciones)

### 🚀 Aplicación (`service/impl/`)
- Implementaciones de puertos de entrada
- Coordina entre puertos de entrada y salida
- Contiene la lógica de casos de uso
- Independiente de frameworks

### 🔧 Adaptadores de Salida (`repository/`)

**Entidades** (`entity/`):
- `ProductoEntity.java`: Mapeo JPA
- `InventarioEntity.java`: Mapeo MongoDB
- Tienen anotaciones del framework

**Repositorios del Framework**:
- `jpa/ProductoRepository.java`: Extiende `JpaRepository`
- `mongo/InventarioRepository.java`: Extiende `MongoRepository`

**Adaptadores** (`adapter/`):
- `ProductoRepositoryAdapter.java`: Implementa `ProductoRepositoryPort`
- `InventarioRepositoryAdapter.java`: Implementa `InventarioRepositoryPort`
- Mapean entre entidades de persistencia y dominio

### 🌐 Adaptadores de Entrada (`controller/`)
- `ProductoController.java`: REST API para productos
- `InventarioController.java`: REST API para inventario
- Dependen de puertos de entrada (use cases)
- Transforman HTTP en dominio

---

## ✨ Ventajas de esta Estructura

✅ **Dominio Independiente**: El núcleo no depende de frameworks  
✅ **Testeable**: Fácil crear tests sin bases de datos  
✅ **Escalable**: Agregar nuevos adaptadores es simple  
✅ **Mantenible**: Cambios en frameworks no afectan dominio  
✅ **Desacoplado**: Cada componente tiene responsabilidad clara  
✅ **Flexible**: Cambiar de JPA a Hibernate, MySQL a PostgreSQL, etc.

---

## 🔄 Migración de la Estructura Actual

| Actual | Recomendado | Cambios |
|--------|------------|---------|
| `models/` | `models/` | ✓ Sin cambios |
| `service/IProductoService.java` | `ports/ProductoUseCase.java` | Renombrar |
| `service/ProductoServiceImpl.java` | `service/impl/ProductoServiceImpl.java` | Mover a subcarpeta |
| `repository/ProductoEntity.java` | `repository/entity/ProductoEntity.java` | Mover |
| `repository/ProductoRepository.java` | `repository/jpa/ProductoRepository.java` | Mover |
| `repository/ProductoRepositoryPort.java` | `ports/ProductoRepositoryPort.java` | Mover |
| `repository/ProductoRepositoryAdapter.java` | `repository/adapter/ProductoRepositoryAdapter.java` | Mover |
| `controller/` | `controller/` | ✓ Sin cambios |

---

## 📱 API Endpoints

### Productos
- `GET /api/productos` - Obtener todos los productos
- `GET /api/productos/test` - Endpoint de prueba

### Inventario
- `GET /api/inventario` - Obtener todos los registros
- `POST /api/inventario/registrar` - Registrar nuevo inventario
- `PUT /api/inventario/actualizar-estado/{id}` - Actualizar estado

---

## 🛠️ Tecnologías

- **Java 17+**
- **Spring Boot 3.x**
- **Spring Data JPA** (MySQL)
- **Spring Data MongoDB**
- **Gradle**
- **Lombok**

---

## 🚀 Cómo Ejecutar

```bash
# Compilar
./gradlew build

# Ejecutar
./gradlew bootRun
```

---

## 📚 Referencias

- [Arquitectura Hexagonal - Alistair Cockburn](https://alistair.cockburn.us/hexagonal-architecture/)
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
