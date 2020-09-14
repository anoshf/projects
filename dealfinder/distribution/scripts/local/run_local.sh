#!/bin/sh

LOCAL_PASSBOOK_JAR=`ls /local_pbapi_target/passbook-api*.jar`

if [ ! -f "$LOCAL_PASSBOOK_JAR" ]; then
  echo "ERROR: couldn't find a passbook api JAR file in ./target. Did you forget to build one with maven?"
  exit 1
fi

echo "LOCAL passbook api jar: $LOCAL_PASSBOOK_JAR"

echo "Waiting 8 seconds for PB DB container to start.."
sleep 8

#java -jar $LOCAL_PASSBOOK_JAR
java -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=5005,suspend=n -jar $LOCAL_PASSBOOK_JAR