FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/customer-service-0.0.1-SNAPSHOT.jar customer-service.jar

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "customer-service.jar"]
