#ifndef menu_H
#define menu_H

#include <ncurses.h>
#include "affiJeux.h"
#include "import.h"


// Taille minimale requise pour l'affichage
#define HAUTEUR_MIN 30
#define LARGEUR_MIN 80

// Prototypes des fonctions de gestion des erreurs
int verifierTailleTerminal(void);
int verifierNcurses(WINDOW *fenetre);
int verifierCreationFichier(void);

// Prototypes des fonctions pour afficher le menu et le jeu
char afficherMenu(WINDOW *fenetreMenu);
void afficherJeu( WINDOW *fenetreJeu);
 
#endif // menu_H

