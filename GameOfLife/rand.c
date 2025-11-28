#include "rand.h"
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
char cellAlea() {
    int p = rand() % 4;//donne a p un nombre aléatoire entre 0 et 3
    char c = ' ';
    if (p == 1) {
        c = 'O';;
    }
    return c;
}

void tabAlea(int h,int l,char tab[h] [l]) {
    srand(time(NULL));//Crée un seed pour ne pas refaire plusieur fois le même aléatoire
    int i=0;
    int j=0;
    for (i=0; i < h; i++) {
        for (j = 0; j < l; j++) {
            tab[i][j] = cellAlea();//mets une cellule crée par cellAlea a l'emplacement (i;j)
        }
    }
}
