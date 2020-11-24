echo "%%% Building Project %%%"
mvn clean install -DskipTests
echo "%%% Bringing Docker Compose Down %%%"
docker-compose down
echo "%%% Building Docker Compose %%%"
docker-compose build
echo "%%% Bringing Docker Compose Up %%%"
docker-compose --env-file .env up --remove-orphans