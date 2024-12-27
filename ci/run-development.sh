export ALL_VERSIONS=$(echo $(awk -F '[<>]' '/version/{print $3}' pom.xml))

export VERSION=$(echo $ALL_VERSIONS | cut -d' ' -f2)

#docker rm -vf $(docker ps -aq)
docker rm -vf nandi-foods-service-manager

#docker rmi -f $(docker images -aq)
docker rmi -f teenthofabud/nandi-foods-service-manager:${VERSION}

mvn clean package -e

docker build -t teenthofabud/nandi-foods-service-manager:${VERSION} -f Dockerfile .
#docker build --build-arg PROFILE=default,development -t teenthofabud/nandi-foods-service-manager:0.0.3-SNAPSHOT -f Dockerfile .

#docker run -p 8081:8081 -d --name nandi-foods-service-manager teenthofabud/nandi-foods-service-manager:0.0.3-SNAPSHOT
docker run -p 8081:8081 -d -e PROFILE=default,development --name nandi-foods-service-manager teenthofabud/nandi-foods-service-manager:${VERSION}