FROM openjdk:8-jdk-alpine
LABEL maintainer="Aliaksandr Rahavoi <aliaksandr.rahavoi@gmail.com>"

VOLUME /tmp
EXPOSE 8080

ARG JAR_FILE=target/university-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} search-engine.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/search-engine.jar"]