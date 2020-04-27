FROM openjdk:11
ADD target/kick-scooter-support.jar kick-scooter-support.jar
ENTRYPOINT ["java", "-jar", "kick-scooter-support.jar"]