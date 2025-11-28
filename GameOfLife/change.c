#include "change.h"
#include <stdio.h>
#include <stdlib.h>
void copiTab(int h, int l, char tab[h][l], char tabch[h][l]) {//copie tab dans tabch
    for (int i = 0; i < h; i++) {
        for (int j = 0; j < l; j++) {
            tabch[i][j] = tab[i][j];
        }
    }
}

int comptVoisin(int h, int l, char tab[h][l], int x, int y) {
    int compt = 0;
    for (int i = -1; i <= 1; i++) {
        for (int j = -1; j <= 1; j++) {
            if (i == 0 && j == 0) continue; // Ignore la cellule elle-même
            int Vx = x + i;
            int Vy = y + j;
            // Vérifier que les coordonnées du voisin sont dans les limites du tableau
            if (Vx >= 0 && Vx < h && Vy >= 0 && Vy < l) {
                if (tab[Vx][Vy] == 'O') {
                    compt++;
                }
            }
        }
    }
    return compt;
}


void actuaTab(int h, int l, char tab[h][l]) {
    char tabch[h][l];
    copiTab(h, l, tab, tabch);
    int voisins;
    for (int i = 0; i < h; i++) {
        for (int j = 0; j < l; j++) {
            voisins=0;
            voisins += comptVoisin(h, l, tab, i, j);
            if (tab[i][j] == 'O') {
                // Une cellule vivante reste vivante si elle a 2 ou 3 voisins
                tabch[i][j] = (voisins == 2 || voisins == 3) ? 'O' : ' ';
            } else {
                // Une cellule morte devient vivante si elle a exactement 3 voisins
                tabch[i][j] = (voisins == 3) ? 'O' : ' ';
            }
        }
    }
    copiTab(h, l, tabch, tab);
}
