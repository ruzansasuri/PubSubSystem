PROJDIR="/home/ruzan/DS/PubSubSystem/PubSubSystem/src/edu/rit/CSCI652"
ls $PROJDIR/demo/*.java | while read java_file; do
	javac -d "out" -cp "out:$PROJDIR/demo;" $PROJDIR/demo/$java_file
done
echo $?
