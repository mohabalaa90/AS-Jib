FROM openjdk:8-jre-alpine

EXPOSE 9090

WORKDIR /app

COPY ./target/demo-0.0.1-SNAPSHOT.jar ./demo-0.0.1-SNAPSHOT.jar


ENTRYPOINT ["java", "-jar","demo-0.0.1-SNAPSHOT.jar"]
