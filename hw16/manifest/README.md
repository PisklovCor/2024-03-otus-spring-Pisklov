## Манифесты описывают сущности: Deployment, Service, Ingress, Secret, Job

Приложение отвечает по хосту [arch.homework](http://arch.homework)

### Директория домашней работы с манифестами k8s
```shell
cd hw16/manifest
```

### Команды взаимодействия (minikube, kubectl), подготовка окружения:
## Запуск образа minikube
```shell
minikube start
```

## Запуск встроенного ingress (тк дефолтно он отключен)
```shell
minikube addons enable ingress
```

## ## Запуск тунеля из minikube на хост arch.homework (необходима новая вкладка!!!)
```
minikube tunnel
```

## Запуск dashboard (необходима новая вкладка!!!)
```
minikube dashboard
```

### Применить манифесты одной командой kubectl
```shell
kubectl apply -f .
```

### Удаление всего окруженяи после завершения работы
```shell
minikube delete --all
```