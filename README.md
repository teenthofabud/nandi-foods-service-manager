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