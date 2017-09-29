#!/bin/bash
pid=`ps -ef | grep -w LoadBalancerService | grep -v grep | awk '{print $2}'`

if [ $pid ]; then
  kill -9 $pid
  echo "Stopped."
else
  echo "Nothing to stop any process."
fi
