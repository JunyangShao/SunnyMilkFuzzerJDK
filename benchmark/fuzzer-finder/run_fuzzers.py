import os
import subprocess
import sys
import re

def parse_file(file_path):
    with open(file_path, 'r') as file:
        lines = file.readlines()

    pattern = r"#(\d+)\t"
    for line in reversed(lines):
        match = re.search(pattern, line)
        if match:
            return int(match.group(1))

    return 0

# image names
# image_names = [
#     "janino", "jersey", "json-java",
#     "jsoup", "mysql-connector-j", "slf4j-api", "spring-cloud-sleuth-brave",
#     "fastjson2", "guice", "java-example", "jettison", "json-sanitizer",
#     "jul-to-slf4j", "osgi", "snakeyaml", "spring-data-mongodb",
#     "gson", "jakarta-mail-api", "javaparser", "joda-time", "json-smart-v2",
#     "log4j2", "retrofit", "spring-boot", "stringtemplate4"
# ]
image_names = ["json-java"]
image_names_excluded = ["jersey","mysql-connector-j", "guice", "self4j-api",
                        "spring-cloud-sleuth-brave", "java-example", "json-sanitizer",
                        "osgi", "snakeyaml", "spring-data-mongodb", "log4j2",
                        "retrofit", "spring-boot", "slf4j-api", "fastjson2", "jul-to-slf4j",
                        "janino"]

mode = "print"
running_time = 10
suffix = ""
# mode 'all' means run fuzzers and analyze benchmark reports.
# mode 'benchmark' means only analyze benchmark reports.
# mode 'fuzzers' means only run fuzzers.
# mode 'print' means only print the project names.
if len(sys.argv) > 1:
    mode = sys.argv[1]
    if mode == "all" or mode == "fuzzers":
        if len(sys.argv) > 2:
            running_time = int(sys.argv[2])
    if mode == "benchmark":
        if len(sys.argv) > 2:
            suffix = sys.argv[2]
            

if mode == "print":
    print(set(image_names) - set(image_names_excluded))
    exit(0)

# for each image name, run it
if mode == "all" or mode == "fuzzers":
    for image_name in image_names:
        if image_name in image_names_excluded:
            continue
        # generate the class path, aggregate all .jar files and separate them with :
        # the jars are in ../extracted/{image_name}
        print("image name is :", image_name)
        class_path = ""
        first = True
        for file in os.listdir(f"../extracted/{image_name}"):
            if file.endswith(".jar"):
                # need only the name of the jar, not the path
                file = file.split("/")[-1]
                print("jar file is :", file)
                if first:
                    class_path += f"{file}"
                    first = False
                else:
                    class_path += f":{file}"
        print("classpath is :", class_path)
        # get the fuzzers, they are in ../extracted/{image_name} and ends with
        # SMF/Jazzer/Main
        fuzzer_list = set()
        for fuzzer in os.listdir(f"../extracted/{image_name}"):
            if fuzzer.endswith(".java"):
                fuzzer_list.add(fuzzer.replace("SMF.java", "").replace("Jazzer.java", "").replace("Main.java", ""))
        
        print("fuzzer list is :", fuzzer_list)
        # run the fuzzers, running run_jazzer.sh and run_smf.sh
        # running for 10s for now
        for fuzzer in fuzzer_list:
            jazzer_cmd = f"sh run_jazzer.sh ../extracted/{image_name} {fuzzer} {class_path} {fuzzer}JazzerReport {running_time}"
            smf_cmd = f"sh run_smf.sh ../extracted/{image_name} {fuzzer} {class_path} {fuzzer}SMFReport {running_time}"
            print("jazzer cmd is :", jazzer_cmd)
            print("smf cmd is :", smf_cmd)
            # run jazzer
            os.system(jazzer_cmd)
            # run smf
            os.system(smf_cmd)

# parse all the reports, they are in ../extracted/{image_name}/{fuzzer}[Jazzer|SMF]Report
if mode == "all" or mode == "benchmark":
    print("{:<20}{:<30}{:<20}{:<20}{:<20}{:<20}".format("Project Name", "Fuzzer", "Jazzer Insts", "SMF Insts", "Cov", "Thruput"))
    for image_name in image_names:
        if image_name in image_names_excluded:
            continue
        fuzzer_list = set()
        for fuzzer in os.listdir(f"../extracted/{image_name}"):
            if fuzzer.endswith(".java"):
                fuzzer_list.add(fuzzer.replace("SMF.java", "").replace("Jazzer.java", "").replace("Main.java", ""))
        for fuzzer in fuzzer_list:
            jazzer_stats = int(subprocess.run(["python", "parse_jacoco_report.py",\
                                    f"../extracted/{image_name}/{fuzzer}JazzerReport{suffix}/index.html"],\
                                    check=True, text=True, stdout=subprocess.PIPE).stdout.strip())
            smf_stats = int(subprocess.run(["python", "parse_jacoco_report.py",\
                                    f"../extracted/{image_name}/{fuzzer}SMFReport{suffix}/index.html"],\
                                    check=True, text=True, stdout=subprocess.PIPE).stdout.strip())
            jazzer_cnts = parse_file(f"../extracted/{image_name}/{fuzzer}JazzerReport{suffix}/jazzer-out")
            smf_cnts = parse_file(f"../extracted/{image_name}/{fuzzer}SMFReport{suffix}/smf-out")
            if (jazzer_stats == 0 and jazzer_cnts == 0):
                print("{:<20}{:<30}{:<20}{:<20}{:<20}{:<20}".format(image_name, fuzzer, jazzer_stats, smf_stats, "N/A", "N/A"))
            elif (jazzer_stats == 0):
                formatted_percentage_inst = "{:.2f}%".format((float(smf_stats)/jazzer_stats) * 100)
                print("{:<20}{:<30}{:<20}{:<20}{:<20}{:<20}".format(image_name, fuzzer, jazzer_stats, smf_stats,\
                                                                    "N/A",\
                                                                    formatted_percentage_inst))
            elif (jazzer_cnts == 0):
                formatted_percentage_thruput = "{:.2f}%".format((float(smf_cnts)/jazzer_cnts) * 100)
                print("{:<20}{:<30}{:<20}{:<20}{:<20}{:<20}".format(image_name, fuzzer, jazzer_stats, smf_stats,\
                                                                    formatted_percentage_thruput,\
                                                                    "N/A"))
            else:
                formatted_percentage_thruput = "{:.2f}%".format((float(smf_cnts)/jazzer_cnts) * 100)
                formatted_percentage_inst = "{:.2f}%".format((float(smf_stats)/jazzer_stats) * 100)
                print("{:<20}{:<30}{:<20}{:<20}{:<20}{:<20}".format(image_name, fuzzer, jazzer_stats, smf_stats,\
                                                                    formatted_percentage_inst,\
                                                                    formatted_percentage_thruput))          

if mode == "backup":
    suffix = "old"
    if len(sys.argv) > 2:
        suffix = sys.argv[2]
    for image_name in image_names:
        if image_name in image_names_excluded:
            continue
        fuzzer_list = set()
        for fuzzer in os.listdir(f"../extracted/{image_name}"):
            if fuzzer.endswith(".java"):
                fuzzer_list.add(fuzzer.replace("SMF.java", "").replace("Jazzer.java", "").replace("Main.java", ""))
        for fuzzer in fuzzer_list:
            os.system(f"mv ../extracted/{image_name}/{fuzzer}JazzerReport/ ../extracted/{image_name}/{fuzzer}JazzerReport{suffix}")
            os.system(f"mv ../extracted/{image_name}/{fuzzer}SMFReport/ ../extracted/{image_name}/{fuzzer}SMFReport{suffix}")