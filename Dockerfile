# Use a base image with Java and Maven installed
FROM maven:3.9.7-sapmachine-21 AS build

WORKDIR /app1
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
#COPY .env .
RUN mvn package -DskipTests
RUN echo "done"

FROM openjdk:21-slim
WORKDIR /app1
# Copy the built artifact from the build stage to the app directory
COPY --from=build app1/target/*.jar app1.jar

# Set the startup command to run your application
ENTRYPOINT ["java", "-jar", "/app1/app1.jar"]