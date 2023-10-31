FROM openjdk:17-jdk-slim
VOLUME /tmp
ADD target/WeHelp_stage3_practice-0.0.1-SNAPSHOT.jar app-0.0.1.jar
ADD data/application.properties /app/config/application.properties
EXPOSE 8080
LABEL authors="linwunyu"
ENTRYPOINT ["java","-jar","app-0.0.1.jar", "--spring.config.name=application", "--spring.config.location=/app/config/"]