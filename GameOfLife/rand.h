#ifndef RAND_H  // Utilise un nom plus explicite
#define RAND_H

#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include "import.h"

// Déclaration des fonctions
char cellAlea();//Retourne sois le charactère 'O' sois le caractère ' ' (espace) avec 25% de chance de retourné un O et 75% de chance de retourné un espace
void tabAlea(int h, int l, char tab[h][l]);//Crée un tableau remplie de charactère retourné par cellAlea

#endif