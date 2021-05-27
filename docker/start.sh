#!/bin/sh
#Decode the passed in truststore env variable
mkdir -p src/main/resources
echo "$CREDENTIALS_JSON_ENCODED" | base64 -d > $CREDENTIALS_JSON
java -jar analytics-service.jar