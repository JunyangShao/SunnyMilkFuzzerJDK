Please build the jdk first:

`./make_jvm`

Please ignore the `./misc` directory, it's for my debugging only.

`./fuzzer`	libFuzzer codes

`./jazzer`	jazzer related codes, like testing/benchmarking, also contains the Dacapo benchmark for both jazzer/original/smf.

`./src`		The SMF implementation(just a wrapper to libFuzzer)

`./SunnyMilkJDK`	The jdk for SMF.

`./tests`		Simple tests to verify SMF's correctness and performance.

`./benchmark`	OSS Fuzz benchmark targets. For both Jazzer and SMF.
