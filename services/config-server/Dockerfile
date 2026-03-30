#CONFIG SERVER

# -------- BUILD STAGE --------
FROM maven:3.9.6-eclipse-temurin-17-alpine AS build
WORKDIR /app

# Cache dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source and build the JAR
COPY src ./src
RUN mvn clean package -DskipTests

# -------- RUN STAGE --------
FROM eclipse-temurin:17-jre-alpine
LABEL maintainer="sidof.xyz"

WORKDIR /app

# Copy the JAR from the build stage
# ArtifactId is 'config-server' based on your POM
COPY --from=build /app/target/config-server-*.jar app.jar

# Config Server standard port
EXPOSE 8888

# Default Environment Variables
ENV SPRING_PROFILES_ACTIVE=native
ENV EUREKA_URL="http://eureka-server:8761/eureka/"

# SECURITY: Run as non-root user
# todo RUN addgroup -S sidofgroup && adduser -S sidofuser -G sidofgroup
#todo USER sidofuser

# Optimized JVM settings
# Config Server is usually the first to start after Eureka
ENTRYPOINT ["java", \
            "-Xmx512m", \
            "-Xms256m", \
            "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", \
            "-Deureka.client.serviceUrl.defaultZone=${EUREKA_URL}", \
            "-jar", "app.jar"]