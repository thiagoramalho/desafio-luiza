FROM adoptopenjdk/openjdk11:alpine
VOLUME /tmp
EXPOSE 8080
RUN mkdir -p /app/
RUN mkdir -p /app/logs/
ADD target/desafio-luiza-0.0.1.jar /app/desafio-luiza.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=dev", "-jar", "/app/desafio-luiza.jar"]