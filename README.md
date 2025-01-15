# Read Me First

## System requirements

JDK 21

## Sample Unit PATCH request

```
[
    {"op":"replace","path":"/level","value":"Level 2"}, 
    {"op":"replace","path":"/longName","value": "this is a long name"},
    {"op":"replace","path":"/metric/0/lengthValue","value": 2.9},
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

```
{
  "level": "Level 3",
  "description": "1 X 4LB",
  "longName": "U1003 CARTON (1 X 4LB)",
  "shortName": " CARTON (U1003)",
  "status": "ACTIVE",
  "effectiveDate": "2025-01-21",
  "measuredValues": [
    {
      "measurementSystem": "SI",
      "id": 5,
      "length": 3.4,
      "width": 3.4,
      "height": 3.4,
      "volume": 3.4,
      "weight": 3.4
    },
    {
      "measurementSystem": "IMPERIAL",
      "id": 6,
      "length": 600,
      "width": 600,
      "height": 600,
      "volume": 600,
      "weight": 600
    }
  ],
  "isInventory": true,
  "isPurchase": true,
  "isSales": true,
  "isProduction": true,
  "linkedUOMs": [
    {
      "quantity": 1,
      "id": "U1001"
    }
  ],
  "name": "CARTON"
}
```

### Staging

```sh
mvn clean package -e

docker build -t teenthofabud/nandi-foods-service-manager:0.0.3-SNAPSHOT -f Dockerfile .

docker run -p 8081:8081 -d -e PROFILE=default,staging --name nandi-foods-service-manager teenthofabud/nandi-foods-service-manager:0.0.3-SNAPSHOT

docker image push teenthofabud/nandi-foods-service-manager:0.0.3-SNAPSHOT

docker pull docker.io/teenthofabud/nandi-foods-service-manager:0.0.3-SNAPSHOT
```
