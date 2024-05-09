Written by: Tomas Vasquez
CS356
Colorado State University
9/21/21

Files:
Starter.c:
	Encription decription program using block and stream cipher.
	Usage:
		./Starter [S or B] [inputfile] [outputfile] [keyfile] [D or E]
Makefile
README.txt

NOTES: 
1) To see contents of output in file use:
	xxd -b [outputfile]  
2) Inputfile must have no newline characyters at the end. Otherwise these will be counted when Xor Bits