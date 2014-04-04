#!/bin/bash

mvn clean
mvn install
mvn exec:java
mvn clean
