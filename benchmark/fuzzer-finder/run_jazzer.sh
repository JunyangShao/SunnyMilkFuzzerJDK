#!/bin/bash
if [ $# -lt 3 ]; then
  echo "Usage: ./run_jazzer.sh <dir> <fuzzer_class_name> <classpath> [fuzzing time]"
  exit 1
fi

JAZZER_DIR=/home/junyangshao/Desktop/playground/research/SunnyMilkFuzzerJDK/jazzer
export JAVA_HOME=/home/junyangshao/Desktop/playground/jdk/build/linux-x86_64-server-release/jdk
cd $1

$JAVA_HOME/bin/javac -cp $JAZZER_DIR/jazzer_standalone.jar:$3 $2.java
mkdir fuzzerOut
rm -r jazzer-report
mkdir jazzer-report
if [[ $# -eq 4 ]]; then
	timeout "$4"s $JAZZER_DIR/jazzer fuzzerOut --cp=$3:./ --trace=none --target_class=$2 > jazzer-report/jazzer-out 2>&1
else
	$JAZZER_DIR/jazzer fuzzerOut --cp=$3:./ --trace=none --target_class=$2 > jazzer-report/jazzer-out 2>&1
fi
sh ../../jacoco_report.sh jazzer-report
$JAZZER_DIR/clean.sh
