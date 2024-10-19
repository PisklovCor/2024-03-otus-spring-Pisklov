FROM maven:3.6.3-openjdk-17

RUN mkdir hw-16-migration

WORKDIR hw-16-migration

COPY . .

CMD ["mvn", "liquibase:update", "-Pdocker", "-e"]