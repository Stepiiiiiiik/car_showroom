FROM gradle:8.11.1-jdk21 AS builder

RUN groupadd -r hello && useradd --no-log-init -r -g hello hello

WORKDIR /app

COPY . .

RUN ./gradlew build --no-daemon

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=builder /app/build/libs/java-template-1.0-SNAPSHOT.jar  app.jar

ENTRYPOINT ["java"]
CMD ["-jar", "/app/app.jar"]
