#!/bin/bash
export JAVA_HOME=/home/junyangshao/Desktop/playground/jdk/build/linux-x86_64-server-release/jdk
$JAVA_HOME/bin/javac -cp jazzer_standalone.jar DacapoFuzzer.java
~/Desktop/playground/research/benchmark/jazzer/jazzer --cp=./ --trace=none --target_class=DacapoFuzzer 
#~/Desktop/playground/research/benchmark/jazzer/jazzer --cp=./ --target_class=DacapoFuzzer 
~/Desktop/playground/research/benchmark/jazzer/clean.sh
