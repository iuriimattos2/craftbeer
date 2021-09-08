FROM maven:3.5.2-jdk-8-alpine AS MAVEN_BUILD

COPY pom.xml /build/
COPY src /build/src/

WORKDIR /build/
RUN mvn clean package

FROM openjdk:8-jre-alpine

WORKDIR /app

ARG JAR_FILE=/build/target/craftbeer.jar

COPY --from=MAVEN_BUILD ${JAR_FILE} /app/app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]