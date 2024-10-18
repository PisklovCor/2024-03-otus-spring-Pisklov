## Приложение миграции liquibase

### Локальный запуск postgres:
````shell
cd .\hw16\hw16-migration
````

### Локальный запуск postgres:
````shell
docker-compose up -d
````

### Запуск миграции на локальной базе данных
````
mvn liquibase:update
````

### Сборка образа
````shell
docker build -t pisklovcor/hw-16-migration-docker:dockerfile .  
````

### Запуск образа
````shell
docker run --name hw16-db-migration --env liquibase.username:'admin' --env liquibase.password:'admin' --env liquibase.url:'jdbc:postgresql://postgres:5432/postgres' --network=hw-networks pisklovcor/hw-16-migration-docker:dockerfile
docker run --name hw16-db-migration --env LIQUIBASE_USERNAME:'admin' --env LIQUIBASE_PASSWORD:'admin' --env LIQUIBASE_URL:'jdbc:postgresql://postgres:5432/postgres' --network=hw-networks pisklovcor/hw-16-migration-docker:dockerfile
docker run --name hw16-db-migration -v --url='jdbc:postgresql://postgres:5432/postgres' -v --username='admin' -v --password=admin --network=hw-networks pisklovcor/hw-16-migration-docker:dockerfile
````

### Проверка network:
````shell
docker network ls
````

docker run --name bd-migration --network=hw-networks pisklovcor/hw-16-migration-docker:dockerfile update --liquibase.username:'admin' --liquibase.password:'admin' --liquibase.url:'jdbc:postgresql://postgres:5432/postgres' 