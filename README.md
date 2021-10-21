# hellourld
url shortener which takes in long url's and produces corresponding short url's

##swagger or api docs
http://localhost:8081/v2/api-docs

##setup/install and run
### prerequisites 
    docker, Java 8, docker-compose 1.29, maven 3.6
1. clone this repo
2. run the boot_db.sh shell script, wait till the db is up
3. run the boot_app.sh shell script

##technologies and architecture
Used Mongo as the NoSql DB and Base62 encoding is used to generate the short urls


