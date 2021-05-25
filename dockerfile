FROM openjdk:8-jdk-alpine
WORKDIR /app

COPY target/analytics-service.jar .

EXPOSE 8080

CMD [ "java", "-jar", "analytics-service.jar" ]