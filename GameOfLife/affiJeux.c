// affiJeux.c permet d'afficher les commandes d'interaction et d'en modifier les couleurs
#include "affiJeux.h"
// Fonction qui affiche la nombre de generation grace a l'appel de la fonction "gen"
void generation(int gen, WINDOW *wind)
{
    init_pair(1, COLOR_GREEN, COLOR_BLACK);
    wattron(wind, COLOR_PAIR(4));
    mvwprintw(wind, 20, 1, "Génération");
    mvwprintw(wind, 21, 1, "%d", gen);
    init_pair(1, COLOR_WHITE, COLOR_BLACK);
    wattroff(wind, COLOR_PAIR(1));
    mvwprintw(wind, 21, 1, "%d", gen);
    wrefresh(wind);
}
// Fonction qui affiche la periode d'affichage grâce a l'appel de la fonction  "per"
void periode(int per, WINDOW *wind)
{
    init_pair(1, COLOR_GREEN, COLOR_BLACK);
    wattron(wind, COLOR_PAIR(4));
    mvwprintw(wind, 25, 0, "Période d'affichage");
    wattroff(wind, COLOR_PAIR(1));
    init_pair(1, COLOR_WHITE, COLOR_BLACK);
    mvwprintw(wind, 26, 1, "%d us     ", per);
    wrefresh(wind);
}
// Fonction qui affiche un tableau quelconque afin PK ? je sais pas
void affTabW(int h, int l, char tab[h][l], WINDOW *wind)
{
    init_pair(1, COLOR_WHITE, COLOR_BLACK);
    wattron(wind, COLOR_PAIR(1));
    for (int i = 2; i < h - 5; i++)
    {
        for (int j = 2; j < l - 5; j++)
        {
            mvwprintw(wind, i, j, "%c", tab[i][j]);
        }
    }
    wrefresh(wind);
}
// Fonction qui permet affiche la fenêtre correspondante aux chiffres sélectionné dans le menu
void metTabChoixW(int h, int l, char tab[h][l], int choix, WINDOW *wind)
{
    switch (choix)
    {
    case 1:
        tabAlea(h, l, tab);
        break;
    case 2:
        txtToTab(h, l, tab);
        break;
    case 3:
        tabCanon(h, l, tab);
        break;
    }
    affTabW(h, l, tab, wind);
}
// Fonction qui permet de changer de generation
void passGen(int g, int h, int l, char tab[h][l], int per, WINDOW *wind, WINDOW *w)
{
    generation(g, w);
    actuaTab(h, l, tab);
    affTabW(h, l, tab, wind);
    usleep(per);
}
// Fonction qui permet d'interagir avec le jeu
void interaction(char choix, int *a, int *b, int *g, int h, int l, char tab[h][l], int *per, WINDOW *wind, WINDOW *w)
{
    if (choix == 'q') // si "q" est séléectionné, le jeu se ferme
    {
        *a = 0;
        *b = 0;
        usleep(3000000);
    }
    else if (choix == 's') // si "s" est sélectionné, ça avance la génération de 1
    {
        (*g)++;
        *b = 0;
        passGen(*g, h, l, tab, *per, wind, w);
    }
    else if (choix == 'e') // si "e" est sélectionné, ça ralentit la vitesse de génération
    {
        *per = (*per) * 2;
        periode(*per, w);
    }
    else if (choix == 'z') // si "z" est sélectionné, ça accélère la vitesse de génération.
    {
        if (*per != 1)
        {
            *per = (*per) / 2;
            periode(*per, w);
        }
    }
    else if (choix == 'a') // si "a" est sélectionnné, la génération est automatisée.
    {
        *b = 1;
    }
}

void commande(int g, int h, int l, char tab[h][l], int per, WINDOW *wind, WINDOW *w)
{
    int a = 1;  // Variable de contrôle pour la boucle principale (1 = continuer, 0 = arrêter)
    int b = 0;  // Variable de contrôle pour la sous-boucle d'auto mode
    int choixA; // Variable pour stocker le choix de l'utilisateur en mode manuel
    int choixB; // Variable pour stocker le choix de l'utilisateur en mode auto

    // Boucle principale du jeu
    while (a)
    {
        nodelay(stdscr, FALSE); // Mode bloquant pour attendre une entrée de l'utilisateur
        choixA = getch();       // Lecture de l'entrée de l'utilisateur
        usleep(per);            // Pause entre chaque cycle en fonction de la période 'per'

        if (choixA != ERR)
        { // Si l'utilisateur a entré une commande
            // Appelle la fonction interaction pour gérer l'entrée et mettre à jour les états de jeu
            interaction(choixA, &a, &b, &g, h, l, tab, &per, wind, w);
        }

        // Boucle secondaire pour gérer le mode automatique
        while (b)
        {
            nodelay(stdscr, TRUE); // Mode non-bloquant pour pouvoir lire des entrées sans pause
            choixB = getch();      // Lecture de l'entrée de l'utilisateur en mode auto
            g++;                   // Incrémente le compteur de génération

            // Génère la nouvelle génération de cellules et affiche l'état du jeu
            passGen(g, h, l, tab, per, wind, w);

            if (choixB != ERR)
            { // Si une commande est entrée en mode auto
                // Gère la commande et ajuste les variables de contrôle
                interaction(choixB, &a, &b, &g, h, l, tab, &per, wind, w);
            }
        }
    }

    // Nettoyage des fenêtres et fermeture de ncurses
    delwin(wind);
    delwin(w);
    endwin();
}

void Jeux(int choix)
{
    initscr();                              // Initialise l'environnement ncurses
    noecho();                               // Désactive l'affichage des entrées utilisateur
    cbreak();                               // Entrées disponibles immédiatement sans besoin de 'Enter'
    refresh();                              // Rafraîchit l'écran principal
    start_color();                          // Initialise le mode couleur
    init_pair(1, COLOR_GREEN, COLOR_BLACK); // Définit une paire de couleurs vert/noir

    int h = 30, l = 20, y = 5, x = 85;
    WINDOW *win = newwin(h, l, y, x); // Crée une fenêtre pour afficher les informations du jeu
    x = 25;
    l = 60;
    WINDOW *windo = newwin(h, l, y, x); // Crée une fenêtre pour afficher la grille du jeu
    l = 64;
    h = 34;
    box(windo, 0, 0); // Dessine une boîte autour de la fenêtre de la grille
    wrefresh(windo);  // Rafraîchit pour afficher la boîte

    char tab[l][h]; // Tableau de caractères représentant la grille de cellules
    for (int i = 0; i < l; i++)
    { // Initialise chaque cellule de la grille à ' ' (vide)
        for (int j = 0; j < h; j++)
        {
            tab[i][j] = ' ';
        }
    }

    int gen = 0;       // Initialisation de la génération
    int per = 1000000; // Définition de la période de mise à jour (1 seconde)

    // Affichage des informations sur le jeu et les commandes utilisateur
    wattron(win, COLOR_PAIR(5));
    mvwprintw(win, 1, 1, "Le jeu de la vie ");
    mvwprintw(win, 2, 1, "C'est un automate");
    mvwprintw(win, 3, 1, "cellulaire imaginé  ");
    mvwprintw(win, 4, 1, "par John Conway");
    wattroff(win, COLOR_PAIR(1));

    // Affiche les commandes disponibles pour l'utilisateur
    wattron(win, COLOR_PAIR(4));
    mvwprintw(win, 11, 1, "Touches");
    wattroff(win, COLOR_PAIR(1));
    mvwprintw(win, 12, 1, "a : auto");
    mvwprintw(win, 13, 1, "z : fast");
    mvwprintw(win, 14, 1, "e : slow");
    mvwprintw(win, 15, 1, "s : step");
    mvwprintw(win, 16, 1, "q : quit");

    // Affiche la génération initiale et la période
    generation(gen, win);
    periode(per, win);

    // Initialise la grille avec le choix de configuration de l'utilisateur
    init_pair(1, COLOR_GREEN, COLOR_BLACK);
    metTabChoixW(h, l, tab, choix, windo);

    // Démarre la boucle principale du jeu en appelant la fonction commande
    commande(gen, h, l, tab, per, windo, win);
}
