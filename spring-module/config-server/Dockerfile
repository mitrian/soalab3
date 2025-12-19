FROM gradle:7.6-jdk17 AS build
WORKDIR /app
COPY build.gradle settings.gradle ./
COPY config-server ./config-server
RUN gradle :config-server:bootJar --no-daemon

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/config-server/build/libs/*.jar app.jar
COPY --from=build /app/config-server/config-repo ./config-repo
EXPOSE 8888
ENTRYPOINT ["java", "-jar", "app.jar"]

