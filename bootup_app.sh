# build application
mvn clean install
# change directory to docker
cd docker
# start the application
java -jar hellourld-0.0.1-SNAPSHOT.jar com.cloudflare.hellourld.HellourldApplication