# Trashify!

## Overview
This repository contains the web app, REST endpoints for the android app, and backend of the system. 

## Features of the Trashify Web App
1. Allows sign-in to track your recycling journey
2. Uploading of images to classify trash with built-in Machine Learning support
3. Find the nearest recycling bin
4. Track your weekly recycling statistics 
5. In-sync, and cross-compatible with accompanying mobile app

## Technical Details
The web app is a Spring MVC project with baked-in Machine-Learning support that is deployed as a docker container in the cloud. It connects to a seperate mySQL container in the same cloud environment for database support. The seperation provides database integrity in case the web app container goes down. The REST endpoints provided within the web app are also opened for the android app to connect to so userdata is consistent between the android app and web app. Dockerfile and docker-compose file for the webapp are also provided. 

## Future Plans
1. HTTPS support
2. Spring Security Autentication
3. JWT Token support for REST endpoint security
4. Better seperation between Machine Learning Model by deploying a seperate Flask API in the same cloud environment for increased system flexibility
5. Incorporation of REACTJs or similar frameworks to create a proper front-end to seperate the web backend and frontend completely

