## ДЗ-17: приложения с изолированными (с помощью Resilience4J) внешними вызовами

#### Сборка проекта и тестирование:
```
mvn clean package
```

#### Запуск контейнеров:
````shell
docker-compose up -d
````

#### UI-интерфейсы:

###### Сервис для рабоыт с книгами
````
http://localhost:8000/swagger-ui/index.html
````

###### Сервис для рабоыт с заказами
````
http://localhost:8001/swagger-ui/index.html
````

###### Сервис для рабоыт с аккаунтами пользователей
````
http://localhost:8002/swagger-ui/index.html
````

###### Discovery service
````
http://localhost:8761/
````

###### Дашборд  zipkin
````
http://localhost:9411/zipkin/
````

###### Дашборд prometheus
````
http://localhost:9090/
````

###### Дашборд grafana (JVM)
````
http://localhost:3000/d/ZFSul8UMz/jvm-micrometer
````

###### RabbitMQ (management)
````
http://localhost:15672/
````
