#!/bin/bash
# export JAVA_HOME=/home/junyangshao/Desktop/playground/jdk/build/linux-x86_64-server-release/jdk
rm example_fuzz_text_report
export JAVA_HOME=../SunnyMilkJDK/build/linux-x86_64-server-release/jdk
$JAVA_HOME/bin/javac -cp jazzer_standalone.jar:fastjson-1.2.75.jar ExampleFuzzer2.java
#./jazzer --cp=fastjson-1.2.75.jar:./ -max_total_time=2 --trace=none --target_class=ExampleFuzzer2 --coverage_dump=example_fuzz.exec --coverage_report=example_fuzz_text_report # &
./jazzer --cp=fastjson-1.2.75.jar:./ --trace=none --target_class=ExampleFuzzer2 --coverage_dump=example_fuzz.exec --coverage_report=example_fuzz_text_report

#$JAVA_HOME/bin/java -jar jacoco/lib/jacococli.jar report example_fuzz.exec --classfiles fastjson-1.2.75.jar --sourcefiles ExampleFuzzer2.java --html ExampleFuzzReport
# JAVA_PID=$!
# # JAVA_PID=$(($JAVA_PID + 2))
# $JAVA_HOME/bin/java -cp /home/junyangshao/Desktop/playground/research/perf-map-agent/out/attach-main.jar net.virtualvoid.perf.AttachOnce $JAVA_PID
# perf record -o smf_perf.data -p $JAVA_PID -g -- sleep 20
./clean.sh
