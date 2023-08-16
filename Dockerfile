# Use the official image as a parent image
FROM openjdk:17 as build

# Set the argument for the version tag
ARG TAG="0.0.2"

# Set the location of the app
WORKDIR /usr/app

# Copy the local pre-built jar to the container
COPY build/libs/server-manager-${TAG}-SNAPSHOT.jar /usr/app/app.jar

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Run the app. You can also use ENTRYPOINT if needed.
CMD ["java", "-jar", "app.jar"]