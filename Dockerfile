FROM openjdk
CMD mkdir project
COPY project project
EXPOSE 8080
WORKDIR /project
CMD java -cp src HelloWorld