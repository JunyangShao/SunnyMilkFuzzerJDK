#!/bin/bash
JAZZER_DIR=/home/junyangshao/Desktop/playground/research/SunnyMilkFuzzerJDK/jazzer
export JAVA_HOME=/home/junyangshao/Desktop/playground/jdk/build/linux-x86_64-server-release/jdk
$JAVA_HOME/bin/javac -cp $JAZZER_DIR/jazzer_standalone.jar:gson.jar FuzzParseJazzer.java
~/Desktop/playground/research/benchmark/jazzer/jazzer --cp=gson.jar:./ --trace=none --target_class=FuzzParseJazzer
~/Desktop/playground/research/benchmark/jazzer/clean.sh