FROM openjdk
CMD mkdir project
COPY . project
WORKDIR /project
CMD java -cp "out:lib/gson-2.6.2.jar:lib/sqlite-jdbc-3.21.0.jar:database/pubsub.db" edu.rit.CSCI652.impl.$SYSTEM_TYPE
