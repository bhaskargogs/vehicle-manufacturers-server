FROM openjdk:14-jdk-alpine
ARG DEPLOYMENT_ENV
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENV SPRING_BOOT_PROFILE=${DEPLOYMENT_ENV}
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=${SPRING_BOOT_PROFILE}","app.jar"]