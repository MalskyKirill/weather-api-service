FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY target/WeatherServiceMaven-1.0-SNAPSHOT.jar app.jar

EXPOSE 9090

ENV JAVA_OPTS=""

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
