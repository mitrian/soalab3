FROM gradle:7.6-jdk17 AS build
WORKDIR /app
COPY build.gradle settings.gradle ./
COPY api-gateway ./api-gateway
RUN gradle :api-gateway:bootJar --no-daemon

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/api-gateway/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

