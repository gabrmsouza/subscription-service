FROM eclipse-temurin:21.0.3_9-jre-alpine

COPY build/libs/*.jar /opt/app/application.jar

RUN addgroup -S spring && adduser -S spring -G spring

USER spring:spring

CMD java -jar /opt/app/application.jar