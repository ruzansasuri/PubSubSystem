#!/bin/bash

files=`find . -name "*.java"`
javac -source 1.8 -target 1.8 -d "out" -cp "lib/gson-2.6.2.jar:lib/sqlite-jdbc-3.21.0.jar" $files

if [ $? -eq 0 ]
then
	echo Compiled..
	docker stop $(docker ps -aq)
	# docker rm $(docker ps -aq)
	# docker rmi $(docker images -f "dangling=true" -q)
	docker system prune -fa
	docker image rm pubsub #_sub
	#docker image rm pubsub_em
	docker image build -t pubsub . #_em --build-arg SYSTEM_TYPE=EventManager .
	# docker image build -t pubsub_sub --build-arg SYSTEM_TYPE=PubSubAgent .
else
	echo Did Not Compile..
	exit 1
fi
