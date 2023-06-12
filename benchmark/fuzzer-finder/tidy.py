import os
import shutil

# For each subdirectory in ./extracted
for dir_name in os.listdir('../extracted'):
    dir_path = os.path.join('../extracted', dir_name)

    # Check if it is a directory
    if os.path.isdir(dir_path):

        # Move all files from ./extracted/x/out to ./extracted/x
        out_path = os.path.join(dir_path, 'out')
        if os.path.exists(out_path):
            for file_name in os.listdir(out_path):
                shutil.move(os.path.join(out_path, file_name), dir_path)

            # Remove the ./extracted/x/out directory
            shutil.rmtree(out_path)

# For each subdirectory in ./extracted
for dir_name in os.listdir('../extracted'):
    dir_path = os.path.join('../extracted', dir_name)

    # Check if it is a directory
    if os.path.isdir(dir_path):
        # Remove the ./extracted/x/open-jdk-17 directory if it exists
        folder_path = os.path.join(dir_path, 'open-jdk-17')
        if os.path.exists(folder_path):
            shutil.rmtree(folder_path)

        # Remove the ./extracted/x/llvm-symbolizer file if it exists
        file_path = os.path.join(dir_path, 'llvm-symbolizer')
        if os.path.exists(file_path):
            os.remove(file_path)