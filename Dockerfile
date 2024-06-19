# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container at /app
COPY target/pizzaria-0.0.1-SNAPSHOT.jar /app/pizzaria.jar

# Expose port 443 to the outside world
EXPOSE 443

# Set environment variables
ENV JAVA_OPTS=""

# Run the Spring Boot application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Dspring.profiles.active=prod -jar /app/pizzaria.jar"]

# Add a health check to ensure the container is healthy
HEALTHCHECK --interval=30s --timeout=10s --start-period=30s --retries=3 CMD curl -f http://localhost:443/actuator/health || exit 1
