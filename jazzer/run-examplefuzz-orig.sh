#!/bin/bash
export JAVA_HOME=/home/junyangshao/Desktop/playground/jdk/build/linux-x86_64-server-release/jdk
# export JAVA_HOME=../SunnyMilkJDK/build/linux-x86_64-server-release/jdk
$JAVA_HOME/bin/javac -cp jazzer_orig/jazzer_standalone.jar:fastjson-1.2.75.jar ExampleFuzzer2.java
#perf record -o jazzer_perf.data ./jazzer_orig/jazzer --cp=fastjson-1.2.75.jar:./ --trace=none --target_class=ExampleFuzzer2
./jazzer_orig/jazzer --cp=fastjson-1.2.75.jar:./ --trace=none --target_class=ExampleFuzzer2 --coverage_dump=example_fuzz_orig.exec --coverage_report=example_fuzz_orig_text_report
./clean.sh
