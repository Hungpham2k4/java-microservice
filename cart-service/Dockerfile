FROM openjdk:21-jdk-slim

# Tạo thư mục ứng dụng trong container
WORKDIR /app

# Sao chép file JAR vào thư mục làm việc trong container
COPY target/cart-service-0.0.1-SNAPSHOT.jar /app/cart-service-0.0.1-SNAPSHOT.jar

# Chạy ứng dụng khi container khởi động
ENTRYPOINT ["java", "-jar", "cart-service-0.0.1-SNAPSHOT.jar"]

EXPOSE 8083