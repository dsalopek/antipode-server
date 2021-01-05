echo "%%% Building Project %%%"
mvn clean install
echo "%%% Bringing Docker Compose Down %%%"
cd .docker
docker-compose down
echo "%%% Building Docker Compose %%%"
docker-compose build
echo "%%% Bringing Docker Compose Up %%%"
docker-compose --env-file .env up --remove-orphans