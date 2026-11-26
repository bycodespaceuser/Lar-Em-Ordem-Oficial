FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY . .
RUN chmod +x mvnw && ./mvnw -B -DskipTests package
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/lar-em-ordem-0.0.1-SNAPSHOT.jar app.jar
RUN mkdir -p dados
ENV H2_CONSOLE_ENABLED=false
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
