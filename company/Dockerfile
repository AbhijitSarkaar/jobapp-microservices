FROM eclipse-temurin:25

WORKDIR /root

COPY ./target/*.jar /root/companyms.jar

CMD ["java", "-Dspring.profiles.active=dev", "-jar", "companyms.jar"]
