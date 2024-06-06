FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
VOLUME /app/data
ENTRYPOINT ["java","-jar","/app.jar"]