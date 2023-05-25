FROM openjdk:8-jre-alpine

EXPOSE 3030

COPY ./target/java-maven-app*.jar .


CMD ["java", "-jar","java-maven-app*.jar"]
