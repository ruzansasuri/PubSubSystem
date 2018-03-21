#!/bin/sh

LOGFILE="log/PubSub.log"
touch $LOGFILE
echo Starting PubSubSystem..
wait 100
echo Creating images..This may take a few seconds..
./create.sh >> $LOGFILE 2>&1
if [ $? -eq 0 ]
then
	echo Created images!
	sleep 1
	echo Starting Docker..
	./run.sh >> $LOGFILE 2>&1
	echo Ready!
else
	echo Image Creation Failed
fi
