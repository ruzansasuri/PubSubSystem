#!/bin/bash
PROJDIR="/home/ruzan/DS/PubSubSystem/PubSubSystem/src/edu/rit/CSCI652/"

files=`find -name "*.java"`
javac -d "out" -classpath lib/gson-2.6.2.jar $files
if [ $? -eq 0 ]
then
	echo Compiled..
	docker image rm pubsub_sub
	docker image rm pubsub_em
	docker image build -e EventManager -t pubsub_sub .
	docker image build -e PubSubAgent -t pubsub_em .
fi
