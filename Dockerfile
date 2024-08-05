FROM ubuntu:latest

LABEL authors="Dariuso"

RUN apt-get update &&  \
    apt-get install -y openjdk-17-jdk maven && \
    apt-get -y install git

WORKDIR /app

RUN git clone https://github.com/DariusSo/DockerBandymas.git .


RUN mvn clean package

EXPOSE 8080



ENTRYPOINT ["java", "-jar", "target/DockerBandymas-0.0.1-SNAPSHOT.jar"]