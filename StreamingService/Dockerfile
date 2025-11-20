FROM gradle:jdk17 AS builder

WORKDIR /build
COPY src ./src
COPY build.gradle ./
COPY settings.gradle ./

RUN gradle build --no-daemon -x test --parallel

FROM amazoncorretto:17-alpine

WORKDIR /app
COPY --from=builder build/build/libs/*.jar streaming-app.jar
ENTRYPOINT [ "java", "-jar", "streaming-app.jar" ]