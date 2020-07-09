mvn clean install && docker build -f Dockerfile -t strijkatelier:dev --rm=true . && docker run -p 8080:8081 strijkatelier:dev --net=host
