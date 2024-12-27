export ALL_VERSIONS=$(echo $(awk -F '[<>]' '/version/{print $3}' pom.xml))

export VERSION=$(echo $ALL_VERSIONS | cut -d' ' -f2)

docker rm -vf nandi-foods-service-manager

docker rmi -f teenthofabud/nandi-foods-service-manager:${VERSION}

mvn clean package -e

docker build -t teenthofabud/nandi-foods-service-manager:${VERSION} -f Dockerfile .

docker run -p 8080:8080 -d -e PROFILE=default,staging --name nandi-foods-service-manager teenthofabud/nandi-foods-service-manager:${VERSION}

docker image push teenthofabud/nandi-foods-service-manager:${VERSION}

docker pull docker.io/teenthofabud/nandi-foods-service-manager:${VERSION}

git pull

git tag ${VERSION} -a -m "Release version ${VERSION}"

git push origin ${VERSION}