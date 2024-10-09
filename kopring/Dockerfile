FROM openjdk:17.0.2-jdk-slim-buster AS builder

WORKDIR /app
COPY gradlew build.gradle.kts settings.gradle.kts ./
COPY gradle ./gradle
COPY src/main ./scr/main

RUN ./gradlew bootJar

FROM openjdk:17.0.2-jdk-slim-buster

WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar

#ENV PROFILE="dev"

#ENTRYPOINT java -jar app.jar --spring.profiles.active=$PROFILE
ENTRYPOINT ["java", "-jar", "./app.jar"]

#FROM openjdk:17.0.2-jdk-slim-buster
#COPY ./build/libs/*.jar /build/libs/app.jar
#ENV TZ=Asia/Seoul
#ENTRYPOINT ["java", "-jar", "/build/libs/app.jar"]
#CMD ["app docker image test"]