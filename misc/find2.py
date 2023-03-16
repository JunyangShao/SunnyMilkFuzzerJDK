import os

target_folder = "../fuzzer/"

included_headers = set()

for dirpath, dirnames, filenames in os.walk(target_folder):
    for filename in filenames:
        if filename.endswith(".cpp") or filename.endswith(".h"):
            filepath = os.path.join(dirpath, filename)
            with open(filepath, "r") as file:
                for line in file:
                    if line.startswith("#include"):
                        header = line.split()[1].strip()
                        included_headers.add(header)
                        if ("windows" in header):
                            print(filename)

for header in included_headers:
    print(header)
