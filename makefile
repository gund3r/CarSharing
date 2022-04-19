run:
	java -jar ./target/CarSharing-1.0-SNAPSHOT-jar-with-dependencies.jar

build: clean update
	mvn verify

clean:
	mvn clean

update:
	mvn versions:update-properties versions:display-plugin-updates

.DEFAULT_GOAL := build-run
build-run: build run