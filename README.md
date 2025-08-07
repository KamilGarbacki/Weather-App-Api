# Weather App Api
## Project Description
This project provides a simple REST API with two endpoints that deliver weather forecasts and estimate solar energy production based on geographic coordinates. The service integrates with the external weather data provider Open-Meteo.

It is designed for use in a proof-of-concept photovoltaic system forecasting tool, delivering weather insights and potential solar energy generation over a 7-day period.

## Documentation
>http://localhost:8080/swagger-ui/index.html#/

## Build With
- Java 21
- Spring Boot Framework
- Docker

## Docker Image
The project is avilable as a Docker Repository which can be accessed with
```bash
docker pull kamilgarbacki/weather-app-api
```

## Build the image yourself
You can build the image with the following command. Be sure to edit the dockerhub username in the pom file first.
```bash
mvn package
```

## Project Setup
### 1.Clone the Repository
```bash
git clone https://github.com/KamilGarbacki/Weather-App-Api
cd Weather-App-Api
```

### 2. Build The Project
```sh
mvn clean install
```

## Testing The Application
To run the tests:
```bash
mvn verify
```