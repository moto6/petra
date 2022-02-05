FROM adoptopenjdk/openjdk11
WORKDIR /tmp
EXPOSE 8080
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} carrot-server.jar
ENTRYPOINT ["java", "-jar", "carrot-server.jar"]
