FROM openjdk
CMD mkdir project
COPY lib project
ARG SYSTEM_TYPE
CMD java -cp out edu.rit.CSCI652.impl.$SYSTEM_TYPE
EXPOSE 8080
WORKDIR /project/out
CMD java -cp "../lib/;" edu.rit.CSCI652.impl.PubSubAgent
