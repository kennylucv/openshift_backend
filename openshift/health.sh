#!/bin/sh

cd /opt
wget localhost:9080/backend/health_check 

MYVAR=$(cat health_check)
rm -f greeting
if [ "$MYVAR" = "health_check_passed" ] 
then
    echo `date` > health.log
    exit 0
else
    #touch notWorking
    exit 1
fi
