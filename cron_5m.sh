#!/bin/bash
cd /home/maven/prospherodataquality
git pull origin master
mvn clean
mvn -Dtest=RealTime5mTest test