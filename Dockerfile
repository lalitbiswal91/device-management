FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/device-management-0.0.1-SNAPSHOT.jar device-management-api.jar
ENTRYPOINT ["java", "-jar", "device-management-api.jar"]