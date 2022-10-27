## Stack
```
Spring_
Spring Boot_
Spring Security_
Spring Validation_
Spring Jpa_
Slf4j_
Modelmapper_
Lombok_
Thymeleaf_
Bootstrap_
JS_
Liquibase_
Docker_
Swagger_
AOP_
API_
JUnit
```
## Настройка DockerFile
```
# Oбраз создаётся на основе alpine linux с установленной openjdk11
FROM adoptopenjdk/openjdk11:alpine-jre

# Переменной JAR_FILE присваивается путь к jar- архиву
ARG JAR_FILE=target/spring_boot_security-1.0.jar

# Назначаем рабочую директорию, в которой будут выполняться дальнейшие команды (перемещаемся в папку app)
WORKDIR /MyFirstContainer/app

# Наш jar-файл, указанный в JAR_FILE, копируется в папку app, и копии задаётся имя app.jar
COPY ${JAR_FILE} myapp.jar

# jar-файл запускается, собирается команда java -jar app.jar из заданной рабочей директории
ENTRYPOINT ["java","-jar","app.jar"]
```
### После этого в терминале вводим команду, с помощью которой собираем образ и запускаем контейнер.
```
docker build -t spring-docker-simple:0.0.1 .
```
```
-t создает образ с именем ... - TEG :0.0.1
```
### Точка в конце важна, она указывает на расположение Dockerfile (символ «точка» означает текущую директорию.

### Проверьте, что образ создан командой 
```
docker images.
```
### Запускаем контейнер командой:
```
docker run -d -p 8080:8080 -t spring-docker-simple:0.0.1
```
### Опция -d означает старт процесса в фоновом режиме. Опция -p тоже важна — дело в том,
### что контейнер собирается в полностью изолированном окружении.
### Тот факт, что приложение внутри контейнера запущено на порту 8080, не означает,
### что оно доступно вне контейнера на этом порту.
### Требуется явно указать, что порту 8080 в контейнере (здесь второе значение — это порт,
### на котором работает наше приложение в контейнере) соответствует порт 8080 на локальной машине,
### который будет использоваться при обращении к контейнеру. Поэтому пишем через двоеточие -p 8080:8080.

### Теперь введём в терминале команду:
```
# curl http://localhost:8080
```
### Так проверяется работоспособность запущенного контейнера.
### Есть и альтернативный вариант: можно просто перейти по этому адресу в браузере.
### Если всё работает как надо, задача выполнена — нам удалось упаковать Spring Boot приложение в контейнер.

### Частые команды при работе с Docker:
```
# docker ps — выводит список запущенных контейнеров.
# Также ей можно передать параметр -a, чтобы вывести все контейнеры, а не только запущенные.

# docker build — собирает образ Docker из Dockerfile и набора файлов, расположенных по определённому пути.
# docker build -t spring-docker:0.0.1 .
# Параметр -t используется, чтобы задать имя образа,
# последний параметр. — наименование каталога (в нашем случае текущий каталог).

# docker images — выводит список образов в вашей системе.

# docker logs — позволяет вывести на консоль логи указанного контейнера.
# Для этого необходимо указать имя или id контейнера. Можно использовать флаг --follow, чтобы следить
# за логами работающего контейнера: например, docker logs --follow c5ecc88de8f9.

# docker run — запускает контейнер на основе указанного образа.
# docker run spring-docker:0.0.1

# docker stop — останавливает контейнер.
# Можно передать опцию $(docker ps -a -q) для остановки всех запущенных контейнеров.

# docker rm и docker rmi — команды, удаляющие контейнер и образ соответственно.
# Удалить все контейнеры: docker rm $(docker ps -a -q)
```
```
# COPY . /docker/myapp
# RUN javac Main.java
# CMD ["java", "Main"]
```
```
# 1 docker build -t spring-docker:0.0.1 .
# 2 docker images
# 3 docker run -d -p 8080:8080 -t spring-docker:0.0.1
# 4 curl http://localhost:8080

# Запустить jar в командной строке и убить процесс
# java -jar AnTi-1.0.jar - запустить собраный jar (вне Docker)
# Найти идентификатор процесса командой jps и убить их командой taskkill -f /PID ....
```

## Настройка Docker-compose
```
# Версия Docker API
version: '3.1'
# Сервисы которые мы будем запускать
services:

# Первый сервис - db
db:
container_name: postgres
# Образ на основе которого он будет запускаться
image: postgres:15-alpine
restart: always
# volumes - магическая вещь, которая создает некоторое устройство в
# рамках Docker и монтирует его в директорию /var/lib/postgresql/data
volumes:
- postgres_data:/var/lib/postgresql/data/
# Переменные окружения
environment:
POSTGRES_USER: postgres
POSTGRES_PASSWORD: root
POSTGRES_DB: docker_app
# Говорим открыть снаружи порт 5432
expose:
- 5432

# Второй сервис - app
app:
# Говорим что его надо будет собрать - в качестве контекста
# передаем текущую директорию - в ней лежит Dockerfile
build: ./
# Монтируем локальную директорию ./src в директорию
# внутри контейнера /opt/app
volumes:
- ./src:/opt/app
# Говорим пробросить порт 8000 хоста в порт 8000 контейнера
ports:
- 8000:8000
# Зависит от сервиса db - запускать после него
depends_on:
- db
# Просто говорим создать volume с именем postgres_data
#    volumes:
#    postgres_data:

adminer:
container_name: adminer
image: adminer
restart: always
ports:
- "1010:8080"
environment:
- "POSTGRES_DB=localhost:5555/docker_test_app"
- "POSTGRES_USER=postgres"
- "POSTGRES_PASSWORD=root"
```

### После этого в консоли выполним вот эту команду:
```
docker-compose up --build -d
```
### Тут мы говорим: up – поднять, --build – собрать, -d – пусть робит в фоне.
### После чего мы можем посмотреть список запущенных сервисов:
```  
docker-compose ps
```
### Важно: Все команды docker-compose должны выполняться в той же директории,
### где расположен файл docker-compose.yml. В противном случае необходимо его указывать
### явно через флаг -f и путь до файла docker-compose.yml.

### К слову у вас может быть несколько файлов docker-compose.yml и их можно включать все например вот такой конструкцией:
```
docker-compose -f docker-compose.yml -f docker-compose.admin.yml run -it backup_db
```
### Кейс: в файле docker-compose.admin.yml может быть больше доступа.
### Или для среды разработки можно поднимать моки вместо реальных сервисов.

### Так же замечу, что все сервисы описанные в рамках одного docker-compose.yml файла
### (в нашем примере это сервисы db и app), будут сразу “из коробки” видеть друг друга по указанным именам.

### Ну а теперь откройте в браузере http://127.0.0.1:8000/swagger/ и убедитесь, что приложение поднялось и заработало
### – откроется Swagger-документация по API нашего приложения. По крайней мере так может казаться на первый взгляд.
```
# 1 docker build -t spring-docker:0.0.1 .
# 2 docker images
# 3 docker run -d -p 8080:8080 -t spring-docker:0.0.1 // java -jar spring-docker-1.0.jar - запустить собраный jar (вне Docker)
# 4 curl http://localhost:8080
```
### EUREKA
```
spring.application.name=Ia sam give name client
#server.port=${PORT:8762}

###################################                  INSTANCE                  #########################################
Свойство особенно важно, поскольку мы запускаем его на локальном компьютере
eureka.instance.hostname=my-eureka-server.com
указываем url по которому клиент будет искать свой сервер, обязательно указываем свойство
eureka.instance.prefer-ip-address=true
свойство, указывает интервал эхо запроса, который клиент отправляет на сервер (значение по умолчанию - 30 секунд)
eureka.instance.lease-renewal-interval-in-seconds=30
Если Eureka Server не видел обновления в течение 90 секунд, он удаляет экземпляр из своего реестра
eureka.instance.lease-expiration-duration-in-seconds=100

###################################                  CLIENT                  ###########################################
если параметр shouldUseDns имеет значение false, мы будем использовать следующие свойства для явного указания маршрута к серверам eureka
eureka.client.service-url.defaultZone=http://${eureka.instance.hostname}:${server.port}/eureka/
мы не хотим чтобы клиент получал информацию реестра от Eureka Server
eureka.client.fetch-registry=false
можем запретить Eureka Client регистрироваться в качестве экземпляра
eureka.client.register-with-eureka=false
указать время ожидания (в секундах) до истечения тайм-аута соединения с Eureka Server
eureka.client.eureka-server-connect-timeout-seconds=5
указать время ожидания (в секундах) до истечения тайм-аута чтения с Eureka Server
eureka.client.eureka-server-read-timeout-seconds=5
указать общее количество подключений, разрешенных от клиента ко всем серверам
eureka.client.eureka-server-total-connections=200
указать общее количество подключений, разрешенных от клиента к определенному серверу
eureka.client.eureka-server-total-connections-per-host=50
как часто (в секундах) необходимо запрашивать изменения об информации с сервера
eureka.client.eureka-service-url-poll-interval-seconds=1

###################################                  SERVER                  ###########################################
По умолчанию Eureka Client используют Jersey и Jackson вместе с JSON для связи с Eureka Server
указать свое время, по которому сервер будет ожидать эхо запрос от клиента о том что он жив (по умолчанию 30 секунд)
eureka.server.expected-client-Renewal-interval-seconds=100
Если вы хотите использовать поиск на основе DNS для определения других серверов eureka
eureka.shouldUseDns= true
Если у вас на клиенте есть собственная клиентская конфигурация, то на стороне сервера eureka установите для обновления по требованию значение в false
eureka.shouldOnDemandUpdateStatusChange= false

Eureka Server использует протокол, который требует, чтобы клиенты выполняли явное действие отмены регистрации
DiscoveryManager.getInstance().shutdownComponent()
```