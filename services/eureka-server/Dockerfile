# -------- BUILD STAGE --------
FROM maven:3.9.6-eclipse-temurin-17-alpine AS build
WORKDIR /app

# Cache dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Build the JAR
COPY src ./src
RUN mvn clean package -DskipTests

# -------- RUN STAGE --------
FROM eclipse-temurin:17-jre-alpine
LABEL maintainer="sidof.xyz"

WORKDIR /app

# Copy the JAR from the build stage
# Ensure the name matches your <artifactId> in pom.xml
COPY --from=build /app/target/eureka-server-*.jar app.jar

# Standard Eureka Port
EXPOSE 8761

# Environment Variables
ENV SPRING_PROFILES_ACTIVE=prod

# SECURITY: Run as a non-root user
#todo RUN addgroup -S sidofgroup && adduser -S sidofuser -G sidofgroup
#todo USER sidofuser

# Optimized JVM settings
# Eureka is lightweight, but needs a stable heap to manage service heartbeats
ENTRYPOINT ["java", \
            "-Xmx512m", \
            "-Xms256m", \
            "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", \
            "-jar", "app.jar"]