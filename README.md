## PLAYER MARKET [![Build Status](https://www.travis-ci.com/degath/player-market.svg?branch=develop)](https://www.travis-ci.com/degath/player-market)

- Task description can be found here: [task description](TASK_DESCRIPTION.md)

### Steps to run locally with docker
1. Fill application properties (especially `currency.api.key=`)
2. `docker-compose up`
3. Hit [localhost:8080](http://localhost:8080) for API info on Swagger

### Steps to run locally without docker
##### with default profile (in memory database)
1. Fill application properties (especially `currency.api.key=`)
. `.\gradlew clean bootRun`
2. Hit [localhost:8080](http://localhost:8080) for API info on Swagger

##### with custom profile `postgres` (postgres database)
1. Fill application properties (especially `currency.api.key=`)
2. Run your local postgres & fill application properties (connection string, user & password inside `application-postgres.properties`
3. `.\gradlew clean bootRun --args='--spring.profiles.active=postgres'`
4. Hit [localhost:8080](http://localhost:8080) for API info on Swagger


#### To run build and tests (unit & integration)
1. `.\gradlew clean build`

