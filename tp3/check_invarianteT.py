import re
import io

from more_itertools import rstrip

txt = ''

for i in open("t_invariante.txt", "r"):
    txt = txt + i

def invariantes(txt):
    line = [txt, 0]
    # print(line[0] + '\n')
    while(True) :
        # line = re.subn('T0(.*?)(Tu(.*?)(T2(.*?)T3(.*?)T5(.*?)T6(.*?)T7|T4(.*?)T5(.*?)T6)|T8(.*?)(T9(.*?)T10(.*?)T12(.*?)T13(.*?)T14|T11(.*?)T12(.*?)T13))',
	    # '\g<1>\g<3>\g<5>\g<6>\g<7>\g<8>\g<9>\g<10>\g<11>\g<13>\g<14>\g<15>\g<16>\g<17>\g<18>', line[0].rstrip())

        # line = re.subn('T0(.*?)(Tu(.*?)(T2(.*?)T3(.*?)T5(.*?)T6(.*?)T7|T4(.*?)T5(.*?)T6|T5(.*?)T6(.*?)T4|T5(.*?)T4(.*?)T6)|T8(.*?)(T9(.*?)T10(.*?)T12(.*?)T13(.*?)T14|T11(.*?)T12(.*?)T13|T12(.*?)T13(.*?)T11|T12(.*?)T11(.*?)T13))',
        # '\g<1>\g<3>\g<5>\g<6>\g<7>\g<8>\g<9>\g<10>\g<11>\g<12>\g<13>\g<14>\g<15>\g<17>\g<18>\g<19>\g<20>\g<21>\g<22>\g<23>\g<24>\g<25>\g<26>', line[0].rstrip())

        # line = re.subn('T0(.*?)T1(.*?)(T2(.*?)T3(.*?)|T4(.*?))T5(.*?)T6((.*?)T7)?',
        # '\g<1>\g<2>\g<4>\g<5>\g<6>\g<7>\g<9>', line[0].rstrip())

        # line = re.subn('(.*)T0(.*?)(?:Tu(.*?)(?:T5(.*?)(?:T6(.*?)T4|T4(.*?)T6)|T4(.*?)T5(.*?)T6|(?:T2(.*?)T3(.*?)T5(.*?)T6(.*?)|T5(.*?)T6(.*?)T2(.*?)T3(.*?))T7)|(?:T8(.*?)(?:T12(.*?)(?:T13(.*?)T11|T11(.*?)T13)|T11(.*?)T12(.*?)T13|(?:T9(.*?)T10(.*?)T12(.*?)T13(.*?)|T12(.*?)T13(.*?)T9(.*?)T10(.*?))T14)))',
        # '\g<1>\g<2>\g<3>\g<4>\g<5>\g<6>\g<7>\g<8>\g<9>\g<10>\g<11>\g<12>\g<13>\g<14>\g<15>\g<16>\g<17>\g<18>\g<19>\g<20>\g<21>\g<22>\g<23>\g<24>\g<25>\g<26>\g<27>\g<28>\g<29>\g<30>', line[0].rstrip())

        line = re.subn('T0(.*?)(?:Tu(.*?)(?:T5(.*?)(?:T6(.*?)T4|T4(.*?)T6)|T4(.*?)T5(.*?)T6|(?:T2(.*?)T3(.*?)T5(.*?)T6(.*?)|T5(.*?)T6(.*?)T2(.*?)T3(.*?))T7)|(?:T8(.*?)(?:T12(.*?)(?:T13(.*?)T11|T11(.*?)T13)|T11(.*?)T12(.*?)T13|(?:T9(.*?)T10(.*?)T12(.*?)T13(.*?)|T12(.*?)T13(.*?)T9(.*?)T10(.*?))T14)))',
        '\g<1>\g<2>\g<3>\g<4>\g<5>\g<6>\g<7>\g<8>\g<9>\g<10>\g<11>\g<12>\g<13>\g<14>\g<15>\g<16>\g<17>\g<18>\g<19>\g<20>\g<21>\g<22>\g<23>\g<24>\g<25>\g<26>\g<27>\g<28>\g<29>', line[0].rstrip())


        # print(line[0])
        # print('\n')

        if(line[1] == 0):
            break

    if(line[0] == ""):
        print("Test de invariantes exitoso")
    else:
        print("Error de invarianteT")
        print(line[0])

invariantes(txt)
