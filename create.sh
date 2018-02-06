#!/bin/bash
PROJDIR="/home/ruzan/DS/PubSubSystem/PubSubSystem/src/edu/rit/CSCI652/"

files=`find -name "*.java"`
javac -d "out" -classpath lib/gson-2.6.2.jar $files
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
fi
