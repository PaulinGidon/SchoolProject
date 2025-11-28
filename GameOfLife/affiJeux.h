#ifndef AffichJeux_h  // Protection contre les inclusions multiples
#define AffichJeux_h
#include <ncurses.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <unistd.h>
#include "import.h"

// Déclarations des fonctions
void generation(int gen, WINDOW *wind);//affiche la génération actuelle 
void periode(int per, WINDOW *wind);//affiche la pèriode entre de génération
void affTabW(int h, int l, char tab[h][l], WINDOW *wind);//affiche tab dans la fenètre wind
void passGen(int g,int h, int l, char tab[h][l], int per, WINDOW *wind, WINDOW *w);//fait passer une génération , augmente la génération de  et actualise la table en fonction
void metTabChoixW(int h, int l, char tab[h][l],int choix ,WINDOW *wind);//met une table au choix dans wind
void interaction(char choix, int *a, int *b, int *g, int h, int l, char tab[h][l], int *per, WINDOW *wind, WINDOW *w); //gères une interaction avec les joueurs 
void commande(int g,int h, int l, char tab[h][l], int per,WINDOW *wind, WINDOW *w);//Gères toute les interaction jusqu'à la fin
void Jeux(int choix);//affiche l'interface et mets un tableau au choix 

#endif // AffichJeux_h