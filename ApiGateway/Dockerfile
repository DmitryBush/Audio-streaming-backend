FROM gradle:jdk25-alpine AS builder

WORKDIR /build
COPY src ./src
COPY build.gradle.kts ./
COPY settings.gradle.kts ./

RUN gradle build -x test --parallel

FROM amazoncorretto:25-alpine

WORKDIR /app
COPY --from=builder build/build/libs/*.jar gateway-app.jar
ENTRYPOINT [ "java", "-jar", "gateway-app.jar" ]