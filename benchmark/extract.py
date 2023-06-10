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
os.chdir(f'./oss-fuzz/projects/{dir_name}')

# Docker build
subprocess.run(["docker", "build", "-t", image_name, "."], check=True)

# Run the Docker container
container = subprocess.run(["docker", "run", "-e", "FUZZING_LANGUAGE=java", "-d", image_name], check=True, text=True, stdout=subprocess.PIPE).stdout.strip()
subprocess.run(["docker", "wait", container], check=True)

# Get the output directory from the Docker container
os.makedirs(f'../../../extracted/{dir_name}', exist_ok=True)
subprocess.run(["docker", "cp", f"{container}:/out", f'../../../extracted/{dir_name}'], check=True)

# Remove Docker container and image
subprocess.run(["docker", "rm", "-f", container], check=True)
subprocess.run(["docker", "rmi", image_name], check=True)
