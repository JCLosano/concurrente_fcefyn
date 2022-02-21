f = open("./H.txt", "r")
lines = f.readlines()

numbers = []

for line in range(len(lines)):
    numbers.append([])
    numbers[line] = lines[line].split(" ")
    numbers[line][-1] = (numbers[line][-1])[0]   
    print(numbers[line])

print("\n")

inverse = [[]]*len(numbers[0])
for i in range(len(inverse)):
    inverse[i] = ['0']*len(numbers)

for j in range(len(inverse)):
    for i in range(len(inverse[j])):
        inverse[j][i] = numbers[i][j]
    print(inverse[j])

wf = open("./Hinv.txt", "w")
for j in range(len(inverse)):
    for i in range(len(inverse[j])):
        wf.write(inverse[j][i] + " ")
    wf.write("\n")


