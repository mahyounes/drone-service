# Drone service

Drone service is spring boot project that introduce api to get info about drones used for medications delivery.

It use H2 database (in memory DB), so when the application restart all the data are lost.

Once the service is running, there will be one drone and medication created by the code for testing purposes

## Installation

Use maven to build and run unit tests for drone service.

Go to the project folder then run:

```bash
mvn clean install
```
This will build the service and introduce traget folder that has the app jar and unit test coverage result.

## Running

To run the service:

```bash
mvn spring-boot:run
```

## Run test only

```bash
mvn test
```

## Accessing apis

The service will be running in port 8080 and the swagger url: [local swagger](http://localhost:8080/drone-service/swagger-ui.html)

It has 5 apis:

1- Register drone End-Point (to register new drone) path = /drone and http method type is post

2- Load drone End-Point (to load an existing drone with package of medications) path=/drone/{droneSerialNumber} and http method type is patch

3- Retrieve Drone End-Point (to retrieve a drone with its packages and battery history) path= /drone/{droneSerialNumber} and http method type is get

4- List available Drones End-Point (to list all drones available for loading (in idle state)) path=/drone/available and http method type is get

5- Create medication End-Point (to create new medication) path=/medication and http method type is post
