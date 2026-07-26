FROM node:22-bookworm-slim AS web-build
WORKDIR /web
COPY apps/web/package*.json ./
RUN npm ci
COPY apps/web ./
RUN npm run build

FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /workspace
COPY apps/api/pom.xml .
RUN mvn -q -DskipTests dependency:go-offline
COPY apps/api/src src
COPY --from=web-build /web/dist src/main/resources/static
RUN mvn -q -DskipTests package

FROM eclipse-temurin:17-jre
WORKDIR /app
RUN apt-get update && apt-get install -y --no-install-recommends wget && rm -rf /var/lib/apt/lists/*
RUN addgroup --system studybuddy && adduser --system --ingroup studybuddy studybuddy
COPY --from=build /workspace/target/*.jar app.jar
USER studybuddy
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
