docker rm -vf nandi-foods-service-manager

docker rmi -f teenthofabud/nandi-foods-service-manager:0.1.4-RELEASE

mvn clean package -e

docker build -t teenthofabud/nandi-foods-service-manager:0.1.4-RELEASE -f Dockerfile .

docker run -p 8080:8080 -d -e PROFILE=default,staging --name nandi-foods-service-manager teenthofabud/nandi-foods-service-manager:0.1.4-RELEASE

docker image push teenthofabud/nandi-foods-service-manager:0.1.4-RELEASE

docker pull docker.io/teenthofabud/nandi-foods-service-manager:0.1.4-RELEASE