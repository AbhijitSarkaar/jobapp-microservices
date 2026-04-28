
FROM eclipse-temurin:25

WORKDIR /root

COPY ./target/*.jar /root/reviewms.jar

CMD ["java", "-Dspring.profiles.active=dev", "-jar", "reviewms.jar"]
