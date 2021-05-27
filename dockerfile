FROM openjdk:8-jdk-alpine
WORKDIR /app

COPY target/analytics-service.jar .
COPY docker/start.sh .

EXPOSE 8080

CMD [ "sh", "./start.sh" ]