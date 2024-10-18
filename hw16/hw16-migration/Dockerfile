FROM maven:3.6.3-openjdk-17

RUN mkdir hw-16-migration

WORKDIR hw-16-migration

COPY . .

ENV JDBC_URL="jdbc:postgresql://postgres:5432/postgres" \
  JDBC_USERNAME="admin" \
  JDBC_PASSWORD="admin"

CMD ["mvn", "liquibase:update", "-Pdocker", "-e"]