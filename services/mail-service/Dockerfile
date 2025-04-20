FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/mail-service-0.0.1-SNAPSHOT.jar mail-service.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "mail-service.jar"]
