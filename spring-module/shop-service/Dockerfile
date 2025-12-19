FROM gradle:7.6-jdk17 AS build
WORKDIR /app
COPY build.gradle settings.gradle ./
COPY shop-service ./shop-service
RUN gradle :shop-service:bootJar --no-daemon

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/shop-service/build/libs/*.jar app.jar
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "app.jar"]

