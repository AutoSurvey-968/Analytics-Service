# AutoSurvey-Analytics-Service

## Project Description

Analytics Service for AutoSurvey. Allows survey responses to be generalized by Batch and Week.

## Technologies Used

* Java - SE1.8
* Java Spring
* SonarCloud
* Swagger
* Eureka
* Karate
* JUnit
* Jacoco

## Features

* Able to provide an average of aggregate survey response data.
* Able to provide percent frequency of responses by question.
* Calculates batch delta organized by weekly results.
* Calculates aggregate weekly delta trends between batches.

Future Feature Opportunities:
* Caliber2 integration with weekly skill scores.
* Full SQS Integration.
* Refactor to not have to make a request to the survey service when creating a report.

## Getting Started

**See [Primary README.md](https://github.com/AutoSurvey-968/AutoSurvey-back) for full program setup instructions.**

Set environment variables:
* GATEWAY-URL - 
* CREDENTIALS_JSON - name of credentials json file to be placed in src/main/resources
* EUREKA_URL - default URL for eureka host
* FIREBASE_API_KEY - Firebase API key for authentication calls
* SERVICE_ACCOUNT_ID - Firebase service account id

## Usage

```
{base-url}/
```

### GET:
**Authorization level**: ADMIN-ONLY

Gets organized responses aggregated by week / batch

## Contributors

- [Austin Withers](https://github.com/AustinWithers) - Primary
- [Kevin Rose](https://github.com/Kevinrose235)
- [Michael Chan](https://github.com/chanmic)

