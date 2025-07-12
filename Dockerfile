# Dockerfile
# Use Maven to build the app
FROM maven:3.8.7-eclipse-temurin-17 as builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Use a lighter base image to run the app
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
COPY firebase-service-account.json firebase-service-account.json
CMD ["java", "-jar", "app.jar"]