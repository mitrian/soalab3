FROM gradle:7.6-jdk17 AS build
WORKDIR /app
COPY build.gradle settings.gradle ./
COPY eureka-server ./eureka-server
RUN gradle :eureka-server:bootJar --no-daemon

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/eureka-server/build/libs/*.jar app.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "app.jar"]

