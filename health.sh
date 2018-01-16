#!/bin/sh
cd /opt
wget localhost:9080/backend/greeting 

MYVAR=$(cat greeting)
rm -f greeting
if [ "$MYVAR" = "greetings" ] 
then
    echo `date` > health.log
    exit 0
else
    #touch notWorking
    exit 1
fi