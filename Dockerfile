# Oбраз создаётся на основе alpine linux с установленной openjdk11
FROM adoptopenjdk/openjdk11:alpine-jre

# Развернуть приложение на порту 8080
#EXPOSE 8080

# Переменной JAR_FILE присваивается путь к jar- архиву
ARG JAR_FILE=target/anti-1.0.jar

# Назначаем рабочую директорию, в которой будут выполняться дальнейшие команды (перемещаемся в папку app)
WORKDIR /MyFirstContainer/app

# Наш jar-файл, указанный в JAR_FILE, копируется в папку app, и копии задаётся имя myapp.jar
COPY ${JAR_FILE} myapp.jar

# jar-файл запускается, собирается команда java -jar app.jar из заданной рабочей директории
ENTRYPOINT ["java","-jar","myapp.jar"]

#################################################INFO###################################################################
# После этого в терминале вводим команду, с помощью которой собираем образ и запускаем контейнер.
# docker build -t spring-docker-simple:0.0.1 .
# -t создает образ с именем ... - TEG :0.0.1
# Точка в конце важна, она указывает на расположение Dockerfile (символ «точка» означает текущую директорию.

# Проверьте, что образ создан командой docker images.

# Запускаем контейнер командой:
# docker run -d -p 8080:8080 -t spring-docker-simple:0.0.1
# Опция -d означает старт процесса в фоновом режиме. Опция -p тоже важна — дело в том,
# что контейнер собирается в полностью изолированном окружении.
# Тот факт, что приложение внутри контейнера запущено на порту 8080, не означает,
# что оно доступно вне контейнера на этом порту.
# Требуется явно указать, что порту 8080 в контейнере (здесь второе значение — это порт,
# на котором работает наше приложение в контейнере) соответствует порт 8080 на локальной машине,
# который будет использоваться при обращении к контейнеру. Поэтому пишем через двоеточие -p 8080:8080.

# Теперь введём в терминале команду:
# curl http://localhost:8080
# Так проверяется работоспособность запущенного контейнера.
# Есть и альтернативный вариант: можно просто перейти по этому адресу в браузере.
# Если всё работает как надо, задача выполнена — нам удалось упаковать Spring Boot приложение в контейнер.

# Частые команды при работе с Docker:
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
########################################################################################################################

# COPY . /docker/myapp
# RUN javac Main.java
# CMD ["java", "Main"]

#1 docker build -t anti-1.0 .
#2 docker images
#3 docker run -d -p 8080:8080 -t anti-1.0
#4 curl http://localhost:8080

# docker rmi $(docker images -q) - Удалить все образы
# docker rm $(docker ps -a -q) - Удалить все контейнеры
# docker system prune - Удалитьb все не связанные с контейнерами ресурсы, в том числе образы, контейнеры, тома и сети

# docker container  rm $(docker ps -a -f status=created -f status=exited -q) - Удалить все контейнеры

### 1. Останавливаем и удаляем контейнеры:
# docker stop $(docker ps -a -q)
# docker rm $(docker ps -a -q)
### 2. Удаляем образы:
# docker rmi $(docker images -a -q)
### 3. Удаляем тома:
# docker volume rm $(docker volume ls -a -q)
### 4. Удаляем сети:
# docker network rm $(docker network ls -a -q)
### 5. Удаляем volumes(тома)
# docker-compose down --volumes

### С отоброжением в консоль
# docker-compose up --build
### В фоновом режиме
# docker-compose up --build -d

# docker container ls -qa - List all containers by id
# docker container rm [id] - run each container
# docker volume ls - List all volumes by VolumeName
# docker volume rm [VolumeName] - run each volume

# java -jar AnTi-1.0.jar - запустить собраный jar (вне Docker)
# Найти идентификатор процесса командой jps и убить их командой taskkill -f /PID ....

# docker stop $(docker ps -a -q)
# docker rm $(docker ps -a -q)
# docker rmi $(docker images -a -q)
# docker volume rm $(docker volume ls -a -q)
# docker network rm $(docker network ls -a -q)

# docker-compose down --volumes
# docker system prune
# docker-compose up --build

# docker-compose config