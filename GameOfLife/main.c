#include <ncurses.h>
#include "menu.h"
#include "affiJeux.h"

int main()
{
    // Initialisation de ncurses
    initscr();
    cbreak();
    noecho();
    keypad(stdscr, TRUE); // Gestion des touches spéciales
    curs_set(0);          // Masque le curseur

    // Initialisation des couleurs
    if (has_colors())
    {
        start_color();
        init_pair(1, COLOR_BLUE, COLOR_BLACK);
        init_pair(2, COLOR_RED, COLOR_BLACK);
        init_pair(3, COLOR_YELLOW, COLOR_BLACK);
        init_pair(4, COLOR_GREEN, COLOR_BLACK);
        init_pair(5, COLOR_MAGENTA, COLOR_BLACK);
    }

    // Définir la taille de l'écran
    int nbLigne = 30;
    int nbColonne = 80;

    // Créer une fenêtre pour le menu et une autre pour le jeu
    WINDOW *fenetreMenu = newwin(nbLigne, nbColonne, 0, 0);
    WINDOW *fenetreJeu = newwin(nbLigne, nbColonne, 0, 0);

    if (verifierNcurses(fenetreMenu) != 0)
    {
        endwin();  // Restaure le terminal avant de quitter
        return -1; // Quitte si la création échoue
    }

    if (verifierTailleTerminal() == -1)
    {
        printf("Erreur : Dimensions insuffisantes pour l'affichage. Minimum requis : 30 lignes et 80 colonnes.\n");
        return -1;
    }

    nodelay(fenetreMenu, TRUE);

    int saisie;
    int boucle = 1;
    // Boucle principale pour afficher les bonnes fenêtres soit celle du menu ou du jeu
    while (boucle)
    {

        saisie = afficherMenu(fenetreMenu); // Affiche le menu et récupère la saisie utilisateur

        // Gérer l'entrée clavier pour le menu
        if (saisie == 'q')
        {               // Option q: Quitter
            boucle = 0; // Quitte la boucle
        }
        else
        {
            Jeux(saisie - '0'); // Affiche l'écran du jeu
            boucle = 0;
        }
    }

    // Fin du programme
    delwin(fenetreMenu); // Supprime les fenêtres
    delwin(fenetreJeu);
    endwin(); // Restaure le terminal
    return 0; // Ajout de la fermeture correcte de la fonction main
}
