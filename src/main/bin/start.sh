#!/usr/bin/env bash

### TODO create start stop using pid file
cd ..
export CLASS_PATH="config:lib/*"
# ADD MEM OPTS AND GS OPTS
export JAVA_OPTS="-server -showversion $MEM_OPTS $GC_OPTS -Dfile.encoding=utf-8"

nohup java -classpath $CLASS_PATH $JAVA_OPTS my.interviews.Init $@ > hohup.out 2>&1&
