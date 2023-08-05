import os
import subprocess

# image names
image_names = subprocess.run(["python", "listproj.py"], check=True, text=True, stdout=subprocess.PIPE).stdout.strip()

# for each line, only the first element is the image name, get them out.
image_names = set(image_names.split())
print(sorted(image_names))
print(len(image_names))
print('---------------------')

# projects that failed to build, has bugs, etc.
# `index`, `getting-started` and `objects` are from .git/, it's an error value returned by listproj.py...
image_names_exclude = set(['index','getting-started','objects', 'hibernate-orm', 'apache-commons-beanutils']) 

# also exclude projects that specifically depends on a jdk version.
# we altered the jdk and it will be tricky to build these projects.
image_names_special_jdk = subprocess.run(["grep",'-r','jdk-', ("/home/junyangshao/Desktop/playground/"\
                                          "research/SunnyMilkFuzzerJDK/benchmark/oss-fuzz/projects")],\
                                           check=True, text=True, stdout=subprocess.PIPE).stdout.strip().split('\n')
image_names_special_jdk = set([x.split('/')[10] for x in image_names_special_jdk])
image_names_exclude = image_names_exclude.union(image_names_special_jdk)

image_names_special_jdk = subprocess.run(["grep",'-r','javac.src.version=17', ("/home/junyangshao/Desktop/playground/"\
                                          "research/SunnyMilkFuzzerJDK/benchmark/oss-fuzz/projects")],\
                                           check=True, text=True, stdout=subprocess.PIPE).stdout.strip().split('\n')
image_names_special_jdk = set([x.split('/')[10] for x in image_names_special_jdk])
image_names_exclude = image_names_exclude.union(image_names_special_jdk)

image_names_special_jdk = subprocess.run(["grep",'-r','javac.src.version=17', ("/home/junyangshao/Desktop/playground/"\
                                          "research/SunnyMilkFuzzerJDK/benchmark/oss-fuzz/projects")],\
                                           check=True, text=True, stdout=subprocess.PIPE).stdout.strip().split('\n')
image_names_special_jdk = set([x.split('/')[10] for x in image_names_special_jdk])
image_names_exclude = image_names_exclude.union(image_names_special_jdk)

image_names_special_jdk = subprocess.run(["grep",'-r','javac.target.version=17', ("/home/junyangshao/Desktop/playground/"\
                                          "research/SunnyMilkFuzzerJDK/benchmark/oss-fuzz/projects")],\
                                           check=True, text=True, stdout=subprocess.PIPE).stdout.strip().split('\n')
image_names_special_jdk = set([x.split('/')[10] for x in image_names_special_jdk])
image_names_exclude = image_names_exclude.union(image_names_special_jdk)

image_names = image_names.difference(image_names_exclude)

# filter again, only keep projects that are run with `jazzer_driver --agent_path`
# and are based on `gcr.io/oss-fuzz-base/base-builder-jvm`
# and does not change JAVA_HOME(used default)
image_names_filtered = set()
for image_name in sorted(image_names):
    os.chdir(f'../oss-fuzz/projects/{image_name}')
    normal_run_grep = subprocess.run(["grep",'-r','jazzer_driver --agent_path', '.'],\
                                       text=True, stdout=subprocess.PIPE).stdout
    normal_base_grep = subprocess.run(["grep",'-r','gcr.io/oss-fuzz-base/base-builder-jvm', '.'],\
                                        text=True, stdout=subprocess.PIPE).stdout
    change_java_home_grep = subprocess.run(["grep",'-r','JAVA_HOME', '.'],\
                                        text=True, stdout=subprocess.PIPE).stdout

    if normal_run_grep != '' and normal_base_grep != '' and change_java_home_grep == '':
        image_names_filtered.add(image_name)
    # subprocess.run(["docker", "build", "-t", image_name, "."], check=True)
    os.chdir("../../../fuzzer-finder")
    # exit(0)

print(sorted(image_names_filtered))
print(len(image_names_filtered))

# filter again, only keep projects that are run with `jazzer_driver --agent_path``
for image_name in sorted(image_names_filtered):
    os.chdir(f'../oss-fuzz/projects/{image_name}')
    subprocess.run(["docker", "build", "-t", image_name, "."], check=True)
    os.chdir("../../../fuzzer-finder")
    exit(0)