FROM adoptopenjdk:11-jdk-hotspot
VOLUME /tmp
COPY target/api-service-*.jar app.jar
EXPOSE 8085
ENTRYPOINT [ "java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar" ]