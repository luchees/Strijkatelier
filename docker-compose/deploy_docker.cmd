#build docker file for REACT app
cd ../frontend && docker build -f Dockerfile -t react:dev . && cd ../docker-compose && docker-compose up -d