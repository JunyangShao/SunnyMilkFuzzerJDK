export JAVA_HOME=/home/junyangshao/Desktop/playground/research/SunnyMilkFuzzerJDK/SunnyMilkJDK/build/linux-x86_64-server-release/jdk
export SMF_HOME=/home/junyangshao/Desktop/playground/research/SunnyMilkFuzzerJDK/

# To include a library path and make it the most prioritized search path, include it as "xxx.h" and when compile, -I$DIR_CONTAINING_XXX.

# Compilation for the command:
$JAVA_HOME/bin/javac -cp ".:gson.jar" FuzzParseSMF.java

g++ -g -O2 -fPIC -pthread -I"$JAVA_HOME/include" -I"$JAVA_HOME/include/linux" -I$SMF_HOME/fuzzer -L"${JAVA_HOME}/lib/server" -L$SMF_HOME/fuzzer -o LibFuzzerLauncher $SMF_HOME/src/LibFuzzerLauncher.cpp -ljvm -lFuzzer -ldl

export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:$JAVA_HOME/lib/server

mkdir fuzzerOut
#perf record ./LibFuzzerLauncher fuzzerOut LibFuzzerTest3
#perf report
./LibFuzzerLauncher fuzzerOut FuzzParseSMF "---p=./gson.jar"
rm -r fuzzerOut
./clean.sh
