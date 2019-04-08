FROM openjdk:11
VOLUME /tmp
ADD target/rss-feed-server-DEVELOP.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]
EXPOSE 8080