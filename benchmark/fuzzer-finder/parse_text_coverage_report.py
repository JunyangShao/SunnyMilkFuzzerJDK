import os
import sys
import re

def parse_text_coverage_report(filename):
    start_token = "Line coverage:\n"
    end_token = "Missed lines:\n"
    data_line_start = False
    total_int1 = 0

    with open(filename, 'r') as file:
        for line in file:
            # Start reading after finding the start token
            if start_token in line:
                data_line_start = True
                continue
            # Stop reading after finding the end token
            if end_token in line:
                data_line_start = False
                break
            # If we are in the data block, process the line
            if data_line_start:
                # Find and accumulate integers $int1 in "java: $int1/$int2"
                match = re.search(r': (\d+)/', line)
                if match:
                    int1 = int(match.group(1))  # match.group(1) gives us the first captured group, which is $int1
                    total_int1 += int1
                    if int1 > 0:
                        print(line, end='')

    return total_int1

if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Usage: python parse_text_coverage_report.py <filename>")
        sys.exit(1)
    filename = sys.argv[1]
    if not os.path.isfile(filename):
        print("File not found: " + filename)
        sys.exit(1)
    print(parse_text_coverage_report(filename))
    sys.exit(0)
