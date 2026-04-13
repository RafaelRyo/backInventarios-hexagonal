# Inventario - Microservicio con Arquitectura Hexagonal

Microservicio Spring Boot para gestión de productos e inventario, con arquitectura hexagonal (puertos y adaptadores), persistencia híbrida (MySQL + MongoDB) y observabilidad con métricas y logs.

## Estado actual del proyecto

- El micro corre localmente desde IntelliJ o Gradle en `localhost:8080`.
- La infraestructura (bases de datos y observabilidad) corre en un **repo aparte** con Docker Compose.
- Este repositorio contiene el código del micro, no los contenedores de infra.

## Estructura actual (resumen)

```text
src/main/java/com/empresa/inventario/
├── controller/
│   ├── ProductoController.java
│   └── InventarioController.java
├── models/
│   ├── Producto.java
│   └── Inventario.java
├── service/
│   ├── ProductoUseCase.java
│   ├── InventarioUseCase.java
│   └── impl/
│       ├── ProductoServiceImpl.java
│       └── InventarioServiceImpl.java
├── repository/
│   ├── ProductoRepositoryPort.java
│   ├── InventarioRepositoryPort.java
│   ├── ProductoRepositoryAdapter.java
│   ├── InventarioRepositoryAdapter.java
│   ├── ProductoRepository.java
│   ├── InventarioRepository.java
│   ├── ProductoEntity.java
│   └── InventarioEntity.java
└── InventarioApplication.java
```

## Stack técnico

- Java 17
- Spring Boot 3.3.x
- Gradle Wrapper
- Spring Data JPA (MySQL)
- Spring Data MongoDB
- Spring Boot Actuator + Micrometer Prometheus
- Logback + Logstash Encoder

## Configuración local del micro

Archivo: `src/main/resources/application.properties`

Valores relevantes actuales:

- Puerto app: `8080` (default Spring Boot)
- MySQL: `localhost:3306`, DB `atm`
- MongoDB: `localhost:27017`, DB `inventarios`
- Actuator expuesto: `prometheus,health,metrics`

Archivo: `src/main/resources/logback-spring.xml`

- Perfil `!logstash`: solo consola.
- Perfil `logstash`: consola + envío TCP a `localhost:5000`.

## Arranque rápido

### 1) Levantar infraestructura (repo de infra)

En tu repo de infraestructura, levanta lo necesario para desarrollo.
Normalmente incluye:

- MySQL
- MongoDB
- Prometheus
- Grafana
- Logstash
- Elasticsearch
- Kibana

Ejemplo:

```powershell
Set-Location "C:\RUTA\A\TU\REPO-INFRA"
docker compose up -d
docker compose ps
```

### 2) Ejecutar microservicio local

Desde este repo (`backInventarios-hexagonal`):

```powershell
Set-Location "C:\ESTRUCTURA-HEXAGONAL\backInventarios-hexagonal"
.\gradlew bootRun
```

Si quieres enviar logs a Logstash:

```powershell
Set-Location "C:\ESTRUCTURA-HEXAGONAL\backInventarios-hexagonal"
.\gradlew bootRun --args="--spring.profiles.active=logstash"
```

## Endpoints principales

### API de productos

- `GET /api/productos`
- `GET /api/productos/test`

### API de inventario

- `GET /api/inventario`
- `POST /api/inventario/registrar`
- `PUT /api/inventario/actualizar-estado/{id}?estado=...`

### Actuator

- `GET /actuator/health`
- `GET /actuator/prometheus`
- `GET /actuator/metrics`

## Observabilidad

### Métricas (Prometheus + Grafana)

Flujo general:

`Spring Boot /actuator/prometheus` -> `Prometheus` -> `Grafana`

Funcionamiento paso a paso:

1. El microservicio publica las métricas en el endpoint:
   - `GET http://localhost:8080/actuator/prometheus`
2. Prometheus scrapea ese endpoint (por ejemplo con target `host.docker.internal:8080` y `metrics_path: /actuator/prometheus`) y guarda las métricas como series temporales en su TSDB.
3. Grafana no lee directo del micro; Grafana consulta a Prometheus como datasource (normalmente `http://prometheus:9090`) y con eso dibuja los dashboards.

Verificación rápida:

```powershell
Invoke-WebRequest -Uri "http://localhost:8080/actuator/prometheus" -UseBasicParsing
```

- Prometheus UI: `http://localhost:9090`
- Grafana UI: `http://localhost:3000`

Consultas PromQL útiles:

```promql
up
```

```promql
sum(rate(http_server_requests_seconds_count[1m]))
```

```promql
sum(jvm_memory_used_bytes)
```

### Logs (Logstash + Elasticsearch + Kibana)

Flujo:

`Spring Boot (logback)` -> `Logstash:5000` -> `Elasticsearch` -> `Kibana`

Verificación rápida:

```powershell
docker logs logstash --tail 100
```

```powershell
Invoke-WebRequest -Uri "http://localhost:9200/_cat/indices?v" -UseBasicParsing
```

- Kibana UI: `http://localhost:5601`
- Data view típico: `inventarios-logs-*` (según tu pipeline).

## Prueba de carga rápida para generar métricas/logs

```powershell
1..50 | ForEach-Object { Invoke-WebRequest -Uri "http://localhost:8080/actuator/health" -UseBasicParsing | Out-Null }
```

Después de esto deberías ver movimiento en Grafana y eventos en Kibana.

