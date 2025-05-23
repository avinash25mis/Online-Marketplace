FROM gradle:jdk11 as builder

# Copy local code to the container image.
WORKDIR /app
COPY . /app

RUN gradle build

# Use AdoptOpenJDK for base image.
# It's important to use OpenJDK 8u191 or above that has container support enabled.
# https://hub.docker.com/r/adoptopenjdk/openjdk8
# https://docs.docker.com/develop/develop-images/multistage-build/#use-multi-stage-builds
FROM adoptopenjdk/openjdk11:alpine-slim

# Copy the jar to the production image from the builder stage.
COPY --from=builder /app/build/libs/*.jar /app.jar

# Run the web service on container startup.
CMD ["java","-agentlib:jdwp=transport=dt_socket,address=*:5005,server=y,suspend=n", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]