## Приложение миграции liquibase

### Переход в папку сабмодуля
````shell
cd .\hw16\hw16-service
````

### Локальный запуск postgres:
````shell
docker-compose up -d
````

#### Сборка проекта и тестирование:
```
mvn clean package
```

### Сборка образа
````shell
docker build -t pisklovcor/hw-16-service-docker:dockerfile .  
````

### Запуск docker образа:
````shell
docker run --name hw-16-service -p 8000:8000 -e spring.datasource.url='jdbc:postgresql://postgres:5432/postgres' --network=hw-networks -d pisklovcor/hw-16-service-docker:dockerfile
````

### Проверка network:
````shell
docker network ls
````