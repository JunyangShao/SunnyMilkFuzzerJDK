#!/bin/bash
export JAVA_HOME=/home/junyangshao/Desktop/playground/jdk/build/linux-x86_64-server-release/jdk
$JAVA_HOME/bin/javac -cp jazzer_standalone.jar:fastjson-1.2.75.jar ExampleFuzzer2.java
~/Desktop/playground/research/benchmark/jazzer/jazzer --cp=fastjson-1.2.75.jar:./ --trace=none --target_class=ExampleFuzzer2
~/Desktop/playground/research/benchmark/jazzer/clean.sh
