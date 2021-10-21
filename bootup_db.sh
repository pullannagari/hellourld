#TODO fix the docker compose file to have both app and db in a single file
# change directory to docker
cd docker
# build with no cache
docker-compose build --no-cache
# start the service
docker-compose up --remove-orphans
# list the services
docker-compose ps
# list the containers
docker ps
# stop services
docker-compose stop