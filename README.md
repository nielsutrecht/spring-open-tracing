# Spring Open Tracing example

Spring / Open tracing example project

## Build

    mvn clean
    mvn -Dbar package
    mvn -Dfoo package

## Run

    ./run.sh
    
or:

    java -jar -Dspring.profiles.active=foo target/foo/foo-service.jar
    java -jar -Dspring.profiles.active=bar target/bar/bar-service.jar

## Todo

* JDBC tracing
* Cassandra tracing
* Async tracing
