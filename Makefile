run:
	mvn clean install && java -jar target/unUber-1.0-SNAPSHOT.jar server config.yml

deploy:
	mvn clean install && docker-compose up --build
