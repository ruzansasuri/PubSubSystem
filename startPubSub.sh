#!/bin/sh

LOGFILE="log/PubSub.log"
touch $LOGFILE
echo Starting PubSubSystem..
wait 100
echo Creating images..This may take a few seconds..
./create.sh >> $LOGFILE 2>&1
echo Created images!
wait 100
echo Starting Docker..
echo Building network..
echo Starting DB..
echo Taking a coffee break..
echo Ready!
./pubsub.sh >> $LOGFILE 2>&1
