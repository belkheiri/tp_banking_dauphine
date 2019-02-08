#!/bin/bash

SERVICE_NAME=$1
echo ${SERVICE_NAME}


mvn clean install -pl ${SERVICE_NAME} -am && mvn spring-boot:run -pl ${SERVICE_NAME}