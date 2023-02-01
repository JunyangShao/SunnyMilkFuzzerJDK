x = set()
with open('template_include_list','r') as f:
    for l in f:
        x.add(l)
    f.close()

with open('jni_include_list','r') as f:
    for l in f:
        if l in x:
            print(l)
    f.close()
