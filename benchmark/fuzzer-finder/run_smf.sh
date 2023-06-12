#!/bin/bash
if [ $# -lt 3 ]; then
  echo "Usage: ./run_smf.sh <dir> <fuzzer_class_name> <classpath> [fuzzing time]"
  exit 1
fi

export JAVA_HOME=/home/junyangshao/Desktop/playground/research/SunnyMilkFuzzerJDK/SunnyMilkJDK/build/linux-x86_64-server-release/jdk
export SMF_HOME=/home/junyangshao/Desktop/playground/research/SunnyMilkFuzzerJDK/
JAZZER_DIR=/home/junyangshao/Desktop/playground/research/SunnyMilkFuzzerJDK/jazzer

cd $1

# To include a library path and make it the most prioritized search path, include it as "xxx.h" and when compile, -I$DIR_CONTAINING_XXX.

# Compilation for the command:
$JAVA_HOME/bin/javac -cp "$3" $2.java

g++ -g -O2 -fPIC -pthread -I"$JAVA_HOME/include" -I"$JAVA_HOME/include/linux" -I$SMF_HOME/fuzzer \
-L"${JAVA_HOME}/lib/server" -L$SMF_HOME/fuzzer -o LibFuzzerLauncher $SMF_HOME/src/LibFuzzerLauncher.cpp -ljvm -lFuzzer -ldl

export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:$JAVA_HOME/lib/server

mkdir fuzzerOut
rm -r smf-report
mkdir smf-report
if [[ $# -eq 4 ]]; then
	timeout "$4"s ./LibFuzzerLauncher fuzzerOut $2 "---p=$3" > smf-report/smf-out 2>&1
else
	./LibFuzzerLauncher fuzzerOut $2 "---p=$3" > smf-report/smf-out 2>&1
fi
sh ../../fuzzer-finder/jacoco_report.sh smf-report
rm -r fuzzerOut
$JAZZER_DIR/clean.sh
