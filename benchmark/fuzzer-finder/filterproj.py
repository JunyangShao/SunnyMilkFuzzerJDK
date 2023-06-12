import os
import re

def find_fuzzer_files(root_dir):
    for root, dirs, files in os.walk(root_dir):
        for file in files:
            if file.endswith('Fuzzer.java'):
                yield os.path.join(root, file)

def check_class_method(file_path):
    with open(file_path, 'r') as file:
        content = file.read()

    # Search for method signature
    method_pattern = re.compile(r"public static void fuzzerTestOneInput\(FuzzedDataProvider (\w+)\)")
    method_match = method_pattern.search(content)

    if method_match:
        method_variable = method_match.group(1)

        # Check if method variable is accessed only once
        access_pattern = re.compile(fr"{method_variable}\.consumeRemainingAsString\(\)")
        access_matches = access_pattern.findall(content)

        if len(access_matches) == 1:
            return True

    return False

def main():
    root_dir = '../oss-fuzz/projects/'
    for file_path in find_fuzzer_files(root_dir):
        if check_class_method(file_path):
            print(file_path)

if __name__ == '__main__':
    main()
