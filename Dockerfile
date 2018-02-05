FROM openjdk
CMD mkdir project
COPY lib project
EXPOSE 8080
WORKDIR /project/out
CMD java -cp "../lib/;" edu.rit.CSCI652.impl.PubSubAgent
