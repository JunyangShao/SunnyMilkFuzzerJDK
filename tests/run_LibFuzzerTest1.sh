export JAVA_HOME=../SunnyMilkJDK/build/linux-x86_64-server-release/jdk

# To include a library path and make it the most prioritized search path, include it as "xxx.h" and when compile, -I$DIR_CONTAINING_XXX.

# Compilation for the command:
$JAVA_HOME/bin/javac LibFuzzerTest1.java

g++ -fPIC -pthread -I"$JAVA_HOME/include" -I"$JAVA_HOME/include/linux" -I../fuzzer -L"${JAVA_HOME}/lib/server" -L../fuzzer -o LibFuzzerLauncher ../src/LibFuzzerLauncher.cpp -ljvm -lFuzzer -ldl

export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:$JAVA_HOME/lib/server

mkdir fuzzerOut
./LibFuzzerLauncher fuzzerOut LibFuzzerTest1
rm -r fuzzerOut
./clean.sh

# $JAVA_HOME/bin/java -XX:+UnlockDiagnosticVMOptions -XX:+PrintCompilation -XX:+PrintAssembly -XX:LogFile=log.txt -XX:TieredStopAtLevel=1 LibFuzzerTest1
