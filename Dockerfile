# Sử dụng image chính thức của OpenJDK 17
FROM openjdk:17

# Thư mục làm việc bên trong container
WORKDIR /app

# Copy file JAR từ host vào container
COPY target/hello-world-1.0.jar app.jar

# Command để chạy ứng dụng
CMD ["java", "-jar", "app.jar"]
