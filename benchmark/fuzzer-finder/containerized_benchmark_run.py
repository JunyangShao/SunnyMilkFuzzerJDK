# import os
# import subprocess

# def run_cmd(cmd):
#     """Utility function to run a shell command and return its output."""
#     return subprocess.check_output(cmd, stderr=subprocess.STDOUT).decode("utf-8")

# def run_cmd_unchecked(cmd):
#     """Utility function to run a shell command and return its output."""
#     return subprocess.run(cmd, check=False, stderr=subprocess.STDOUT)

# # Get the list of docker images
# cmd = ["docker", "image", "ls", "--format", "{{.Repository}}"]
# images = run_cmd(cmd).splitlines()

# # Filter out the unwanted images
# filtered_images = [img for img in images if img not in ["smfbase", "gcr.io/oss-fuzz-base/base-builder-jvm"]]

# for image in filtered_images:
#     # Create a container and start it, keeping it running in the background
#     container_id = run_cmd(["docker", "run", "-d", image, "tail", "-f", "/dev/null"]).strip()

#     # List files in /out/ directory of the container
#     cmd = ["docker", "exec", container_id, "ls", "/out/"]
#     files = run_cmd(cmd).splitlines()

#     # Filter for *Fuzzer and *FuzzerSMF files
#     fuzzers = [f for f in files if f.endswith("Fuzzer") or f.endswith("FuzzerSMF")]

#     for fuzzer in fuzzers:
#         filepath = f"/out/{fuzzer}"
        
#         # Replace --trace=none with the new content inside the Docker container using sed
#         replace_cmd = f"sed -i 's|--trace=none|{fuzzer}Corpus --trace=none --coverage_report={fuzzer}CovReport|' {filepath}"
#         run_cmd(["docker", "exec", container_id, "bash", "-c", replace_cmd])
#         replace_cmd = f"sed -i 's|/out/jazzer-smf-bin/jazzer|timeout 60 /out/jazzer-smf-bin/jazzer|' {filepath}"
#         run_cmd(["docker", "exec", container_id, "bash", "-c", replace_cmd])
#         replace_cmd = f"sed -i 's|/out/jazzer-orig-bin/jazzer|timeout 60 /out/jazzer-orig-bin/jazzer|' {filepath}"
#         run_cmd(["docker", "exec", container_id, "bash", "-c", replace_cmd])

#         # Execute the modified fuzzer file inside the container and redirect outputs
#         execute_cmd = f"cd /out/ && mkdir -p {fuzzer}Corpus && ./{fuzzer} > {fuzzer}Output 2>&1"
#         print(f"Executing {execute_cmd} for {image}...")
#         run_cmd_unchecked(["docker", "exec", container_id, "bash", "-c", execute_cmd])

#         # Copy outputs and reports from the container to the local directory
#         # Ensure the target directory exists
#         local_dir = f"../containerOut/{image}/{fuzzer}Results"
#         os.makedirs(local_dir, exist_ok=True)

#         filepath = f"/out/{fuzzer}"
#         run_cmd(["docker", "cp", f"{container_id}:{filepath}Output", f"{local_dir}/{fuzzer}Output"])
#         run_cmd(["docker", "cp", f"{container_id}:{filepath}CovReport", f"{local_dir}/{fuzzer}CovReport"])
#         run_cmd(["docker", "cp", f"{container_id}:{filepath}Corpus", f"{local_dir}/{fuzzer}Corpus"])
        
#     # Cleanup: stop and remove the container
#     run_cmd(["docker", "rm", "-f", container_id])
    
#     # break

# print("Finished processing all images and fuzzers.")




import os
import subprocess
import concurrent.futures

def run_cmd(cmd):
    """Utility function to run a shell command and return its output."""
    return subprocess.check_output(cmd, stderr=subprocess.STDOUT).decode("utf-8")

def run_cmd_unchecked(cmd):
    """Utility function to run a shell command and return its output."""
    return subprocess.run(cmd, check=False, stderr=subprocess.STDOUT)

def process_fuzzer(image, container_id, fuzzer):
    filepath = f"/out/{fuzzer}"
    
    # Replace parts of the fuzzer file
    for replace_cmd in [
        f"sed -i 's|--trace=none|{fuzzer}Corpus --trace=none --coverage_report={fuzzer}CovReport --coverage_dump={fuzzer}Dump.exec|' {filepath}",
        f"sed -i 's|/out/jazzer-smf-bin/jazzer|timeout 360 /out/jazzer-smf-bin/jazzer|' {filepath}",
        f"sed -i 's|/out/jazzer-orig-bin/jazzer|timeout 360 /out/jazzer-orig-bin/jazzer|' {filepath}"
    ]:
        run_cmd(["docker", "exec", container_id, "bash", "-c", replace_cmd])

    # Execute the modified fuzzer file inside the container and redirect outputs
    execute_cmd = f"cd /out/ && mkdir -p {fuzzer}Corpus && ./{fuzzer} > {fuzzer}Output 2>&1"
    print(f"Executing {execute_cmd} for {image}...")
    run_cmd_unchecked(["docker", "exec", container_id, "bash", "-c", execute_cmd])

    # Copy outputs and reports from the container to the local directory
    local_dir = f"../containerOut/{image}/{fuzzer}Results"
    os.makedirs(local_dir, exist_ok=True)
    for suffix in ["Output", "CovReport", "Corpus"]:
        run_cmd(["docker", "cp", f"{container_id}:{filepath}{suffix}", f"{local_dir}/{fuzzer}{suffix}"])

def process_image(image, starting_cpu, ending_cpu):
    # Create a container and start it with cpu bindings, keeping it running in the background
    container_id = run_cmd(["docker", "run", "-d", "--cpuset-cpus", f"{starting_cpu}-{ending_cpu}", image, "tail", "-f", "/dev/null"]).strip()

    # List files in /out/ directory of the container
    cmd = ["docker", "exec", container_id, "ls", "/out/"]
    files = run_cmd(cmd).splitlines()

    # Filter for *Fuzzer and *FuzzerSMF files
    fuzzers = [f for f in files if f.endswith("Fuzzer") or f.endswith("FuzzerSMF")]

    with concurrent.futures.ThreadPoolExecutor() as executor:
        futures = [executor.submit(process_fuzzer, image, container_id, fuzzer) for fuzzer in fuzzers]
        for future in concurrent.futures.as_completed(futures):
            future.result()

    # Cleanup: stop and remove the container
    # run_cmd(["docker", "rm", "-f", container_id])

def get_fuzzer_counts(images):
    # Go over `images` and count the number of fuzzers for each image, return a map of it
    fuzzer_counts = {}
    for image in images:
        container_id = run_cmd(["docker", "run", "-d", image, "tail", "-f", "/dev/null"]).strip()
        cmd = ["docker", "exec", container_id, "ls", "/out/"]
        files = run_cmd(cmd).splitlines()
        fuzzers = [f for f in files if f.endswith("Fuzzer") or f.endswith("FuzzerSMF")]
        fuzzer_counts[image] = len(fuzzers)
        run_cmd(["docker", "rm", "-f", container_id])
    
    return fuzzer_counts

# Get the list of docker images
cmd = ["docker", "image", "ls", "--format", "{{.Repository}}"]
images = run_cmd(cmd).splitlines()
# only try 10 for now
images = images[:10]

# Filter out the unwanted images
filtered_images = [img for img in images if img not in ["smfbase", "gcr.io/oss-fuzz-base/base-builder-jvm"]]

core_number = 12

# Get the number of fuzzers for each image
# If there is an image with fuzzer count > core_number, then skip it.
fuzzer_counts = get_fuzzer_counts(filtered_images)
for image, count in fuzzer_counts.items():
    if count > core_number:
        print(f"Skipping {image} with {count} fuzzers.")
        filtered_images.remove(image)
        fuzzer_counts.pop(image)

# Parallelizing the loop over images
with concurrent.futures.ThreadPoolExecutor() as executor:
    # Batch it. Go over filtered_images, and infer the batch by allowing no more than core_number fuzzers per batch.
    while len(filtered_images) > 0:
        image_batch = []
        used_core = 0
        for image in filtered_images.copy():  # Use a copy to avoid modifying the original list during iteration
            # The filter above should have removed all images with fuzzer count > core_number
            if used_core + fuzzer_counts[image] > core_number:
                break
            filtered_images.remove(image)
            image_batch.append((image, used_core, used_core + fuzzer_counts[image] - 1))
            used_core += fuzzer_counts[image]
            if used_core >= core_number:
                break

        futures = [executor.submit(process_image, image, starting_cpu, ending_cpu) for image, starting_cpu, ending_cpu in image_batch]
        for future in concurrent.futures.as_completed(futures):
            future.result()

print("Finished processing all images and fuzzers.")
