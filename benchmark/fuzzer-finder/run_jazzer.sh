#!/bin/bash
if [ $# -lt 4 ]; then
  echo "Usage: ./run_jazzer.sh <fuzzer_dir> <fuzzer_class_name> <classpath> <report_dir_name> [fuzzing time]"
  exit 1
fi

JAZZER_DIR=/home/junyangshao/Desktop/playground/research/SunnyMilkFuzzerJDK/jazzer/jazzer_orig
export JAVA_HOME=/home/junyangshao/Desktop/playground/jdk/build/linux-x86_64-server-release/jdk
export PERF_MAP_HOME=/home/junyangshao/Desktop/playground/research/perf-map-agent/out
cd $1

$JAVA_HOME/bin/javac -cp $JAZZER_DIR/jazzer_standalone.jar:$3 $2Jazzer.java
rm -r fuzzerOut
mkdir fuzzerOut
rm -r $4
mkdir $4
# rm /home/junyangshao/Desktop/playground/research/SunnyMilkFuzzerJDK/benchmark/fuzzer-finder/jzr_out/*
if [[ $# -eq 5 ]]; then
	# perf record -o jazzer_perf.data timeout "$5"s $JAZZER_DIR/jazzer fuzzerOut --cp=$3:./ --trace=none --target_class=$2Jazzer -timeout=1 > $4/jazzer-out 2>&1
	timeout "$5"s $JAZZER_DIR/jazzer fuzzerOut --cp=$3:./ --trace=none --target_class=$2Jazzer > $4/jazzer-out 2>&1

	# # Start the Java application as a background job
	# timeout "$5"s $JAZZER_DIR/jazzer fuzzerOut --cp=$3:./ --trace=none --target_class=$2Jazzer > $4/jazzer-out 2>&1 &
	# # Get the PID of the Java application
	# JAVA_PID=$!
	# JAVA_PID=$(($JAVA_PID + 2))

	# echo $JAVA_PID

	# # Sleep for a bit to ensure that the Java application has started
	# sleep 1

	# # Generate the map file using perf-map-agent
	# $JAVA_HOME/bin/java -cp /home/junyangshao/Desktop/playground/research/perf-map-agent/out/attach-main.jar net.virtualvoid.perf.AttachOnce $JAVA_PID

	# # cat /proc/$JAVA_PID/maps > $2Jazzer_proc_maps

	# # Run the perf command
	# # perf record --call-graph=lbr -o $2Jazzer_perf.data -p $JAVA_PID -g -- sleep "$5"
	# perf record -o $2Jazzer_perf.data -p $JAVA_PID -g -- sleep "$5"
else
	$JAZZER_DIR/jazzer fuzzerOut --cp=$3:./ --trace=none --target_class=$2Jazzer > $4/jazzer-out 2>&1
fi
# mv /home/junyangshao/Desktop/playground/research/SunnyMilkFuzzerJDK/benchmark/fuzzer-finder/jzr_out \
# 	/home/junyangshao/Desktop/playground/research/SunnyMilkFuzzerJDK/benchmark/fuzzer-finder/jzr_out_old
# mkdir /home/junyangshao/Desktop/playground/research/SunnyMilkFuzzerJDK/benchmark/fuzzer-finder/jzr_out
sh ../../fuzzer-finder/jacoco_report.sh $2Main $3 $4
mv fuzzerOut $4
$JAZZER_DIR/clean.sh
