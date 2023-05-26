FROM openjdk:8-jre-alpine

EXPOSE 9090

COPY ./target/demo0.0.1-SNAPSHOT.jar demo0.0.1-SNAPSHOT.jar


CMD ["java", "-jar","/demo0.0.1-SNAPSHOT.jar"]
