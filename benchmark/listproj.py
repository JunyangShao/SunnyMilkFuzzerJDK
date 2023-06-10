import os
import subprocess

# cd into ./oss-fuzz
os.chdir('./oss-fuzz')

# get the output of grep *Fuzzer.java
grep_output = subprocess.run(["grep", "-r", "-l", "Fuzzer.java"], capture_output=True, text=True).stdout

# Split the output into lines
lines = grep_output.splitlines()

# Get the second element of the path for each line, deduplicate and print
elements = set()
for line in lines:
    path_elements = line.split('/')
    if len(path_elements) >= 2:
        elements.add(path_elements[1])

for element in elements:
    print(element)