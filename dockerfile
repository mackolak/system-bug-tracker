FROM gradle:8.7 as builder
COPY . /app
WORKDIR /app
RUN gradle build -x test


FROM eclipse-temurin:21.0.3_9-jdk-ubi9-minimal
COPY --from=builder /app/build/libs/*.jar /app/
WORKDIR /app
CMD ["java", "-jar", "system-bug-tracker-0.0.1-SNAPSHOT.jar"]