#!/bin/sh

# Check if autosurvey-network network exists
if [ -z "$(docker network ls -q -f name=autosurvey-network)" ]; then
    docker network create autosurvey-network
fi

# rm analytics-service container if it exists
if [ -n "$(docker container ls -aqf name=analytics-service)" ]; then
    echo "Removing analytics-service"
    docker container stop analytics-service
    docker container rm analytics-service
fi

#start analytics-service container
docker container run -d --name analytics-service --network autosurvey-network -e EUREKA_URL -e CREDENTIALS_JSON -e CREDENTIALS_JSON_ENCODED -e FIREBASE_API_KEY -e SERVICE_ACCOUNT_ID autosurvey/analytics-service