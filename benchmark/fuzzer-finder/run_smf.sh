#!/bin/bash
if [ $# -lt 4 ]; then
  echo "Usage: ./run_smf.sh <fuzzer_dir> <fuzzer_class_name> <classpath> <report_dir_name> [fuzzing time]"
  exit 1
fi

export JAVA_HOME=/home/junyangshao/Desktop/playground/research/SunnyMilkFuzzerJDK/SunnyMilkJDK/build/linux-x86_64-server-release/jdk
export SMF_HOME=/home/junyangshao/Desktop/playground/research/SunnyMilkFuzzerJDK/
JAZZER_DIR=/home/junyangshao/Desktop/playground/research/SunnyMilkFuzzerJDK/jazzer

cd $1

# To include a library path and make it the most prioritized search path, include it as "xxx.h" and when compile, -I$DIR_CONTAINING_XXX.

# Compilation for the command:
$JAVA_HOME/bin/javac -cp "$3" $2SMF.java

g++ -Wno-write-strings -g -O2 -fPIC -pthread -I"$JAVA_HOME/include" -I"$JAVA_HOME/include/linux" -I$SMF_HOME/fuzzer \
-L"${JAVA_HOME}/lib/server" -L$SMF_HOME/fuzzer -o LibFuzzerLauncher $SMF_HOME/src/LibFuzzerLauncher.cpp -ljvm -lFuzzer -ldl

export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:$JAVA_HOME/lib/server

rm -r fuzzerOut
mkdir fuzzerOut
rm -r $4
mkdir $4
rm /home/junyangshao/Desktop/playground/research/SunnyMilkFuzzerJDK/benchmark/fuzzer-finder/smf_out/*
if [[ $# -eq 5 ]]; then
	perf record -o smf_perf.data timeout "$5"s ./LibFuzzerLauncher fuzzerOut $2SMF "---p=$3" > $4/smf-out 2>&1
	# timeout "$5"s ./LibFuzzerLauncher fuzzerOut $2SMF "---p=$3" > $4/smf-out 2>&1
else
	./LibFuzzerLauncher fuzzerOut $2SMF "---p=$3" > $4/smf-out 2>&1
fi
mv /home/junyangshao/Desktop/playground/research/SunnyMilkFuzzerJDK/benchmark/fuzzer-finder/smf_out \
	/home/junyangshao/Desktop/playground/research/SunnyMilkFuzzerJDK/benchmark/fuzzer-finder/smf_out_old
mkdir /home/junyangshao/Desktop/playground/research/SunnyMilkFuzzerJDK/benchmark/fuzzer-finder/smf_out
sh ../../fuzzer-finder/jacoco_report.sh $2Main $3 $4
mv fuzzerOut $4
$JAZZER_DIR/clean.sh
