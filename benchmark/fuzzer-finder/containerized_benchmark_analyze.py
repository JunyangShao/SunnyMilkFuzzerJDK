import subprocess
import re

def run_command(command):
    process = subprocess.Popen(command, stdout=subprocess.PIPE, stderr=subprocess.PIPE, shell=True)
    stdout, stderr = process.communicate()
    return stdout.decode('utf-8')

def get_throughput(file_content):
    match = re.findall(r'exec/s: (\d+)', file_content)
    return int(match[-1]) if match else 0

# Get the list of active docker containers
container_list = run_command("docker ps --format '{{.ID}} {{.Image}}'")

# Print header
print('{:40s}{:40s}{:10s}{:10s}{:20s}{:20s}'.format('ImageName', 'FuzzerName', 'CovSMF', 'CovJazzer', 'CovSMF/Jazzer', 'ThruputSMF/Jazzer'))

for container in container_list.strip().split("\n"):
    container_id, image_name = container.split()

    # Install python3 in the container
    run_command(f"docker exec {container_id} sh -c 'apt-get update && apt-get install -y python3'")

    # Copy parse_text_coverage_report.py to container's /out/ directory
    run_command(f"docker cp ./parse_text_coverage_report.py {container_id}:/out/")

    # List files in /out/ of the container
    files = run_command(f"docker exec {container_id} ls /out/").split()

    # Identify pairs
    fuzzer_pairs = {}
    for file in files:
        if "FuzzerOutput" in file:
            base_name = file.replace("FuzzerOutput", "")
            if base_name in fuzzer_pairs:
                fuzzer_pairs[base_name]['jazzer'] = file
            else:
                fuzzer_pairs[base_name] = {'jazzer': file}
        elif "FuzzerSMFOutput" in file:
            base_name = file.replace("FuzzerSMFOutput", "")
            if base_name in fuzzer_pairs:
                fuzzer_pairs[base_name]['smf'] = file
            else:
                fuzzer_pairs[base_name] = {'smf': file}

    for fuzzer, output_files in fuzzer_pairs.items():
        smf_output = run_command(f"docker exec {container_id} cat /out/{output_files['smf']}")
        jazzer_output = run_command(f"docker exec {container_id} cat /out/{output_files['jazzer']}")

        smf_cov_text = run_command(f"docker exec {container_id} python3 /out/parse_text_coverage_report.py /out/{fuzzer}FuzzerSMFCovReport")
        smf_cov = int(smf_cov_text.split()[-1])
        jazzer_cov_text = run_command(f"docker exec {container_id} python3 /out/parse_text_coverage_report.py /out/{fuzzer}FuzzerCovReport")
        jazzer_cov = int(jazzer_cov_text.split()[-1])

        smf_throughput = get_throughput(smf_output)
        jazzer_throughput = get_throughput(jazzer_output)

        # Handle division by zero for ratio calculation
        cov_ratio = '{:7.2f}'.format(smf_cov / jazzer_cov) if jazzer_cov else '-'
        throughput_ratio = '{:7.2f}'.format(smf_throughput / jazzer_throughput) if jazzer_throughput else '-'

        # print(f"{ image_name:<20 }{ fuzzer:<20 }{ smf_cov:<20 }{ jazzer_cov:<20 }{ cov_ratio:<20 }{ throughput_ratio:<20 }")
        print('{:40s}{:40s}{:10s}{:10s}{:20s}{:20s}'.format(image_name, fuzzer, str(smf_cov), str(jazzer_cov), cov_ratio, throughput_ratio))