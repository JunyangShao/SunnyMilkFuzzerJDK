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

# excluded projects
# these projects are manually checked to be malformed...
# image_names_exclude_manual = []
image_names_exclude_manual = ['angus-mail', 'antlr3-java', 'antlr4-java']

# copy the Dockerfiles of these projects, and modify them, by changing the dependency of 
# `gcr.io/oss-fuzz-base/base-builder-jvm` to `smfbase-clean`.
# and also copy build `\$this_dir/jazzer_driver --agent_path=\$this_dir/jazzer_agent_deploy.jar` to
# be `\$this_dir/jazzer`
# Then build them.
target_count = 0
for image_name in sorted(image_names_filtered):
    target_count += 1
    # only try 50 for now
    if target_count > 50:
        break
    if image_name in image_names_exclude_manual:
        continue
    if any(image_name in line.split()[0] for line in subprocess.check_output(["docker", "image", "ls"]).decode().splitlines()):
        continue
    os.chdir(f'../oss-fuzz/projects/{image_name}')
    # if the project is unchanged(does not contain Dockerfile_orig)
    # then move the Dockerfile to be Dockerfile_orig
    # similarly for build.sh
    if not os.path.exists('Dockerfile_orig'):
        os.rename('Dockerfile', 'Dockerfile_orig')
    if not os.path.exists('build_orig.sh'):
        os.rename('build.sh', 'build_orig.sh')
    # copy the Dockerfile_orig to be Dockerfile and similarly for build.sh
    subprocess.run(["cp", "Dockerfile_orig", "Dockerfile"], check=True)
    subprocess.run(["cp", "build_orig.sh", "build.sh"], check=True)
    # replace the dependency of `gcr.io/oss-fuzz-base/base-builder-jvm` to `smfbase`
    subprocess.run(["sed", "-i", "s/gcr.io\/oss-fuzz-base\/base-builder-jvm/smfbase/g", "Dockerfile"], check=True)
    # replace the build command
    subprocess.run(["sed", "-i", "s/\\\$this_dir\/jazzer_driver --agent_path=\\\$this_dir\/jazzer_agent_deploy\.jar/\/out\/jazzer-orig-bin\/jazzer \
                    --trace=none -close_fd_mask=3/g", "build.sh"], check=True)
    open('./Dockerfile', 'a').write('\nRUN FUZZING_LANGUAGE=java compile && cd /out/ && \
                                     for file in *Fuzzer; do [ -f "$file" ] && \
                                    cp "$file" "${file}SMF" && \
                                    sed -i -e \'s|LD_LIBRARY_PATH|JAVA_HOME="/out/jdk-smf" LD_LIBRARY_PATH|g\' \
                                        -e \'s|jdk-orig|jdk-smf|g\' -e \'s|jazzer-orig-bin|jazzer-smf-bin|g\' \
                                            "${file}SMF"; done\n\n\
                                    RUN cd /out/ && ./Checker')
    os.system('cat Dockerfile')
    # build the image
    # remove leftover if 
    subprocess.run(["docker", "build", "-t", image_name, "."], check=False)
    os.system('docker container prune -f && docker images -f "dangling=true" -q | xargs -r docker rmi')
    # restore the Dockerfile and build.sh
    subprocess.run(["cp", "Dockerfile_orig", "Dockerfile"], check=True)
    subprocess.run(["cp", "build_orig.sh", "build.sh"], check=True)
    # switch back to main directory
    os.chdir("../../../fuzzer-finder")
    print(f'built {image_name}')
    # exit(0)