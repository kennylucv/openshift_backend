#!/bin/sh
i=0
while [ $i -le 10 ]
do
    curl http://backend-dev-env.127.0.0.1.nip.io/backend/greeting
    i=$(( $i + 1 ))
done