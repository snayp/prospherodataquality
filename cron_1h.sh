#!/bin/bash
cd /home/maven/prospherodataquality
git pull origin master
mvn clean
mvn test -Dtest=HourlyFieldsTest