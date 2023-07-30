cd /home/junyangshao/Desktop/playground/research/SunnyMilkFuzzerJDK/benchmark/fuzzer-finder

python run_fuzzers2.py all 300
sleep 5
python run_fuzzers2.py backup 5min-2023-7-18-noavx-nostraceomit2
sleep 5
python run_fuzzers2.py all 300
sleep 5
python run_fuzzers2.py backup 5min-2023-7-18-noavx-nostraceomit3
sleep 5

# patch the avx instructions hack for coverage map scanning.
mv /home/junyangshao/Desktop/playground/research/SunnyMilkFuzzerJDK/benchmark/fuzzer-finder/patches/FuzzerTracePC.h \
    /home/junyangshao/Desktop/playground/research/SunnyMilkFuzzerJDK/fuzzer
sleep 5
cd /home/junyangshao/Desktop/playground/research/SunnyMilkFuzzerJDK/jazzer
./update_jazzer.sh
sleep 5
cd /home/junyangshao/Desktop/playground/research/SunnyMilkFuzzerJDK/benchmark/fuzzer-finder

python run_fuzzers2.py all 300
sleep 5
python run_fuzzers2.py backup 5min-2023-7-18-nostraceomit
sleep 5
python run_fuzzers2.py all 300
sleep 5
python run_fuzzers2.py backup 5min-2023-7-18-nostraceomit2
sleep 5
python run_fuzzers2.py all 300
sleep 5
python run_fuzzers2.py backup 5min-2023-7-18-nostraceomit3
sleep 5

# patch the avx instructions hack for coverage map scanning.
mv /home/junyangshao/Desktop/playground/research/SunnyMilkFuzzerJDK/benchmark/fuzzer-finder/patches/jvm_tooling.cpp \
    /home/junyangshao/Desktop/playground/research/jazzer2/launcher
sleep 5
cd /home/junyangshao/Desktop/playground/research/SunnyMilkFuzzerJDK/jazzer
./update_jazzer.sh
sleep 5
cd /home/junyangshao/Desktop/playground/research/SunnyMilkFuzzerJDK/benchmark/fuzzer-finder

python run_fuzzers2.py all 300
sleep 5
python run_fuzzers2.py backup 5min-2023-7-18
sleep 5
python run_fuzzers2.py all 300
sleep 5
python run_fuzzers2.py backup 5min-2023-7-18-2
sleep 5
python run_fuzzers2.py all 300
sleep 5
python run_fuzzers2.py backup 5min-2023-7-18-3
sleep 5
