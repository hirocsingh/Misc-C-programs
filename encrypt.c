/*
        Name: Avinash Singh
        Date: 17 - March -2018
*/

#include<stdio.h>
#include<stdlib.h>
#include<ctype.h>
#include<string.h>


int main(int argc, char **argv){
        FILE *encrypt;
        FILE *decrypt;
        char ch1;
        char count=0;

        int i = 0;

        printf("%s\n", argv[1]);
        printf("%lu\n", strlen(argv[1]));

        char FILE_NAME[strlen(argv[1]) + 4];

        for(int i = 0; i < strlen(argv[1]); i++)
                printf("%c", argv[1][i]);

        for(int i = 0; i < strlen(argv[1]); i++)
                FILE_NAME[i] = argv[1][i];
        printf("\n");

        FILE_NAME[strlen(FILE_NAME)] = '.';
        FILE_NAME[strlen(FILE_NAME) + 1] = 'e';
        FILE_NAME[strlen(FILE_NAME) + 2] = 'n';
        FILE_NAME[strlen(FILE_NAME) + 3] = 'c';

        for(int i = 0; i < strlen(FILE_NAME); i++)
                printf("%c", FILE_NAME[i]);
        printf("\n");

        int shift = atoi(argv[2]);

        if(argc==1)
        {
                printf("Usage: $ ./encrypt <filenam> <Alphabet shift>\n");
                return 1;
        }

        encrypt = fopen(argv[1], "r");
        decrypt = fopen (FILE_NAME, "w+");

        if(!encrypt  && !decrypt)
        {
                printf("Error opening input filerd\n");
                return 2;
        }

        while((ch1=getc(encrypt))!=EOF)
        {
                if(ch1 != ' ' && !isdigit(ch1) && ch1 != '\n'){
                        if(isupper(ch1)){
                                ch1 = (char)(ch1 + shift);
                                if(ch1 > 'Z')
                                        ch1 = (char)(ch1 - 26);
                        }
                        else if(islower(ch1)){
                                ch1 = (char)(ch1 + shift);
                                if(ch1 > 'z')
                                        ch1 = (char)(ch1 - 26);
                        }
                }
                printf("%c", ch1);
                fprintf(decrypt, "%c", ch1);
        }


        fclose(encrypt);
        fclose(decrypt);

        printf("\n");


        return 0;
}
