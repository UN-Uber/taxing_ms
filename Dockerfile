FROM adoptopenjdk/openjdk11:ubi
EXPOSE 8080 8081

COPY config.yml /service/config/
COPY target/unUber-1.0-SNAPSHOT.jar /service/bin/

CMD java -jar /service/bin/unUber-1.0-SNAPSHOT.jar server /service/config/config.yml
