#!/bin/bash
for i in `seq 1 10`;
do
    curl http://localhost:8080/recursive/$i|jq
done