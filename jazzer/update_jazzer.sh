JAZZER_DIR=/home/junyangshao/Desktop/playground/research/jazzer
export JAVA_HOME=/home/junyangshao/Desktop/playground/jdk/build/linux-x86_64-server-release/jdk
cd $JAZZER_DIR
bazel build jazzer_release
cd -
rm jazzer_release.tar.gz
cp /home/junyangshao/Desktop/playground/research/jazzer2/bazel-bin/jazzer_release.tar.gz .
rm jazzer
rm jazzer_standalone.jar
tar -xf jazzer_release.tar.gz
rm jazzer_release.tar.gz
