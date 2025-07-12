# Stage 1: Build Java App
FROM maven:3.8.7-eclipse-temurin-17 AS builder

WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run app with Firebase SDK
FROM eclipse-temurin:17

WORKDIR /app

# Copy built JAR from builder stage
#COPY --from=builder /app/target/*.jar app.jar
COPY --from=builder /app/target/java-hello-world-1.0-SNAPSHOT.jar app.jar


# Set environment variable for Firebase credentials (read at runtime)
ENV FIREBASE_CONFIG_BASE64=""
CMD ["java", "-jar", "app.jar"]
