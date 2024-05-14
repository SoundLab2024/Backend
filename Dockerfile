# Soundlab Dockerfile
# Build Stage
FROM gradle:8.4.0-jdk17-alpine as buildstage
WORKDIR /buildstage
COPY build.gradle.kts settings.gradle.kts ./
COPY ./src ./src
RUN gradle build -x test --no-daemon

# Optimization Stage
FROM eclipse-temurin:17-jre as optimizer
WORKDIR /optimizer
COPY --from=buildstage /buildstage/build/libs/*.jar ./server.jar
RUN java -Djarmode=layertools -jar server.jar extract

# Runner Stage
FROM eclipse-temurin:17-jre as runner
WORKDIR /runner

# RUN rm -rf /runner/*
COPY --from=optimizer /optimizer/dependencies/ ./
COPY --from=optimizer /optimizer/spring-boot-loader/ ./
COPY --from=optimizer /optimizer/snapshot-dependencies/ ./
COPY --from=optimizer /optimizer/application/ ./
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "org.springframework.boot.loader.launch.JarLauncher"]