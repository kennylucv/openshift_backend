#!/bin/sh

# usage
# ./test-backend dev

# read environment from command line
env=$1

url="http://backend-$env-env.52.233.32.176.nip.io/backend/welcome"

# show output
while true
do
    curl $url
    echo ""
    sleep 1
done