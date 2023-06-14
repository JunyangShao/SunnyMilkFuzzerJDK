#!/bin/bash
if [ $# -ne 3 ]; then
  echo "Usage: ./jacoco_report.sh <fuzzer_main_name> <classpath> <report_path>"
  exit 1
fi

export JAVA_HOME=/home/junyangshao/Desktop/playground/research/SunnyMilkFuzzerJDK/SunnyMilkJDK/build/linux-x86_64-server-release/jdk
JAZZER_DIR=/home/junyangshao/Desktop/playground/research/SunnyMilkFuzzerJDK/jazzer

# Compilation for the command:
$JAVA_HOME/bin/javac -cp $2 $1.java
$JAVA_HOME/bin/java -javaagent:$JAZZER_DIR/jacoco/lib/jacocoagent.jar=destfile=jacoco.exec -cp .:$2 $1 hello

$JAVA_HOME/bin/java -jar $JAZZER_DIR/jacoco/lib/jacococli.jar report jacoco.exec --classfiles . --sourcefiles . --html $3
$JAVA_HOME/bin/java -jar $JAZZER_DIR/jacoco/lib/jacococli.jar report jacoco.exec --classfiles . --csv $3.csv
