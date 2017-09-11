#!/bin/bash
cd `dirname $0`
cd ../conf
CONF_DIR=`pwd`
cd ../lib
LIB_DIR=`pwd`
cd ../
BASE_DIR=`pwd`


SERVER_NAME='com.doc.MarkdownApplication'
PIDS=`ps -ef | grep java | grep "$LIB_DIR" |grep $SERVER_NAME|awk '{print $2}'`
if [ -n "$PIDS" ]; then
    echo "start fail! The $SERVER_NAME already started!"
    exit 1
fi


LIB_JARS=`ls $LIB_DIR|grep .jar|awk '{print "'$LIB_DIR'/"$0}'|tr "\n" ":"`

CLASSPATH="$CONF_DIR:$LIB_JARS"


LOG_PATH="$BASE_DIR/log"

if [ ! -d "$LOG_PATH" ]; then
    mkdir -p $LOG_PATH
fi

nohup java -Djava.net.preferIPv4Stack=true -server -Xms1g -Xmx1g -XX:PermSize=128m  -classpath $CLASSPATH $SERVER_NAME > $LOG_PATH/application.out 2>&1 &

echo "start "$SERVER_NAME" success!"
