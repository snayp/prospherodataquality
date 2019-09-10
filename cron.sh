#!/bin/bash
cd /home/maven/prospherodataquality
git pull origin master
mvn clean test
mvn allure:report
