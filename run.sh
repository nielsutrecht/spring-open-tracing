#!/bin/bash

java -jar -Dspring.profiles.active=foo target/foo/foo-service.jar & pid1="$!" ; java -jar -Dspring.profiles.active=bar target/bar/bar-service.jar ; kill $pid1