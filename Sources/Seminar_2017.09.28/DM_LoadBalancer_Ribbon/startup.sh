#!/bin/bash
pid=`ps -ef | grep -w LoadBalancerService | grep -v grep | awk '{print $2}'`

if [ $pid ]; then
  echo "The server has been started already !!"
  echo "PID : $pid"
else
  nohup java -jar -Du=LoadBalancerService LoadBalancer-0.0.1-SNAPSHOT.jar 1> /dev/null 2>&1 &
  sleep 10

  pid2=`ps -ef | grep -w LoadBalancerService | grep -v grep | awk '{print $2}'`
  if [ $pid2 ]; then
    echo "The Server started."
  else
    echo "Failure Starting Server."
  fi
fi
