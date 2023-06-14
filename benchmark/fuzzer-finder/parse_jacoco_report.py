import argparse
from bs4 import BeautifulSoup

def parse_html(file_path):
    with open(file_path, 'r') as file:
        content = file.read()

    soup = BeautifulSoup(content, 'html.parser')
    
    table = soup.find('table', {'id': 'coveragetable'})
    if table is None:
        return "Table not found"
        
    tfoot = table.find('tfoot')
    if tfoot is None:
        return "Table footer not found"
        
    total_line = tfoot.find('tr')
    if total_line is None:
        return "Total line not found"
    
    values = [td.get_text() for td in total_line.find_all('td')]
    return values

parser = argparse.ArgumentParser(description='Parse a HTML file and return the "Total" line.')
parser.add_argument('file_path', help='The path to the HTML file to be parsed.')

args = parser.parse_args()

total = parse_html(args.file_path)
total_inst = total[1].split()
total_inst_covered = int(total_inst[2].replace(',','')) - int(total_inst[0].replace(',',''))
print(total_inst_covered)