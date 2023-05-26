FROM openjdk:8-jre-alpine

EXPOSE 9090

WORKDIR /app

COPY ./target/demo0.0.1-SNAPSHOT.jar ./demo0.0.1-SNAPSHOT.jar


ENTRYPOINT ["java", "-jar","demo0.0.1-SNAPSHOT.jar"]
