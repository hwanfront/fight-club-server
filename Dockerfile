FROM eclipse-temurin:17-jdk

USER root
RUN apt-get update && apt-get install -y bash && rm -rf /var/lib/apt/lists/*

WORKDIR /app
ARG PROFILE
ENV SPRING_PROFILES_ACTIVE=$PROFILE

COPY build/libs/fight-club-server-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["bash", "-c", "\
  echo 'Waiting for MySQL to be ready...' && \
  while ! echo > /dev/tcp/fight-club-mysql/3306; do \
    sleep 1; \
  done && \
  echo 'MySQL is up, starting app...' && \
  java -jar app.jar \
"]
