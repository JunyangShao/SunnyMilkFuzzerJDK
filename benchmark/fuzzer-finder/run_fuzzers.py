import os
import subprocess

# image names
image_names = [
    "janino", "jersey", "json-java",
    "jsoup", "mysql-connector-j", "slf4j-api", "spring-cloud-sleuth-brave",
    "fastjson2", "guice", "java-example", "jettison", "json-sanitizer",
    "jul-to-slf4j", "osgi", "snakeyaml", "spring-data-mongodb",
    "gson", "jakarta-mail-api", "javaparser", "joda-time", "json-smart-v2",
    "log4j2", "retrofit", "spring-boot", "stringtemplate4"
]

image_names_excluded = ["jersey","mysql-connector-j", "guice", "self4j-api",
                        "spring-cloud-sleuth-brave", "java-example", "json-sanitizer",
                        "osgi", "snakeyaml", "spring-data-mongodb", "log4j2",
                        "retrofit", "spring-boot"]

# print(set(image_names) - set(image_names_excluded))
# exit(0)

# for each image name, run it
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
    running_time = 10
    for fuzzer in fuzzer_list:
        jazzer_cmd = f"sh run_jazzer.sh ../extracted/{image_name} {fuzzer} {class_path} {fuzzer}JazzerReport {running_time}"
        smf_cmd = f"sh run_smf.sh ../extracted/{image_name} {fuzzer} {class_path} {fuzzer}SMFReport {running_time}"
        print("jazzer cmd is :", jazzer_cmd)
        print("smf cmd is :", smf_cmd)
        # run jazzer
        os.system(jazzer_cmd)
        # run smf
        os.system(smf_cmd)