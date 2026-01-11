FROM gradle:jdk17 AS builder

WORKDIR /build
COPY src ./src
COPY build.gradle.kts ./
COPY settings.gradle.kts ./

RUN gradle build --no-daemon -x test --parallel

FROM amazoncorretto:17-alpine

WORKDIR /app
COPY --from=builder build/build/libs/*.jar user-app.jar
ENTRYPOINT [ "java", "-jar", "user-app.jar" ]