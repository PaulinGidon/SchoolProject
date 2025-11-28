#ifndef CHANGE_H  
#define CHANGE_H

#include <stdio.h>
#include <stdlib.h>
#include "import.h"

// Déclarations des fonctions
void copiTab(int h, int l, char tab[h][l], char tabch[h][l]);//copie tab dans tabch
int comptVoisin(int h, int l, char tab[h][l], int x, int y);//compte les voisin O de la cellule de tab[x][y]
void actuaTab(int h, int l, char tab[h][l]);//actualise le tableau en fonction des règle du jeu de la vie par John Conway


#endif // CHANGE_H