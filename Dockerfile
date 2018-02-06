FROM openjdk
CMD mkdir project
COPY . project
ARG SYSTEM_TYPE
ENV SYSTEM_TYPE=${SYSTEM_TYPE}
EXPOSE 8079
WORKDIR /project
CMD java -cp "out:lib/gson-2.6.2.jar:lib/sqlite-jdbc-3.21.0.jar;" edu.rit.CSCI652.impl.$SYSTEM_TYPE
