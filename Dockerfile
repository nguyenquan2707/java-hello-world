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
COPY --from=builder /app/target/*.jar app.jar

# Set environment variable for Firebase credentials (read at runtime)
ENV FIREBASE_CONFIG_BASE64=""

# Copy startup script to decode and run
COPY start.sh .

# Make script executable
RUN chmod +x start.sh

CMD ["./start.sh"]
