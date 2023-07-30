# Benchmarking script for SunnyMilkFuzzer
import os
import sys
import subprocess
import re

if __name__ == "__main__":
    # Path of SMF instrumentation-enabled JDK
    smf_jdk_path = "/home/junyangshao/Desktop/playground/research" \
                   "/SunnyMilkFuzzerJDK/SunnyMilkJDK/build/linux-x86_64-server-release/jdk"
    # Path for the original JDK.
    original_jdk_path = "/home/junyangshao/Desktop/playground/jdk/build/linux-x86_64-server-release/jdk"

    # dacapo benchmark targets
    dacapo_targets = ["avrora", "fop", "h2",
                      "jython", "luindex", "lusearch", "pmd", "sunflow",
                      "xalan"]

    dacapo_time_orig = {}
    dacapo_time_smf = {}
    dacapo_time_jazzer = {}
    for target in dacapo_targets:
        dacapo_time_jazzer[target] = 0

    # SMF and Original

    # Execute dacapo for 3 times, for each benchmark, and record the time average.
    for target in dacapo_targets:
        # java -jar dacapo [target]
        smf_java = smf_jdk_path + "/bin/java"
        orig_java = original_jdk_path + "/bin/java"
        smf_cmd = "export JAVA_HOME=" + smf_jdk_path + " && " \
                  + smf_java + " -XX:TieredStopAtLevel=1" + " -jar dacapo-9.12-MR1-bach.jar " + target

        orig_cmd = "export JAVA_HOME=" + original_jdk_path + " && " \
                  + orig_java + " -XX:TieredStopAtLevel=1" + " -jar dacapo-9.12-MR1-bach.jar " + target
        # print("Benchmarking " + target + "...")
        smf_time_total = 0.0
        orig_time_total = 0.0
        for i in range(3):
            # Execute smf_cmd and orig_cmd, parse their output
            # the time is in the last line, in the format "[1-9][0-9]* msec"
            smf_output = subprocess.run(smf_cmd, stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True, shell=True).stderr
            orig_output = subprocess.run(orig_cmd, stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True, shell=True).stderr
            smf_time = int(smf_output.split("\n")[-2].split(" ")[-3].strip())
            orig_time = int(orig_output.split("\n")[-2].split(" ")[-3].strip())
            smf_time_total += smf_time
            orig_time_total += orig_time
        # print("SMF time: %.2f" % (smf_time_total / 3.0) + "ms")
        # print("Original time: %.2f" % (orig_time_total / 3.0) + "ms")
        # print("SMF slowdown: %.2f" % (smf_time_total / orig_time_total))
        dacapo_time_orig[target] = (orig_time_total)
        dacapo_time_smf[target] = (smf_time_total)
    
    # Jazzer

    # Run the shell script and get its output, including stdout and stderr
    jazzer_cmd = "export JAVA_HOME=" + original_jdk_path + " && " + \
                 "$JAVA_HOME/bin/javac -cp jazzer_standalone.jar DacapoFuzzer.java && " + \
                 "./jazzer --cp=./ --trace=none --target_class=DacapoFuzzer && " + \
                 "./clean.sh"
    for i in range(3):
        result = subprocess.run(jazzer_cmd, capture_output=True, text=True, shell=True)

        # Combine stdout and stderr
        output = result.stdout + result.stderr

        # Initialize the current benchmark name to None
        current_bench_name = None

        # Iterate over the output lines
        for line in output.splitlines():
            # print(line)
            # Match the starting line of a benchmark
            tmp = re.search(r"DaCapo 9.12-MR1 (\w+) starting", line)
            if tmp:
                start_match = re.match(r"DaCapo 9.12-MR1 (\w+) starting", tmp.group())
                current_bench_name = start_match.group(1)
                # print("Starting benchmark " + current_bench_name + "...")
                continue

            # Match the "took x ms" line
            tmp = re.search(r"took (\d+) ms", line)
            if tmp and current_bench_name is not None:
                took_match = re.match(r"took (\d+) ms", tmp.group())
                x = int(took_match.group(1))
                # print("Instrumentation " + current_bench_name + " took " + str(x) + "ms")
                dacapo_time_jazzer[current_bench_name] -= x
                continue

            # Match the passed line
            tmp = re.search(r"===== DaCapo 9.12-MR1 (\w+) PASSED in (\d+) msec =====", line)
            if tmp:
                print(tmp)
                passed_match = re.match(r"===== DaCapo 9.12-MR1 (\w+) PASSED in (\d+) msec =====", tmp.group())
                bench_name = passed_match.group(1)
                x = int(passed_match.group(2))
                # print("Benchmark " + bench_name + " passed in " + str(x) + "ms")
                dacapo_time_jazzer[bench_name] += x
                current_bench_name = None

    # Print the final map
    for i in dacapo_targets:
        print(dacapo_time_orig[i], end=" ")
    print()

    for i in dacapo_targets:
        print(dacapo_time_smf[i], end=" ")
    print()

    for i in dacapo_targets:
        print(dacapo_time_jazzer[i], end=" ")
    print()
