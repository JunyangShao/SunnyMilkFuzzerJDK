import os
import shutil
import sys

def copy_dir(src, dst):
    # Check if the destination directory exists. If not, create it.
    if not os.path.exists(dst):
        os.makedirs(dst)

    for item in os.listdir(src):
        s = os.path.join(src, item)
        d = os.path.join(dst, item)
        if os.path.isdir(s):
            copy_dir(s, d) # If item is a directory, recursively call the function.
        else:
            shutil.copy2(s, d) # If item is a file, copy it preserving metadata.

# Call the function with source and destination paths.
copy_dir(sys.argv[1], sys.argv[2])