FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/reward-service-0.0.1-SNAPSHOT.jar reward-service.jar

EXPOSE 8084

ENTRYPOINT ["java", "-jar", "reward-service.jar"]
