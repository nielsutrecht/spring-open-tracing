#!/bin/bash

mvn clean
mvn -Dbar package
mvn -Dfoo package