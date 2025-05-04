FROM eclipse-temurin:17-jdk

USER root
RUN apt-get update && apt-get install -y netcat && rm -rf /var/lib/apt/lists/*

WORKDIR /app
ARG PROFILE
ENV SPRING_PROFILES_ACTIVE=$PROFILE

COPY build/libs/fight-club-server-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
