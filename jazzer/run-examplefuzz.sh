#!/bin/bash
# export JAVA_HOME=/home/junyangshao/Desktop/playground/jdk/build/linux-x86_64-server-release/jdk
export JAVA_HOME=../SunnyMilkJDK/build/linux-x86_64-server-release/jdk
$JAVA_HOME/bin/javac -cp jazzer_standalone.jar:fastjson-1.2.75.jar ExampleFuzzer2.java
./jazzer --cp=fastjson-1.2.75.jar:./ --trace=none --target_class=ExampleFuzzer2 # &
# JAVA_PID=$!
# # JAVA_PID=$(($JAVA_PID + 2))
# $JAVA_HOME/bin/java -cp /home/junyangshao/Desktop/playground/research/perf-map-agent/out/attach-main.jar net.virtualvoid.perf.AttachOnce $JAVA_PID
# perf record -o smf_perf.data -p $JAVA_PID -g -- sleep 20
./clean.sh
