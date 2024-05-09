//#include<iostream>
//#include <string>
//#include <cstring>
//using namespace std;
#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>


#define BLOCK_SIZE 16 //16 bytes <-> 128 bits.


void writeToFile(char *forOutPut, char* fileName, int n){
	FILE *f = fopen(fileName, "w");
	fwrite(forOutPut, sizeof(char), n, f);
	fclose(f);

}
void streamEncryptDecrypt( char *inputData, long inputSize,int keySize, char* keyFile, char *output){
        int i=0;
        char forOutPut[inputSize];
        while(i < inputSize ){
                forOutPut[i] = inputData[i] ^ keyFile[i%keySize];
        i++;
        }

writeToFile(forOutPut, output, inputSize);
}

void swappingAlgorithm(char *forOutPut, char* keyFile,int size){
        int start = 0;
        int end = size-1;
        while(start < end){
		int test = ((int)keyFile[start%16]) % 2;
                if(test){
			char temp = forOutPut[start];
                        forOutPut[start] = forOutPut[end];
                        forOutPut[end] = temp;
                        ++start;
                        --end;
                }
                else{
                        ++start;
                }

        }
}
void removePaddng(int *size,char * output, int paddingFlag, char* cleanOut){
	int Newsize = *size - paddingFlag;
	
	*size = Newsize;
	for(int i=0; i<Newsize;i++){

		cleanOut[i] = output[i];
	

	}
}
void pad(char * inputData,long size , int numberOfBlocks, char blocks[][BLOCK_SIZE]){
	int numberOfElementsToFill = size % BLOCK_SIZE;
	int fillingIndex = size- numberOfElementsToFill;
	//need to allocate first few elements
	for (int i = 0; i< BLOCK_SIZE; i++){
		if( i < numberOfElementsToFill){
			blocks[numberOfBlocks][i] = inputData[fillingIndex++];
		}
		else{
			//may need to cast this
			blocks[numberOfBlocks][i] = (char)129;	
		}
	}
	
}

void allocateFullBlocks(char * inputData,char blocks[][BLOCK_SIZE], int numberOfBlocks){
        int counter = 0;
	int running_data_index =0;
	while(counter < numberOfBlocks){
		for (int i =0; i <BLOCK_SIZE; i++ ){
			blocks[counter][i] = inputData[running_data_index];
			++running_data_index;
		}
		++counter;

	}	

}

int splitInBlocks(char * inputData, long size, char blocks[][BLOCK_SIZE], int numberOfBlocks){
    //determine if blocks can be split evenly
   // int FullBlocks = numberOfBlocks/BLOCK_SIZE;
	allocateFullBlocks(inputData, blocks, numberOfBlocks);
    if(size % BLOCK_SIZE == 0){
	return 1;
    }else{
	pad(inputData, size,numberOfBlocks, blocks);
	return 0; 
    }

}
void blockDecryption(char *inputData, long size, char* keyFile, char *outputFile){
	
	int numberOfBlocks = size/BLOCK_SIZE;
        //leave room for one extra block incase of partial
        char blocks[numberOfBlocks+1][BLOCK_SIZE];
	int n;
	int paddingFlag = 0;
	for (int i=0; i< size; i++){
		if((int)inputData[i] < 0){
		paddingFlag++ ;
	}
	}
	if(splitInBlocks(inputData, size, blocks, numberOfBlocks)){
                n=numberOfBlocks * BLOCK_SIZE;
        
	}
        else{
                n= (numberOfBlocks+1) * BLOCK_SIZE;
	}
	char forOutPut[n];
	int running_index = 0;
        for (int i =0; i< n/BLOCK_SIZE; i++){
                 for (int j =0; j< BLOCK_SIZE; j++){
                        forOutPut[running_index] =  blocks[i][j];
			running_index++;
                }

        }	
	swappingAlgorithm(forOutPut,keyFile,  n);
	char output[n];
	for (int i =0; i<n; i++){
                output[i]= forOutPut[i] ^ keyFile[i%BLOCK_SIZE];

        }
	
	if(!paddingFlag){
	writeToFile(output, outputFile, n);
	}
	else{
	char cleanOut1[n-paddingFlag]; 
	removePaddng(&n, output, paddingFlag, cleanOut1);
	writeToFile(cleanOut1, outputFile, n);
	}


}
void blockEncryption(char *inputData, long size, char* keyFile, char *output ){
//	int numberOfBlocks =(size/BLOCK_SIZE)*BLOCK_SIZE;		//number of full blocks.
	int numberOfBlocks = size/BLOCK_SIZE;
	//leave room for one extra block incase of partial
	char blocks[numberOfBlocks+1][BLOCK_SIZE];
	int n;
	if(splitInBlocks(inputData, size, blocks, numberOfBlocks)){
		n=numberOfBlocks * BLOCK_SIZE;
	}
	else
		n= (numberOfBlocks+1) * BLOCK_SIZE;
	char forOutPut[n];
	int running_index = 0;
	for (int i =0; i< n/BLOCK_SIZE; i++){
		 for (int j =0; j< BLOCK_SIZE; j++){
			//FOR TESTING THE SWAP I AM DUMMINING OUT THE OUTPUT ARRAY
			forOutPut[running_index] = keyFile[j] ^ blocks[i][j];
			running_index++;
		}
			
	}	
	swappingAlgorithm(forOutPut, keyFile, n);
	writeToFile(forOutPut, output, n);
}
    


int EncryptDecrypthandler(char toggler, char mode){
    if(toggler == 'B'){
        if(mode == 'E'){
            //call Block mode Encrypt
            return 1;
        }
        else
            return 2;
            //call Block mode Decrypt
    }
    else
        return 3;
        //Call stream handler

}
//check that a valid encription or decryption mode was given
 char validMode(char mode[]){
    if( (mode[0] ==  'E' || mode[0] ==  'D') && !mode[1]){  //|| !toggler.compare("S")
       }
       else{
        printf("Incorrect use please give a \"D\" or and \"E\" \n");
        exit(1);
       }
       return mode[0];
 }
//helper function to get the size of the file for the key to be read
void getFileSize(FILE *filePointer, long *size){
    fseek(filePointer, 0, SEEK_END);
    *size = ftell(filePointer);
    fseek(filePointer, 0, SEEK_SET);
}
void openFile(char* fileName, FILE **filePointer){
	if(access(fileName, F_OK) != 0){
        printf("File with name %s does not exist\n", fileName);
        exit(1);
    }
    *filePointer = fopen(fileName, "rb");
}
void readFile(FILE *filePointer, long size, char output[]){
    //char key[*size];
    //char temp;
    for(int i =0; i< size; i++){
        output[i] = (char)(int)fgetc(filePointer);
	}

    fclose(filePointer);

}
//check if the input file is valid
char * validInputFile(char file[]){
    if(access(file, F_OK) != 0){
        printf("File with name %s does not exist\n", file);
        exit(1);
    }
    return file;
}
//check if given a valid cipher mode
char validCipher(char toggler[]){
    if((toggler[0] ==  'B' || toggler[0] ==  'S') && !toggler[1]){  //|| !toggler.compare("S")
       }
       else{
        printf("Incorrect use please give a \"B\" or and \"S\" \n");
        exit(1);
       }
       return toggler[0];
}

int main(int argc, char * argv[]){
    if( argc != 6){
        printf("please give 5 arguments\n");
       exit(0);
   } 
    
    char toggler = validCipher(argv[1]);
    char *inputFile = validInputFile(argv[2]);
    //Instructions do not indicate to check the validity of the outputfile
    char * outputFileName = argv[3];
    //Instructions do not indicate to check the validity of the key
    FILE *keyFilePointer;
 	openFile(argv[4], &keyFilePointer);
	long sizeOfKeyFile;
	getFileSize(keyFilePointer, &sizeOfKeyFile);
 	char keyFile[sizeOfKeyFile];
	readFile(keyFilePointer, sizeOfKeyFile, keyFile);

	char mode = validMode(argv[5]);
	FILE *inputFilePointer;
	openFile(inputFile, &inputFilePointer);
    	long sizeOfInputFile;
	getFileSize(inputFilePointer, &sizeOfInputFile);
	char inputData[sizeOfInputFile];
	readFile(inputFilePointer, sizeOfInputFile, inputData);
	//NEDD TO Implement
    //isInpuFileEmpty(inputData);
    int task = EncryptDecrypthandler(toggler, mode);

    switch (task){
    case 1:
       blockEncryption(inputData, sizeOfInputFile, keyFile, outputFileName);
        break;
    case 2:
	blockDecryption(inputData, sizeOfInputFile, keyFile, outputFileName);
        break;
    case 3:
	streamEncryptDecrypt( inputData, sizeOfInputFile,sizeOfKeyFile, keyFile, outputFileName);
        break;
    }


}
