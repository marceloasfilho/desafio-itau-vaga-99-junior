FROM maven:3.9.9-amazoncorretto-21-al2023 AS build

COPY src /app/src
COPY pom.xml /app

WORKDIR /app
RUN mvn clean package install

FROM openjdk:21-slim

COPY --from=build /app/target/transacoes-0.0.1-SNAPSHOT.jar /app/app.jar

WORKDIR /app
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]