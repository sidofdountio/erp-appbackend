#SHOP DOCKERFILE
# -------- BUILD STAGE --------
FROM maven:3.9.6-eclipse-temurin-17-alpine AS build
WORKDIR /app

# Cache dependencies to speed up subsequent builds
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
# Note: Ensure the JAR name matches your pom.xml artifactId
COPY --from=build /app/target/shop-service-*.jar app.jar

# Shop Service standard port
EXPOSE 9001

# Default Environment Variables (Overridden by Docker Compose / Master .env)
ENV SPRING_PROFILES_ACTIVE=prod
ENV CONFIG_SERVER_URL="http://config-server:8888"
ENV EUREKA_URL="http://eureka-server:8761/eureka/"

# Database and Network Variables injected at runtime
ENV DB_SHOP_URL=""
ENV DB_USERNAME=""
ENV DB_PASSWORD=""
ENV SERVER_IP=""

# SECURITY: Run as a non-root user
#todo: RUN addgroup -S sidofgroup && adduser -S sidofuser -G sidofgroup
#todo: USER sidofuser

# Optimized JVM settings for production
ENTRYPOINT ["java", \
            "-Xmx512m", \
            "-Xms256m", \
            "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", \
            "-Dspring.cloud.config.uri=${CONFIG_SERVER_URL}", \
            "-Deureka.client.serviceUrl.defaultZone=${EUREKA_URL}", \
            "-jar", "app.jar"]