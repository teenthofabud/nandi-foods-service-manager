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

```
mvn clean package -e
```

## Run locally

```
sh ci/run.sh
```

## Docker

### Locally

```
docker build -t teenthofabud/nandi-foods-service-manager:0.0.3-SNAPSHOT -f Dockerfile .
```

```
docker run -p 8081:8081 -d --name nandi-foods-service-manager teenthofabud/nandi-foods-service-manager:0.0.3-SNAPSHOT
```
