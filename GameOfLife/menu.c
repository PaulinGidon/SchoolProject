#include <stdio.h>
#include <unistd.h>
#include <ncurses.h>
#include "menu.h"
#include "affiJeux.h"

// Fonction pour vérifier l'initialisation de ncurses (gestion des erreurs)
int verifierNcurses(WINDOW *win)
{
    if (win == NULL)
    {
        printf("Erreur : Impossible de créer les fenêtres ncurses.\n");
        return -2; // Code d'erreur pour création de fenêtre ncurses
    }

    return 0; // Pas d'erreur
}

// Fonction pour vérifier la création d'un fichier motif.txt (gestion des erreurs)
int verifierCreationFichier()
{
    FILE *fichier = fopen("motif.txt", "w"); // prend le fichier texte mis par l'utilisateur en utilisant un pointeur quand il appuie sur la touche

    if (fichier == NULL)
    {
        printf("Erreur : Impossible de créer le fichier motif.txt.\n"); // gestion d'erreur du fichier txt
        return -3;                                                      // Code d'erreur pour création de fichier
    }

    fclose(fichier); // Fermer le fichier après création
    return 0;        // Pas d'erreur
}

int verifierTailleTerminal()
{
    int nbLigne, nbColonne;
    getmaxyx(stdscr, nbLigne, nbColonne); // Obtenir la taille du terminal

    if (nbLigne < HAUTEUR_MIN || nbColonne < LARGEUR_MIN)
    {
        return -1; // Code d'erreur pour taille insuffisante
    }

    return 0; // Pas d'erreur
}

// Retourne la touche sélectionnée par l'utilisateur
char afficherMenu(WINDOW *fenetreMenu)
{
    wclear(fenetreMenu); // Efface le contenu de la fenêtre au début

    // Initialiser les couleurs (si ncurses supporte les couleurs)

    // Affiche les options du menu (texte sans couleur) une seule fois
    mvwprintw(fenetreMenu, 12, 5, "MENU :");
    mvwprintw(fenetreMenu, 14, 5, "1. Nouvelle Partie (génération faite au hasard)");
    mvwprintw(fenetreMenu, 15, 5, "2. Motif par fichier");
    mvwprintw(fenetreMenu, 16, 5, "3. Canon de planeurs");
    mvwprintw(fenetreMenu, 17, 5, "q. Quitter");

    // Rafraîchir l'affichage des options du menu
    wrefresh(fenetreMenu);

    int compteur = 0;
    int saisie;

    // Boucle pour l'animation tout en surveillant la saisie utilisateur
    while (1)
    {
        compteur = (compteur % 4) + 1;
        wattron(fenetreMenu, COLOR_PAIR(compteur));
        mvwprintw(fenetreMenu, 0, 5, " _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ \n");
        mvwprintw(fenetreMenu, 1, 5, "  O OOOO O  O     OOO   OOOO     O   OOOO    O     O   O  OOOO \n");
        mvwprintw(fenetreMenu, 2, 5, "  O O    O  O     O  O  O        O   O  O    O     O   O  O    \n");
        mvwprintw(fenetreMenu, 3, 5, "  O OOO  O  O     O  O  OOO      O   OOOO    O     O   O  OOO  \n");
        mvwprintw(fenetreMenu, 4, 5, "O O O    O  O     O  O  O        O   O  O     O   O    O  O    \n");
        mvwprintw(fenetreMenu, 5, 5, " O  OOOO  OO      OOO   OOOO     OOO O  O       O      O  OOOO \n");
        mvwprintw(fenetreMenu, 6, 5, " _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ \n");
        wattroff(fenetreMenu, COLOR_PAIR(compteur));
        wrefresh(fenetreMenu);
        usleep(400000);

        // Vérifier si une touche a été pressée
        if ((saisie = wgetch(fenetreMenu)) != ERR)
        {
            // Si une touche valide est pressée, on arrête l'animation
            if (saisie == '1' || saisie == '2' || saisie == '3' || saisie == 'q')
            {
                return saisie; // Retourne la touche pour traitement dans le main
            }
        }
    }
}

void afficherJeu(WINDOW *fenetreJeu)
{
    wclear(fenetreJeu);    // Efface le contenu de la fenêtre
    box(fenetreJeu, 0, 0); // Dessine une bordure autour de la fenêtre

    // Affiche le jeu (ici, c'est juste un texte pour illustrer)
    mvwprintw(fenetreJeu, 2, 2, "JEU EN COURS...");
    mvwprintw(fenetreJeu, 4, 4, "Appuyez sur 'q' pour revenir au menu");

    wrefresh(fenetreJeu); // Rafraîchit l'affichage de la fenêtre
}
