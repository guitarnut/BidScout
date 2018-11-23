#!/bin/bash

BRANCH=${1:-master}

GREEN=`tput -Txterm-256color setaf 2`

# Change to working directory
cd /opt/app/BidScout 2> /dev/null

# Checkout Branch
echo "${GREEN}Checking out ${BRANCH} Branch${RESET}"
git checkout ${BRANCH} 2> /dev/null

git pull 2> /dev/null

mvn clean install 2> /dev/null

nohup java -jar target/bidscout-0.0.1-SNAPSHOT.jar&
