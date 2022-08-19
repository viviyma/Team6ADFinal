# Trashify!

## Overview
This repository contains the web app, REST endpoints for the android app, and backend of the system. 

## Features of the Trashify Web App
1. Allows sign-in
2. Uploading of images to classify trash with built-in Machine Learning support
3. Find the nearest recycling bin
4. Check Weekly Statistics 
5. In sync with accompanying mobile app

## Technical Details
The web app is deployed as a docker container and connects to a seperate mySQL database container in the same cloud environment. The REST endpoints are also opened for the android app to connect to so users of the system can switch between the web app and android app interchangebly. Dockerfile and docker-compose file for the webapp is provided.

