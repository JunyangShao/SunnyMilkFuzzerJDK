export JAVA_HOME=/home/junyangshao/Desktop/playground/jdk/build/linux-x86_64-server-release/jdk
$JAVA_HOME/bin/javac -cp ./javaparser-core-3.24.0.jar FuzzerFinder.java
$JAVA_HOME/bin/java -cp .:./javaparser-core-3.24.0.jar FuzzerFinder