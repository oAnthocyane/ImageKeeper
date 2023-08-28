FROM openjdk:17-alpine
ADD /target/resourceserver-0.0.1-SNAPSHOT.jar server.jar
ENTRYPOINT ["java", "-jar", "server.jar"]