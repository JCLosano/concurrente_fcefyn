import re
import io

from more_itertools import rstrip

txt = ''

for i in open("t_invariante.txt", "r"):
    txt = txt + i

def invariantes(txt):
    line = [txt, 0]
    line2 = [txt, 0]
    # print(line[0] + '\n')
    a = 0;
    b = 0;
    while(True) :

        line = re.subn('T5(.*?)T6(.*?)', '\g<1>\g<2>', line[0].rstrip())
        a = a + line[1]
        line2 = re.subn('T12(.*?)T13(.*?)', '\g<1>\g<2>', line2[0].rstrip())
        b = b + line2[1]

        if(line[1] == 0 and line2[1] == 0):
            break


    if(line[0] == ""):
        print("No termino la ejecucion")
    else:
        print("Nucleo1: ")
        print(a)
        print("Nucleo2: ")
        print(b)

invariantes(txt)
