import argparse
import os
import subprocess

# Argument parser
parser = argparse.ArgumentParser()
parser.add_argument("target", help="Target project name")
args = parser.parse_args()

# Directory and Docker image name
dir_name = args.target
image_name = args.target

# cd into ./oss-fuzz/projects/s
os.chdir(f'../oss-fuzz/projects/{dir_name}')

# Docker build
subprocess.run(["docker", "build", "-t", image_name, "."], check=True)

# # Run the Docker container
# container = subprocess.run(["docker", "run", "-e", "FUZZING_LANGUAGE=java", "-d", image_name], check=True, text=True, stdout=subprocess.PIPE).stdout.strip()
# subprocess.run(["docker", "wait", container], check=True)

# # Get the output directory from the Docker container
# os.makedirs(f'../../../extracted/{dir_name}', exist_ok=False)
# subprocess.run(["docker", "cp", f"{container}:/out/", f'../../../tmp_jars/'], check=False)
# # Kill the container
# subprocess.run(["docker", "kill", container], check=False)
# # # Move all .jar files from ../../../tmp_jars/ to 
# # # ../../../extracted/{dir_name} and remove ../../../tmp_jars/
# subprocess.run(f"mv ../../../tmp_jars/*.jar ../../../extracted/{dir_name}/", shell=True, check=False)
# subprocess.run(["rm", "-rf", f'../../../tmp_jars/'], check=False)

# # Remove Docker container and image
# subprocess.run(["docker", "rm", "-f", container], check=False)
# subprocess.run(["docker", "rmi", image_name], check=False)
