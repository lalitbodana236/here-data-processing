# here-data-processing-Library

## How to Build and Run Tests

## Build the project
mvn clean install

## Run tests
mvn test

---

## Key Design Decisions

* Used Stream API so we don’t load all data into memory
* Used ConcurrentHashMap to handle multi-threading safely
* Used a Set to remove duplicate events

---

## Assumptions

* Events with negative or NaN values are ignored
* Duplicate means same id and timestamp
* Input data is finite
