# Stage 1: Build the application with Maven
FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Create a lightweight container with the JAR file
FROM adoptopenjdk:17-jdk
WORKDIR /app
COPY --from=build /app/target/employeeCrud.jar /app/app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
