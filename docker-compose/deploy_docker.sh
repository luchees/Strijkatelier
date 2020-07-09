#!/bin/bash

# build docker file for REACT app
docker build -f Dockerfile -t react:dev ../frontend

docker-compose up -d