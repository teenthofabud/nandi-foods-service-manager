docker rm -vf nandi-foods-service-manager

docker rmi -f teenthofabud/nandi-foods-service-manager:0.0.3-SNAPSHOT

mvn clean package -e

docker build -t teenthofabud/nandi-foods-service-manager:0.0.3-SNAPSHOT -f Dockerfile .

docker run -p 8081:8081 -d -e PROFILE=default,staging --name nandi-foods-service-manager teenthofabud/nandi-foods-service-manager:0.0.3-SNAPSHOT