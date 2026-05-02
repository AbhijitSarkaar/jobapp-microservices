
FROM eclipse-temurin:25

WORKDIR /root

COPY ./target/*.jar /root/gateway.jar

CMD ["java", "-Dspring.profiles.active=docker", "-jar", "gateway.jar"]
