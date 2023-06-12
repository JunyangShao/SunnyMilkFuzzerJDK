#!/bin/bash
if [ $# -ne 3 ]; then
  echo "Usage: ./run_jazzer.sh <dir> <fuzzer_class_name> <classpath>"
  exit 1
fi

JAZZER_DIR=/home/junyangshao/Desktop/playground/research/SunnyMilkFuzzerJDK/jazzer
export JAVA_HOME=/home/junyangshao/Desktop/playground/jdk/build/linux-x86_64-server-release/jdk
cd $1

$JAVA_HOME/bin/javac -cp $JAZZER_DIR/jazzer_standalone.jar:$3 $2.java
$JAZZER_DIR/jazzer --cp=$3:./ --trace=none --target_class=$2
$JAZZER_DIR/clean.sh