#!/bin/bash
export JAVA_HOME=/home/junyangshao/Desktop/playground/jdk/build/linux-x86_64-server-release/jdk
# export JAVA_HOME=../SunnyMilkJDK/build/linux-x86_64-server-release/jdk
$JAVA_HOME/bin/javac -cp jazzer_orig/jazzer_standalone.jar:fastjson-1.2.75.jar ExampleFuzzer2.java
./jazzer_orig/jazzer --cp=fastjson-1.2.75.jar:./ --trace=none --target_class=ExampleFuzzer2
./clean.sh
