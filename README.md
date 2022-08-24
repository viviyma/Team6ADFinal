# Trashify!
This repository contains the web app, REST endpoints for the android app, and backend of the system. 

## Features of the Trashify Web App
1. Allows sign-in to track your recycling journey
2. Uploading of images to classify trash with built-in Machine Learning support
3. Find the nearest recycling bin
4. Track your weekly recycling statistics 
5. Keep updated with the latest environmental news
6. In-sync, and cross-compatible with accompanying mobile app

## Technical Details
The web app is a Spring MVC project with baked-in Machine-Learning support that is deployed as a docker container in the cloud. It connects to a seperate mySQL container in the same cloud environment for database support. The seperation provides database integrity in case the web app container goes down. The REST endpoints provided within the web app are also opened for the android app to connect to so userdata is consistent between the android app and web app. Dockerfile and docker-compose file for the webapp are also provided. 

## Future Plans
### App Features
1. Improved gamification system involving teams and incorporation of leaderboards 
2. Incorporating promotions and rewards from partnered companies

### System Improvements
1. HTTPS support
2. Spring Security Authentication
3. JWT support for proper REST endpoint security
4. Better seperation between the Machine Learning model by deploying a seperate Flask API as a docker container in the same cloud environment for increased system flexibility
5. Incorporation of REACTjs or similar framework to create a proper front-end to seperate the web backend and frontend completely and create a more reactive user experience

## How to run the app
`Git clone` the repo into your system and change the **system.properties** file to your desired database configuration in your favourite IDE. Some of the views are also set to call the REST endpoints so do change those to localhost as well, if required. Run the app as a Spring Boot App and you will be able to access the webapp on the browser by typing `localhost:8080` in the browser.
