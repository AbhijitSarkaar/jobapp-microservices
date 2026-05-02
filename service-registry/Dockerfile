
FROM eclipse-temurin:25

WORKDIR /root

COPY ./target/*.jar /root/service-registry.jar

CMD ["java", "-Dspring.profiles.active=docker", "-jar", "service-registry.jar"]
