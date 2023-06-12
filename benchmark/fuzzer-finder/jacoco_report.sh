#!/bin/bash
export JAVA_HOME=/home/junyangshao/Desktop/playground/research/SunnyMilkFuzzerJDK/SunnyMilkJDK/build/linux-x86_64-server-release/jdk
JAZZER_DIR=/home/junyangshao/Desktop/playground/research/SunnyMilkFuzzerJDK/jazzer

# Compilation for the command:
$JAVA_HOME/bin/javac -cp gson.jar FuzzParseMain.java
$JAVA_HOME/bin/java -javaagent:$JAZZER_DIR/jacoco/lib/jacocoagent.jar=destfile=jacoco.exec -cp .:gson.jar FuzzParseMain hello

$JAVA_HOME/bin/java -jar $JAZZER_DIR/jacoco/lib/jacococli.jar report jacoco.exec --classfiles . --sourcefiles . --html $1
