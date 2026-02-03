1\.  mvn spring-boot:build-image "-Dspring-boot.build-image.imageName=princejain1198/ecom-application"

2\.  docker push princejain1198/ecom-application

3\.  docker run -d -p 8080:8080 princejain1198/ecom-application



# Docker commands

2\.  docker pull <image>

3\.  docker push <username/image>

4\.  docker run -it -d -p <host-port>:<container-port> --name <name> <image>

5\.  docker stop <container-id/container-name>

6\.  docker start  <container-id/container-name>

7\.  docker rm <container-id/container-name>

8\.  docker rmi <image-id/image-name>

9\.  docker ps

10\. docker ps -a

11\. docker images

12\. docker exec -it <container-name/container-id> bash

13\. docker build -t <username/image> .

14\. docker logs <container-name/container-id>

15\. docker inspect <container-name/container-id>

16\. docker system prune -a --volumes --force

17\. docker network prune --force

18\. docker rm -f $(docker ps-q)

19\. docker exec config-server ls /config

20\. docker compose up --build -d



# Git Commands

## create a new repository on the command line

1\. echo "# ecom-microservices" >> README.md

2\. git init

3\. git add README.md

4\. git commit -m "first commit"

5\. git branch -M main

6\. git remote add origin https://github.com/princejains1198/ecom-microservices.git

7\. git push -u origin main



## push an existing repository from the command line

1. git remote add origin https://github.com/princejains1198/ecom-microservices.git

2\. git branch -M main

3\. git push -u origin main



### command to run postgres in docker

1. docker run -d --name db -e POSTGRES\_PASSWORD=mysecretpassword postgres:14
2. docker run -d --name pgadmin -e PGADMIN\_DEFAULT\_EMAIL=user@domain.com -e PGADMIN\_DEFAULT\_PASSWORD=SuperSecret dpage/pgadmin4
3. docker exec -it pgadmin ping db
4. docker network create my-network
5. docker rm -f db pgadmin (to delete the db and pgadmin container)
6. docker network prune (this will remove all the existing network from the docker)
7. docker run -d --name db --network my-network -e POSTGRES\_PASSWORD=mysecretpassword postgres:14
8. docker run -d --name pgadmin --network my-network -e PGADMIN\_DEFAULT\_EMAIL=user@domain.com -e PGADMIN\_DEFAULT\_PASSWORD=SuperSecret dpage/pgadmin4
9. docker exec -it pgadmin ping db





### Create a Docker network:

1. docker network create postgres



#### Start the PostgreSQL service:

1. docker run -d \\

    --name postgres\_container \\

    -e POSTGRES\_USER=root \\

    -e POSTGRES\_PASSWORD=root \\

    -e PGDATA=/data/postgres \\

    -v postgres:/data/postgres \\

    -p 5432:5432 \\

    --network postgres \\

    --restart unless-stopped \\

    postgres:14



#### Start the pgAdmin service:

1. docker run -d \\

    --name pgadmin\_container \\

    -e PGADMIN\_DEFAULT\_EMAIL=pgadmin4@pgadmin.org \\

    -e PGADMIN\_DEFAULT\_PASSWORD=admin \\

    -e PGADMIN\_CONFIG\_SERVER\_MODE=False \\

    -v pgadmin:/var/lib/pgadmin \\

    -p 5050:80 \\

    --network postgres \\

    --restart unless-stopped

    dpage/pgadmin4





### Docker Compose

services:

 postgres:

  container\_name: postgres\_container

  image: postgres:14

  environment:

   POSTGRES\_USER: root

   POSTGRES\_PASSWORD: root

   PGDATA: /data/postgres

  volumes:

   - postgres:/data/postgres

  ports:

   - "5432:5432"

  networks:

   - postgres

  restart: unless-stopped

 pgadmin:

  container\_name: pgadmin\_container

  image: dpage/pgadmin4

  environment:

   PGADMIN\_DEFAULT\_EMAIL: ${PGADMIN\_DEFAULT\_EMAIL:-pgadmin4@pgadmin.org}

   PGADMIN\_DEFAULT\_PASSWORD: ${PGADMIN\_DEFAULT\_PASSWORD:-admin}

   PGADMIN\_CONFIG\_SERVER\_MODE: 'False'

  volumes:

   - pgadmin:/var/lib/pgadmin

  ports:

   - "5050:80"

  networks:

   - postgres

  restart: unless-stopped



networks:

 postgres:

  driver: bridge



volumes:

 postgres:

 pgadmin:





### Docker Compose commands

1. docker compose up -d
2. docker compose down
3. docker logs 525c12c8042d(container id which we will get after running docker ps command)



## Maven Commands

1. mvn clean package -DskipTests





### Important Command for IntelliJ VM Option:

1. -Duser.timezone=Asia/Kolkata



### To Start/Stop MongoDB service from the terminal

1\. net Stop MongoDB

2\. net Start MongoDB

3\. mongosh

4\. show dbs



### Mongo Db Username Password

Username: princejain7987\_db\_user

Password: PnuHRv4SdLqUEXci







## JVM

1. java -Dbuild.id=123452 -Dbuild.name=java-testing -Dbuild.type=anything -jar target\\configdemo-0.0.1-SNAPSHOT.jar (JVM argument)
2. java -jar target\\configdemo-0.0.1-SNAPSHOT.jar --build.id=1234523 --build.name=java-testing --build.type=anything (program argument)
3. java -jar target\\configdemo-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev
4. java -Dspring.profiles.active=prod -jar target\\configdemo-0.0.1-SNAPSHOT.jar



GIT\_USERNAME: Prince Jain

GIT\_PASSWORD: github\_pat\_11ASIZDYA04e9situiWonb\_QScQc6gqdK7Wxt4VWpVQzxoJMgSa8OIeLERo8kokV2STIMFJFQJHCk4chcm



Environment Variable for Configserver

GIT\_TOKEN=github\_pat\_11ASIZDYA04e9situiWonb\_QScQc6gqdK7Wxt4VWpVQzxoJMgSa8OIeLERo8kokV2STIMFJFQJHCk4chcm;GIT\_USERNAME=Prince Jain;

MYSQL\_USER=admin;MYSQL\_PASSWORD=admin



# Command for generating a Java key pair using keytool:

1. PS C:\\Users\\princ\\.jdks\\ms-17.0.16\\bin> .\\keytool.exe -genkeypair -alias config-server-key -keyalg RSA -dname "CN=Config Server,OU=Spring Cloud,O=Company" -keypass mypass -keystore D://config-server.jks -storepass mypass



## What this does:

\- Generates a key pair using RSA algorithm.

\- Alias: ***config-server-key*** — used to reference the key in the keystore.

\- Distinguished Name (DN): ***"CN=Config Server,OU=Spring Cloud,O=Company"*** — identifies the entity.

\- Keystore file: ***config-server.jks*** — where the key pair is stored.

\- Passwords: ***mypass*** for both key and keystore.





# RABBIT MQ

1. docker run -d -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:4-management

2\. https://api.cloudamqp.com/console/44135c2b-688a-444a-9e4a-c3b5e721cb6b/details

3\. https://customer.cloudamqp.com/instance



# REDIS

docker run -d --name redis -p 6379:6379 redis:latest



# ZIPKIN

docker run -d -p 9411:9411 openzipkin/zipkin



# ZOOKEPER AND KAFKA INSTALLATION COMMANDS

1. docker run -d --name zookeeper -p 2181:2181 -e ZOOKEEPER\_CLIENT\_PORT=2181 -e ZOOKEEPER\_TICK\_TIME=2000 confluentinc/cp-zookeeper:7.5.0
2. docker run -d --name kafka -p 9092:9092 -e KAFKA\_BROKER\_ID=1 -e KAFKA\_ZOOKEEPER\_CONNECT=zookeeper:2181 -e KAFKA\_ADVERTISED\_LISTENERS=PLAINTEXT://localhost:9092 -e KAFKA\_OFFSETS\_TOPIC\_REPLICATION\_FACTOR=1 --link zookeeper confluentinc/cp-kafka:7.5.0
3. docker exec -it kafka bash
4. kafka-topics --create --topic my-topic --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1
5. kafka-topics --list --bootstrap-server localhost:9092
6. kafka-topics --create --topic new-topic --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1
7. kafka-topics --list --bootstrap-server localhost:9092
8. kafka-topics --describe --topic my-topic --bootstrap-server localhost:9092
9. kafka-topics --describe --topic new-topic --bootstrap-server localhost:9092
10. kafka-console-producer --topic my-topic --bootstrap-server localhost:9092
11. kafka-console-consumer --topic my-topic --bootstrap-server localhost:9092 --from-beginning
12. kafka-console-consumer --topic my-topic --bootstrap-server localhost:9092 --group my-group --from-beginning
13. kafka-console-producer --topic my-topic --bootsp-server localhost:9092 --property "parse.key=true" --property "key.separator=:"
14. kafka-consumer-groups --bootstrap-server localhost:9092 --describe --group my-group
15. kafka-console-consumer --topic my-topic --bootstrap-server localhost:9092 --group my-group --from-beginning --property print.partition=true





# KEYCLOAK

1. docker run -d -p 8443:8080 -e KC\_BOOTSTRAP\_ADMIN\_USERNAME=admin -e KC\_BOOTSTRAP\_ADMIN\_PASSWORD=admin quay.io/keycloak/keycloak:26.4.7 start-dev





## BUILD DOCKER IMAGE

1. docker build -t config-server ./configserver
2. docker run --env-file configserver/.env -d --name config-server -p 8888:8888 config-server



## DOCKER FILE

FROM eclipse-temurin:17-jdk-ubi9-minimal

WORKDIR /app

COPY target/\*.jar app.jar

EXPOSE 8888

ENTRYPOINT \["java", "-jar", "app.jar"]



# BUILD PACKS



###  		    Build image with mention of image name

1. mvn spring-boot:build-image -DskipTests "-Dspring-boot.build-image.imageName=dcode007/ecom-application"

 



####  		      Build image without mention of image name

2. mvn spring-boot:build-image -DskipTests



####  		    	           Run images

3. docker run -d -p 8080:8080 <image-name>







## FOR JIB

1\. mvn compile jib:dockerBuild  (build your app and creates a Docker image  locally \[in our docker deamon])

2\. mvn compile jib:build (build your app and pushes the image directly to a remote registry (like docker hub or GCR). Does not require Docker installed ) 

3\. mvn compile jib:buildTar (Creates a .tar file of your image that you can load into Docker manually)

4\. mvn clean compile jib:dockerBuild 

3\. docker run <image-name>

4\. docker image push docker.io/princejain1198/accounts:s4 (command to push image into docker hub)

5\. docker image pull docker.io/princejain1198/accounts:s4 (command to pull image into docker hub)





# GIT

## …or create a new repository on the command line

echo "# ecom-microservices" >> README.md

git init

git add README.md

git commit -m "first commit"

git branch -M main

git remote add origin https://github.com/princejains1198/ecom-microservices.git

git push -u origin main



## …or push an existing repository from the command line

git remote add origin https://github.com/princejains1198/ecom-microservices.git

git branch -M main

git push -u origin main







 

