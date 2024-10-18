## Приложение миграции liquibase

### Переход в папку сабмодуля
````shell
cd .\hw16\hw16-migration
````

### Локальный запуск postgres:
````shell
docker-compose up -d
````

#### Сборка проекта (profiles:default тесты и миграция отключены)
```
mvn clean package
```

### Запуск миграции на локальной базе данных
````
mvn liquibase:update -Dliquibase.url=jdbc:postgresql://localhost:5432/postgres -Dliquibase.username=admin -Dliquibase.password=admin -Pdocker
````

### Сборка образа
````shell
docker build -t pisklovcor/hw-16-migration-docker:dockerfile .  
````