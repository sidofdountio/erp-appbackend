# AUTH SERVER
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

# Copy the JAR and rename for a stable ENTRYPOINT
COPY --from=build /app/target/oauth-server-*.jar app.jar

EXPOSE 9000

# Default Environment Variables (Overridden by Master .env at runtime)
ENV SPRING_PROFILES_ACTIVE=prod
ENV CONFIG_SERVER_URL="http://config-server:8888"
ENV EUREKA_URL="http://eureka-server:8761/eureka/"

# These will be injected by Docker Compose from your Master .env
ENV DB_AUTH_URL=""
ENV DB_USERNAME=""
ENV DB_PASSWORD=""
ENV SERVER_IP=""

# SECURITY: Run as non-root user (Highly recommended for Prod)
#todo:RUN addgroup -S sidofgroup && adduser -S sidofuser -G sidofgroup
#todo:USER sidofuser

# Optimized JVM settings for microservices
ENTRYPOINT ["java", \
            "-Xmx512m", \
            "-Xms256m", \
            "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", \
            "-Dspring.cloud.config.uri=${CONFIG_SERVER_URL}", \
            "-Deureka.client.serviceUrl.defaultZone=${EUREKA_URL}", \
            "-jar", "app.jar"]