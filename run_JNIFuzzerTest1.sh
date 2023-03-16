export JAVA_HOME=./SunnyMilkJDK/build/linux-x86_64-server-release/jdk

# To include a library path and make it the most prioritized search path, include it as "xxx.h" and when compile, -I$DIR_CONTAINING_XXX.

# Compilation for the command:
$JAVA_HOME/bin/javac -cp ".:fastjson-1.2.75.jar" -h . JNIFuzzerTest1.java

gcc -fPIC -I"$JAVA_HOME/include" -I"$JAVA_HOME/include/linux" -shared -o libSunnyMilkFuzzer.so SunnyMilkFuzzer.c

$JAVA_HOME/bin/java -Djava.library.path=. -cp ".:fastjson-1.2.75.jar" JNIFuzzerTest1
