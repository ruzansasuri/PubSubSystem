#!/bin/bash

PROJDIR="/home/ruzan/DS/PubSubSystem/PubSubSystem/src/edu/rit/CSCI652/"

files=`find . -name "*.java"`
javac -source 1.8 -target 1.8 -d "out" -cp "lib/gson-2.6.2.jar:lib/sqlite-jdbc-3.21.0.jar" $files
if [ $? -eq 0 ]
then
	echo Compiled..
	docker stop $(docker ps -aq)
	docker rm $(docker ps -aq)
	docker rmi $(docker images -f "dangling=true" -q)
	docker image rm pubsub_sub
	docker image rm pubsub_em
	docker image build -t pubsub_em --build-arg SYSTEM_TYPE=EventManager .
	docker image build -t pubsub_sub --build-arg SYSTEM_TYPE=PubSubAgent .
else
	exit 1
fi
