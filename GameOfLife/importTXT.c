
#include "importTXT.h"


void txtToTab(int h,int l,char tab[h][l]){
    FILE *fichier = fopen("motif.txt", "r");//ouvre motif.txt
    char ligne[256];//fait en sorte que la ligne puisse contenir jusqu'a 256 caractère 
    int comp = 0;
    while (fgets(ligne,sizeof(ligne),fichier)!=NULL && comp<l) {
        int i = 0;
        while (ligne[i]!='\0' && ligne[i]!='\n' && i<h) {//mets des caractère tant que ce n'est pas un saut de ligne ou une fin de chaine 
            tab[comp+10][i+20] = ligne[i];//mets le caractères dans le tabela uet le décale de 10 en hauteur et 20 en largeur 
            i++;
        }
        comp++;
    }

    fclose(fichier);

}