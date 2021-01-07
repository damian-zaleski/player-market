FROM openjdk:11-jdk-slim
VOLUME /tmp
COPY run.sh .
COPY application/build/libs/application.jar .
ENTRYPOINT ["/run.sh"]