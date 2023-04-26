#!/bin/bash
./gradlew build
PROJECT_NAME=carrot-server
docker build -t ${PROJECT_NAME} .
docker run -p 80:8080 ${PROJECT_NAME}
