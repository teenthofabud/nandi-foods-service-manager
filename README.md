# Read Me First

## System requirements

JDK 21

## Sample Unit PATCH request

```
[
    {"op":"replace","path":"/level","value":"Level 2"}, 
    {"op":"replace","path":"/longName","value": "this is a long name"},
    {"op":"replace","path":"/metric/lengthValue","value": 2.9},
    {"op":"replace","path":"/isInventory","value": true}
]
```

## Build

### Native

```
mvn clean package -e

sh ci/run.sh
```

### Locally

```
mvn clean package -e

docker build -t teenthofabud/nandi-foods-service-manager:0.0.3-SNAPSHOT -f Dockerfile .

docker run -p 8081:8081 -d -e PROFILE=default,development --name nandi-foods-service-manager teenthofabud/nandi-foods-service-manager:0.0.3-SNAPSHOT
```

### Staging

```sh
mvn clean package -e

docker build -t teenthofabud/nandi-foods-service-manager:0.0.3-SNAPSHOT -f Dockerfile .

docker run -p 8081:8081 -d -e PROFILE=default,staging --name nandi-foods-service-manager teenthofabud/nandi-foods-service-manager:0.0.3-SNAPSHOT

docker image push teenthofabud/nandi-foods-service-manager:0.0.3-SNAPSHOT

docker pull docker.io/teenthofabud/nandi-foods-service-manager:0.0.3-SNAPSHOT
```
