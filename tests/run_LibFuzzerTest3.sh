export JAVA_HOME=../SunnyMilkJDK/build/linux-x86_64-server-release/jdk
#export JAVA_HOME=../SunnyMilkJDK/build/linux-x86_64-server-slowdebug/jdk

# To include a library path and make it the most prioritized search path, include it as "xxx.h" and when compile, -I$DIR_CONTAINING_XXX.

# Compilation for the command:
$JAVA_HOME/bin/javac -cp ".:fastjson-1.2.75.jar" LibFuzzerTest3.java

g++ -g -O2 -fPIC -pthread -I"$JAVA_HOME/include" -I"$JAVA_HOME/include/linux" -I../fuzzer -L"${JAVA_HOME}/lib/server" -L../fuzzer -o LibFuzzerLauncher ../src/LibFuzzerLauncher.cpp -ljvm -lFuzzer -ldl

export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:$JAVA_HOME/lib/server

mkdir fuzzerOut
#perf record ./LibFuzzerLauncher fuzzerOut LibFuzzerTest3
#perf report
perf record ./LibFuzzerLauncher fuzzerOut LibFuzzerTest3 "---p=./fastjson-1.2.75.jar"
perf report
rm -r fuzzerOut
./clean.sh
