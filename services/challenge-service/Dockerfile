FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/challenge-service-0.0.1-SNAPSHOT.jar challenge-service.jar

EXPOSE 8085

ENTRYPOINT ["java", "-jar", "challenge-service.jar"]
