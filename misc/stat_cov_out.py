x = {}
with open('cov_out','r') as f:
    for l in f:
        if l not in x:
            x[l] = 0
        else:
            x[l] += 1
    f.close()

for i in x:
    print(i, x[i])
