FROM openjdk:8-jre
COPY ./target/*uber.jar uber.jar
COPY ./config.yml .
ENTRYPOINT ["java", "-jar", "uber.jar", "server", "config.yml"]