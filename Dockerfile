FROM ubuntu:latest

LABEL authors="sarug"

RUN apt-get update &&  \
    apt-get install -y openjdk-17-jdk maven

WORKDIR /app

COPY pom.xml /app
COPY src /app/src

RUN mvn clean package

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "target/DockerBandymas-0.0.1-SNAPSHOT.jar"]