FROM openjdk:17
VOLUME /tmp
EXPOSE 8080
ARG JAR_NAME=backend-server-manager
ARG TAG=latest
ARG VERSION=0.0.1-SNAPSHOT
ARG JAR_FILE=build/libs/${JAR_NAME}-${VERSION}.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
