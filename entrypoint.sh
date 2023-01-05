#!/bin/sh

if [ -z "$ATTACH_JFR" ]; then
  exec java -jar /ocpp-charge-point-emulator-cli.jar "$@"
else
  JFR_RECORD_NAME="ocpp-charge-point-emulator-cli$(/bin/date +%s)"
  exec java -XX:+FlightRecorder -XX:StartFlightRecording=name="$JFR_RECORD_NAME",settings="default",dumponexit=true,filename="/jfr/$JFR_RECORD_NAME.jfr" -XX:FlightRecorderOptions=stackdepth=2048 -jar /ocpp-charge-point-emulator-cli.jar "$@"
fi
