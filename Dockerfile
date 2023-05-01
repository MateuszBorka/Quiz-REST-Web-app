FROM openjdk:17
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=target/Quiz-REST-Web-app-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
CMD ["java", "-jar", "/app.jar"]
