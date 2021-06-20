FROM openjdk:8-jdk-alpine
ARG JAR_FILE=build/libs/exchange-rates-api-1.0.jar
COPY ${JAR_FILE} rates-api.jar
ENTRYPOINT ["java","-jar","/rates-api.jar"]