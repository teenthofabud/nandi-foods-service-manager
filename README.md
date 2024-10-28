# Read Me First

## System requirements

JDK 21

## Sample Unit PATCH request

```
[
    {"op":"replace","path":"/level","value":"+1-555-56"}, 
    {"op":"replace","path":"/code","value": "u9909"},
    {"op":"replace","path":"/longName","value": "this is a long name"},
    {"op":"replace","path":"/metric/lengthValue","value": 2.9},
    {"op":"replace","path":"/bulkCode","value": 2},
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