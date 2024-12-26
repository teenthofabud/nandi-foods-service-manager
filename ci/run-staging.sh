docker rm -vf nandi-foods-service-manager

docker rmi -f teenthofabud/nandi-foods-service-manager:0.0.4-SNAPSHOT

mvn clean package -e

docker build -t teenthofabud/nandi-foods-service-manager:0.0.4-SNAPSHOT -f Dockerfile .

docker run -p 8080:8080 -d -e PROFILE=default,staging --name nandi-foods-service-manager teenthofabud/nandi-foods-service-manager:0.0.4-SNAPSHOT

docker image push teenthofabud/nandi-foods-service-manager:0.0.4-SNAPSHOT

docker pull docker.io/teenthofabud/nandi-foods-service-manager:0.0.4-SNAPSHOT