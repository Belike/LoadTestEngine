FROM openjdk:17-jdk-alpine
VOLUME /tmp
EXPOSE 8080
RUN mkdir -p /camunda/
ADD /target/LoadTestEngine.jar /camunda/LoadTestEngine.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=k8s", "-jar", "camunda/LoadTestEngine.jar"]
