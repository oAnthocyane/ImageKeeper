FROM openjdk:17-alpine
ADD /target/TelegramBot-1.0-SNAPSHOT-jar-with-dependencies.jar telegrambot.jar
ENTRYPOINT ["java", "-jar","telegrambot.jar"]