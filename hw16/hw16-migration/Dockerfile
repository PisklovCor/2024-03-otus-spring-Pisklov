FROM maven:3.6.3-openjdk-17

RUN mkdir hw-16-migration

WORKDIR hw-16-migration

COPY . .

ENV LIQUIBASE_DATABASE="jdbc:postgresql://postgres:5432/postgres" \
  LIQUIBASE_USERNAME="admin" \
  LIQUIBASE_PASSWORD="admin"

CMD ["mvn", "liquibase:update", "-Pdocker", "-e"]