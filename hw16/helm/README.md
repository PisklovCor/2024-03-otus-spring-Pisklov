### Работа с Helm Chart

### Директория домашней работы с манифестами k8s
```shell
cd hw16/helm
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

### Проверка HelmChart
```shell
helm list
````

### Установка HelmChart
```shell
helm install hw16 helmChart
````

### Удаление HelmChart
```shell
helm delete hw16
````

### Удаление всего окруженяи после завершения работы
```shell
minikube delete --all
```
