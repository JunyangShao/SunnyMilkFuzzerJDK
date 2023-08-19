import os
import subprocess

def run_cmd(cmd):
    """Utility function to run a shell command and return its output."""
    return subprocess.check_output(cmd, stderr=subprocess.STDOUT).decode("utf-8")

def run_cmd_unchecked(cmd):
    """Utility function to run a shell command and return its output."""
    return subprocess.run(cmd, check=False, stderr=subprocess.STDOUT)

# Get the list of docker images
cmd = ["docker", "image", "ls", "--format", "{{.Repository}}"]
images = run_cmd(cmd).splitlines()

# Filter out the unwanted images
filtered_images = [img for img in images if img not in ["smfbase", "gcr.io/oss-fuzz-base/base-builder-jvm"]]

for image in filtered_images:
    # Create a container and start it, keeping it running in the background
    container_id = run_cmd(["docker", "run", "-d", image, "tail", "-f", "/dev/null"]).strip()

    try:
        # List files in /out/ directory of the container
        cmd = ["docker", "exec", container_id, "ls", "/out/"]
        files = run_cmd(cmd).splitlines()

        # Filter for *Fuzzer and *FuzzerSMF files
        fuzzers = [f for f in files if f.endswith("Fuzzer") or f.endswith("FuzzerSMF")]

        for fuzzer in fuzzers:
            filepath = f"/out/{fuzzer}"
            
            # Replace --trace=none with the new content inside the Docker container using sed
            replace_cmd = f"sed -i 's|--trace=none|{fuzzer}Corpus --trace=none --coverage_report={fuzzer}CovReport|' {filepath}"
            run_cmd(["docker", "exec", container_id, "bash", "-c", replace_cmd])
            replace_cmd = f"sed -i 's|/out/jazzer-smf-bin/jazzer|timeout 60 /out/jazzer-smf-bin/jazzer|' {filepath}"
            run_cmd(["docker", "exec", container_id, "bash", "-c", replace_cmd])
            replace_cmd = f"sed -i 's|/out/jazzer-orig-bin/jazzer|timeout 60 /out/jazzer-orig-bin/jazzer|' {filepath}"
            run_cmd(["docker", "exec", container_id, "bash", "-c", replace_cmd])

            # Execute the modified fuzzer file inside the container and redirect outputs
            execute_cmd = f"cd /out/ && mkdir -p {fuzzer}Corpus && ./{fuzzer} > {fuzzer}Output 2>&1"
            print(f"Executing {execute_cmd} for {image}...")
            run_cmd_unchecked(["docker", "exec", container_id, "bash", "-c", execute_cmd])
    
    finally:
        # Copy outputs and reports from the container to the local directory
        for fuzzer in fuzzers:
            # Ensure the target directory exists
            local_dir = f"../containerOut/{image}/{fuzzer}Results"
            os.makedirs(local_dir, exist_ok=True)

            filepath = f"/out/{fuzzer}"
            run_cmd(["docker", "cp", f"{container_id}:{filepath}Output", f"{local_dir}/{fuzzer}Output"])
            run_cmd(["docker", "cp", f"{container_id}:{filepath}CovReport", f"{local_dir}/{fuzzer}CovReport"])
            run_cmd(["docker", "cp", f"{container_id}:{filepath}Corpus", f"{local_dir}/{fuzzer}Corpus"])
        
        # Cleanup: stop and remove the container
        run_cmd(["docker", "rm", "-f", container_id])
    
    break

print("Finished processing all images and fuzzers.")
