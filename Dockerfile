FROM amazoncorretto:17-alpine-jdk
MAINTAINER dev@nectar.software
WORKDIR /etc/tokens-service
ARG JAR_FILE=build/libs/tokens-service-3.0.2-alpha.jar
COPY ${JAR_FILE} tokens-service.jar
ENTRYPOINT ["java","-jar","tokens-service.jar"]
