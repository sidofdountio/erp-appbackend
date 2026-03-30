# API GATEWAY

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
COPY --from=build /app/target/api-gateway-*.jar app.jar

# Gateway standard port
EXPOSE 9009

# Default Environment Variables
ENV SPRING_PROFILES_ACTIVE=prod
ENV CONFIG_SERVER_URL="http://config-server:8888"
ENV EUREKA_URL="http://eureka-server:8761/eureka/"

# Security: Run as a non-root user
#RUN addgroup -S sidofgroup && adduser -S sidofuser -G sidofgroup
#USER sidofuser

# JVM settings: Gateway handles more concurrent connections, so we keep heap stable
ENTRYPOINT ["java", \
            "-Xmx512m", \
            "-Xms256m", \
            "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", \
            "-Dspring.cloud.config.uri=${CONFIG_SERVER_URL}", \
            "-Deureka.client.serviceUrl.defaultZone=${EUREKA_URL}", \
            "-jar", "app.jar"]
