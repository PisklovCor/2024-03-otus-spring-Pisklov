## Использование метрики, healthchecks и logfile

Приложение с применением Spring Boot Actuator

### --- Приложение ---

#### Сборка проекта и тестирование:
```shell
mvn clean package
```

#### UI-интерфейсы:
#### Главная страница
````
http://localhost:8080/
````

#### HAL Explorer - Swagger для Spring Data REST
````
http://localhost:8080/daterest/explorer/index.html#uri=/daterest/
````

### --- Prometheus ---

#### Запуск прометеуса в контейнере:
````shell
docker-compose up -d
````

#### Доступ до UI-интерфейса:
````
http://localhost:9090/
````
