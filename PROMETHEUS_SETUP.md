# 📊 Configuración de Prometheus - Referencia

## ¿Qué se hizo en el microservicio?

✅ **build.gradle:** Se agregaron 2 dependencias
```groovy
implementation 'org.springframework.boot:spring-boot-starter-actuator'
implementation 'io.micrometer:micrometer-registry-prometheus'
```

✅ **application.properties:** Se habilitó el endpoint de Prometheus
```properties
management.endpoints.web.exposure.include=prometheus,health,metrics
management.endpoint.prometheus.enabled=true
management.metrics.enable.jvm=true
management.metrics.enable.process=true
management.metrics.enable.system=true
```

---

## 🐳 Archivos a crear en tu carpeta de INFRA (fuera del proyecto)

### 1. **docker-compose.yml**
Crear este archivo en tu carpeta de infraestructura:

```yaml
version: '3.8'

services:
  prometheus:
    image: prom/prometheus:latest
    container_name: prometheus
    ports:
      - "9090:9090"
    volumes:
      - ./prometheus.yml:/etc/prometheus/prometheus.yml
      - prometheus_data:/prometheus
    command:
      - '--config.file=/etc/prometheus/prometheus.yml'
      - '--storage.tsdb.path=/prometheus'
      - '--storage.tsdb.retention.time=30d'
    networks:
      - monitoring

volumes:
  prometheus_data:
    driver: local

networks:
  monitoring:
    driver: bridge
```

### 2. **prometheus.yml**
Crear este archivo en tu carpeta de infraestructura (al mismo nivel que docker-compose.yml):

```yaml
global:
  scrape_interval: 15s
  evaluation_interval: 15s

scrape_configs:
  - job_name: 'inventario-microservice'
    static_configs:
      - targets: ['host.docker.internal:8080']
    metrics_path: '/actuator/prometheus'
    scrape_interval: 10s
```

---

## 🚀 Cómo usar

### Paso 1: Compilar el microservicio
```bash
cd backInventarios-hexagonal
./gradlew build
```

### Paso 2: Ejecutar el microservicio
```bash
./gradlew bootRun
```

### Paso 3: Levantar Prometheus (desde carpeta de infra)
```bash
docker-compose up -d
```

### Paso 4: Acceder a Prometheus
- **UI de Prometheus:** http://localhost:9090
- **Métricas en texto:** http://localhost:8080/actuator/prometheus
- **Health check:** http://localhost:8080/actuator/health

---

## 📊 Métricas disponibles automáticamente

| Métrica | Descripción |
|---------|-------------|
| `jvm_memory_used_bytes` | Memoria JVM usada |
| `jvm_threads_live` | Threads activos |
| `process_cpu_usage` | CPU del proceso |
| `http_server_requests_seconds` | Latencia de requests HTTP |
| `http_server_requests_seconds_count` | Cantidad de requests |
| `system_load_average_1m` | Carga del sistema |

---

## 🔍 Verificación en Prometheus UI

1. Ir a http://localhost:9090
2. En "Graph" → escribir: `http_server_requests_seconds_count`
3. Deberías ver los requests a tu API

---

## ❓ Notas importantes

- **host.docker.internal:** Permite que el contenedor de Prometheus acceda a `localhost:8080` del host
- Si usas Windows y no funciona, reemplaza por: `docker.for.win.localhost` o la IP de tu máquina
- El intervalo de scraping está en 10 segundos (data casi en tiempo real)
- Prometheus guarda datos por 30 días (configurable en `storage.tsdb.retention.time`)


