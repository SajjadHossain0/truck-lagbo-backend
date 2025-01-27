FROM maven:3.8.3-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
COPY --from=build /target/truck_lagbo_backend-0.0.1-SNAPSHOT.jar truck_lagbo_backend.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "truck_lagbo_backend.jar"]