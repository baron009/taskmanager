FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /workspace/app

# Copy gradle files first to cache dependencies
COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY backend/build.gradle.kts backend/

# Download dependencies
RUN ./gradlew dependencies

# Copy source code
COPY backend/src backend/src

# Build the application
RUN ./gradlew backend:build -x test

FROM eclipse-temurin:21-jre-alpine
VOLUME /tmp
COPY --from=build /workspace/app/backend/build/libs/*.jar app.jar

# Add health check using exec form
HEALTHCHECK --interval=30s --timeout=3s \
    CMD ["wget", "--quiet", "--tries=1", "--spider", "http://localhost:8080/actuator/health", "||", "exit", "1"]

# Use exec form for ENTRYPOINT
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]
