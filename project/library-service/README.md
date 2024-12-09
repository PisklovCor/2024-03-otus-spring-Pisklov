### Сервис вляется головным хранилищем книг и выполняет выдачу книг пользователям

#### Локальная сборка проекта:
```
mvn clean package
```
* Для корректной работы необходимо запустить (docker-compose.yaml) postgres_library

### Внешнии параметры:
| Название                 | описание                            | local          | docker                 |
|--------------------------|-------------------------------------|----------------|------------------------|
| SECURITY_ENABLED         | Флаг включения безопасности         | true           | false                  |
| DATASOURCE_URL           | Адрес базы данных  PostgreSQL       | localhost:5433 | postgres_library:5432  |
| RABBITMQ_HOST            | Адрес    rabbitmq                   | localhost      | rabbitmq               |
| CLOUD_CONFIG_ENABLE      | Флаг включения внешней конфигурации | true           | false                  |
| KEYCLOAK_URL             | Адрес keycloak                      | localhost:8888 | keycloak:8080          |
| DISCOVERY_URL            | Адрес eureka                        | localhost:8761 | discovery-service:8761 |
| EUREKA_INSTANCE_HOSTNAME | Наименование сервиса для eureka     | localhost      | library-service        |
| ZIPKIN_URL               | Адрес zipkin                        | localhost:9411 | zipkin:9411            |