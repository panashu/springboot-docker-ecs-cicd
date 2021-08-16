# Calculate date difference between two dates

This service offers Rest API that takes in any date and returns the difference in number of days between the given date (the input) and the current date (Today’s
date).
Technical offerings : Spring Boot application as an AWS Lambda built with Cloud formation.


# Swagger for visualizing API

Relevant specifications for consuming the REST API with the expected output is documented using OpenAPI standards.
This can be accessed at http://localhost:8080/swagger-ui.html


# AWS Lambda

Build the code and deploy with AWS SAM.

mvn clean package

aws cloudformation package --template-file sam.yaml --output-template-file target/output-sam.yaml --s3-bucket lambda-cfn
 
aws cloudformation deploy --template-file target/output-sam.yaml --stack-name spring-boot-lambda --capabilities CAPABILITY_IAM
 
aws cloudformation describe-stacks --stack-name date-difference-service


# Run

To build and run from a packaged jar locally:

    mvn spring-boot:run

or 

    mvn clean package -Dboot
    java -jar target/date-difference-service-1.0.0-SNAPSHOT.jar

# Docker

To build the image. First build the application, then build the docker image

    mvn package -Dboot
    docker build -t date-difference-service .
    
## Run

    docker run --name date-difference-service -p 8080:8080 -d date-difference-service
    
# Test

    curl http://localhost:8080/api/datedifference?inputDate=<yyyy-mm-dd>
