FROM gradle:jdk25-alpine AS builder

WORKDIR /build
COPY OutboxLibrary/build.gradle.kts ./OutboxLibrary/
COPY OutboxLibrary/src ./OutboxLibrary/src/

COPY UuidAdapter/build.gradle.kts ./UuidAdapter/
COPY UuidAdapter/src ./UuidAdapter/src/

COPY StreamingService/build.gradle ./StreamingService/
COPY StreamingService/src ./StreamingService/src/

COPY UserService/build.gradle.kts ./UserService/
COPY UserService/src ./UserService/src/

COPY SearchService/build.gradle.kts ./SearchService/
COPY SearchService/src ./SearchService/src/

COPY settings.gradle.kts .

RUN gradle StreamingService:bootJar -x test --parallel

FROM amazoncorretto:25-alpine

WORKDIR /app
COPY --from=builder /build/StreamingService/build/libs/*.jar streaming-app.jar
ENTRYPOINT [ "java", "-jar", "streaming-app.jar" ]