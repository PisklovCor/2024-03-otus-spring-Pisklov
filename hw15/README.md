## Использование метрики, healthchecks и logfile

Приложение с применением Spring Boot Actuator

### --- Приложение ---

#### Сборка проекта и тестирование:
```shell
mvn clean package
```

#### Доступ до UI-интерфейса:
````
http://localhost:8080/
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
