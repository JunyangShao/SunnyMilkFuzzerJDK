export JAVA_HOME=../SunnyMilkJDK/build/linux-x86_64-server-release/jdk

# To include a library path and make it the most prioritized search path, include it as "xxx.h" and when compile, -I$DIR_CONTAINING_XXX.

# Compilation for the command:
$JAVA_HOME/bin/javac -h . JNIFuzzerTest2.java

gcc -fPIC -I"$JAVA_HOME/include" -I"$JAVA_HOME/include/linux" -shared -o libSunnyMilkFuzzer.so ../src/SunnyMilkFuzzer.c

$JAVA_HOME/bin/java -Djava.library.path=. -Xint JNIFuzzerTest2
./clean.sh