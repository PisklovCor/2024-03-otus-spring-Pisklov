### API Gateway. Используется как точка входа и маршрутиризатор запросов от клиента

#### Локальная сборка проекта:
```
mvn clean package
```
* Для корректной работы необходимо запустить (docker-compose.yaml) keycloak и discovery-service

### Внешнии параметры:
| Название                 | описание                            | local          | docker                 |
|--------------------------|-------------------------------------|----------------|------------------------|
| CLOUD_CONFIG_ENABLE      | Флаг включения внешней конфигурации | false          | false                  |
| KEYCLOAK_URL             | Адрес keycloak                      | localhost:8888 | keycloak:8080          |
| DISCOVERY_URL            | Адрес eureka                        | localhost:8761 | discovery-service:8761 |
| ZIPKIN_URL               | Адрес zipkin                        | localhost:9411 | zipkin:9411            |
