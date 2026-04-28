
FROM eclipse-temurin:25

WORKDIR /root

COPY ./target/*.jar /root/jobms.jar

CMD ["java", "-Dspring.profiles.active=dev", "-jar", "jobms.jar"]
