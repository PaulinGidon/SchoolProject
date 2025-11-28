#include <stdio.h>
#include "canon.h"
#include "rand.h"

int tabCanon(int h, int l, char tab[h][l])
{
    int hc = 9;  // hauteur max du canon
    int lc = 36; // largeur max du canon
    int canon[36][2] = {
        {1, 25}, {2, 23}, {2, 25}, {3, 13}, {3, 14}, {3, 21}, {3, 22}, {3, 35}, {3, 36}, {4, 12}, {4, 16}, {4, 21}, {4, 22}, {4, 35}, {4, 36}, {5, 1}, {5, 2}, {5, 11}, {5, 17}, {5, 21}, {5, 22}, {6, 1}, {6, 2}, {6, 11}, {6, 15}, {6, 17}, {6, 18}, {6, 23}, {6, 25}, {7, 11}, {7, 17}, {7, 25}, {8, 12}, {8, 16}, {9, 13}, {9, 14}}; // Coordonnées pour le canon à planeurs

    if (l < lc || h < hc)
    {
        printf("Le tableau final sera trop petit.\n");
        return 1; // Vérifie si le canon à planeurs rentre dans le tableau
    }

    // Initialiser le tableau avec des espaces
    for (int i = 0; i < h; i++)
    {
        for (int j = 0; j < l; j++)
        {
            tab[i][j] = ' ';
        }
    }

    // Dessiner le canon
    for (int compt = 0; compt < 36; compt++)
    {
        int i = canon[compt][0] + 1;
        int j = canon[compt][1] + 1;
        if (i < h && j < l)
        { // Vérification des limites
            tab[i][j] = 'O';
        }
    }
    return 0;
}