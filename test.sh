PROJDIR="/home/ruzan/DS/PubSubSystem/PubSubSystem"
PACKDIR="$PROJDIR/src/edu/rit/CSCI652"
#javac -d "out" -cp "out:$PACKDIR/demo;" $PACKDIR/demo/*.java
#javac -d "$PROJDIR/out" -cp "$PROJDIR/out/:$PACKDIR/impl/:$PACKDIR/demo/:$PROJDIR/lib/;" $PACKDIR/impl/*.java
rm -rf out/*

files=`find -name "*.java"`
javac -d "out" -classpath lib/gson-2.6.2.jar $files
echo $?

