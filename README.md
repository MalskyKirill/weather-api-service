# Weather Service

Небольшой Spring Boot-сервис, который ходит в Yandex Weather API и отдаёт погоду по коду города и количеству дней.

## Стек

- Java 21
- Spring Boot 3.x
- Maven
- Docker (опционально)
- Yandex Weather API

---

## Конфигурация

Файл: `src/main/resources/application.properties`

```properties
yandex.weather.api.key=demo_yandex_weather_api_key_ca6d09349ba0
yandex.weather.base.url=https://api.weather.yandex.ru/v2/forecast
server.port=9090
```

`server.port` можно поменять при необходимости.

---

## Сборка

Собрать jar:

```bash
mvn clean package 
```

После сборки jar лежит в:

```text
target/WeatherServiceMaven-1.0-SNAPSHOT.jar
```

---

## Запуск локально

Через Spring Boot:

```bash
mvn spring-boot:run
```

или напрямую через jar:

```bash
java -jar target/WeatherServiceMaven-1.0-SNAPSHOT.jar
```

По умолчанию сервис доступен по адресу:

```text
http://localhost:9090
```

(если в `application.properties` указан `server.port=9090`).

---

## Запуск в Docker

### Dockerfile

```dockerfile
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY target/WeatherServiceMaven-1.0-SNAPSHOT.jar app.jar

EXPOSE 9090

ENV JAVA_OPTS=""

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
```

### Сборка Docker-образа

```bash
mvn clean package 
docker build -t weather-service .
```

### Запуск контейнера

Одинаковый порт снаружи и внутри:

```bash
docker run --rm -p 9090:9090 weather-service
```

Другой порт снаружи (пример: хост 8080 → контейнер 9090):

```bash
docker run --rm -p 8080:9090 weather-service
```

---

## API

### GET `/weather`

**Параметры:**

- `code` — код города (`MSC`, `SPB`, ...);
- `days` — количество дней прогноза (`1–11`).

**Пример запроса:**

```http
GET http://localhost:9090/weather?code=MSC&days=3
```


