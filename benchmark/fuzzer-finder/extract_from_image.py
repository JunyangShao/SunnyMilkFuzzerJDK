import os
import subprocess

# image names
image_names = \
            "janino        jersey     json-java       \
            jsoup         mysql-connector-j  slf4j-api    spring-cloud-sleuth-brave\
            fastjson2                     guice             java-example  jettison   json-sanitizer  \
            jul-to-slf4j  osgi               snakeyaml    spring-data-mongodb\
            gson                          jakarta-mail-api  javaparser    joda-time  json-smart-v2   \
            log4j2        retrofit           spring-boot  stringtemplate4"


# for each line, only the first element is the image name, get them out.
image_names = image_names.split()

# for each image name, run it
for image_name in image_names:
    os.chdir(f'../oss-fuzz/projects/{image_name}')
    container = subprocess.run(["docker", "run", "-e", "FUZZING_LANGUAGE=java", "-d", image_name], check=True, text=True, stdout=subprocess.PIPE).stdout.strip()
    subprocess.run(["docker", "wait", container], check=True)
    print("My current container is: ", container)
    os.system(f"docker cp {container}:/out/ .")
    # get all .jar or .war to ../../../extracted//{image_name}
    os.system(f"mv out/*.jar ../../../extracted/{image_name}/")
    os.system(f"mv out/*.war ../../../extracted/{image_name}/")
    # remove out
    os.system("rm -rf out")
    os.system("docker container prune -f")
    os.chdir("../../../fuzzer-finder")