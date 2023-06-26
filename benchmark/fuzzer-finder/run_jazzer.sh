#!/bin/bash
if [ $# -lt 4 ]; then
  echo "Usage: ./run_jazzer.sh <fuzzer_dir> <fuzzer_class_name> <classpath> <report_dir_name> [fuzzing time]"
  exit 1
fi

JAZZER_DIR=/home/junyangshao/Desktop/playground/research/SunnyMilkFuzzerJDK/jazzer
export JAVA_HOME=/home/junyangshao/Desktop/playground/jdk/build/linux-x86_64-server-release/jdk
cd $1

$JAVA_HOME/bin/javac -cp $JAZZER_DIR/jazzer_standalone.jar:$3 $2Jazzer.java
rm -r fuzzerOut
mkdir fuzzerOut
rm -r $4
mkdir $4
rm /home/junyangshao/Desktop/playground/research/SunnyMilkFuzzerJDK/benchmark/fuzzer-finder/jzr_out/*
if [[ $# -eq 5 ]]; then
	# perf record -o jazzer_perf.data timeout "$5"s $JAZZER_DIR/jazzer fuzzerOut --cp=$3:./ --trace=none --target_class=$2Jazzer > $4/jazzer-out 2>&1
	timeout "$5"s $JAZZER_DIR/jazzer fuzzerOut --cp=$3:./ --trace=none --target_class=$2Jazzer > $4/jazzer-out 2>&1
else
	$JAZZER_DIR/jazzer fuzzerOut --cp=$3:./ --trace=none --target_class=$2Jazzer > $4/jazzer-out 2>&1
fi
mv /home/junyangshao/Desktop/playground/research/SunnyMilkFuzzerJDK/benchmark/fuzzer-finder/jzr_out \
	/home/junyangshao/Desktop/playground/research/SunnyMilkFuzzerJDK/benchmark/fuzzer-finder/jzr_out_old
mkdir /home/junyangshao/Desktop/playground/research/SunnyMilkFuzzerJDK/benchmark/fuzzer-finder/jzr_out
sh ../../fuzzer-finder/jacoco_report.sh $2Main $3 $4
mv fuzzerOut $4
$JAZZER_DIR/clean.sh
