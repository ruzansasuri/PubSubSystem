#!/bin/bash
javac -source 1.7 -target 1.7 project/src/*.java
docker system prune | y
docker image rm pubsub
docker image build -t pubsub .