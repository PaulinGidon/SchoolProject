package edt;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Représente une semaine contenant un tableau de jours.
 */
public class Semaine {

    /**
     * Tableau des jours de la semaine (5 jours).
     */
    public Jour[] jours = new Jour[5];

    /**
     * Nombre actuel de jours dans la semaine.
     */
    public int taille = 0;

    /**
     * Numéro de la semaine.
     */
    public Integer numero;

    /**
     * Constructeur initialisant la semaine avec un numéro.
     * 
     * @param numero Numéro de la semaine.
     */
    public Semaine(Integer numero) {
        super();
        this.numero = numero;
    }

    /**
     * Constructeur initialisant la semaine avec un tableau de jours et un numéro.
     * 
     * @param tj  Tableau de jours.
     * @param i   Numéro de la semaine.
     */
    public Semaine(Jour[] tj, int i) {
        jours = tj;
        numero = i;
    }

    /**
     * Ajoute un jour à la semaine.
     * 
     * @param j Jour à ajouter.
     * @throws Exception Si le tableau de jours est déjà rempli.
     */
    public void addJour(Jour j) throws Exception {
        if (taille >= 5) {
            throw new Exception("Tableau déjà rempli");
        } else {
            jours[taille] = j;
            taille++;
        }
    }

    /**
     * Retire et retourne le jour à l'index donné.
     * 
     * @param id Index du jour à retirer.
     * @return Le jour retiré.
     * @throws Exception Si l'index est inaccessible.
     */
    public Jour pop(int id) throws Exception {
        if (taille <= id || taille == 0) {
            throw new Exception("Index inaccessible");
        } else {
            Jour result = jours[id];
            Jour[] resultat = new Jour[5];
            for (int i = 0, j = 0; i < taille; i++) {
                if (i != id) {
                    resultat[j++] = jours[i];
                }
            }
            jours = resultat;
            taille--;
            return result;
        }
    }

    /**
     * Retourne le tableau des jours de la semaine.
     * 
     * @return Le tableau des jours.
     */
    public Jour[] getJours() {
        return jours;
    }

    /**
     * Définit le tableau des jours de la semaine.
     * 
     * @param jours Nouveau tableau des jours.
     */
    public void setJours(Jour[] jours) {
        this.jours = jours;
    }

    /**
     * Retourne le numéro de la semaine.
     * 
     * @return Le numéro de la semaine.
     */
    public Integer getNumero() {
        return numero;
    }

    /**
     * Définit le numéro de la semaine.
     * 
     * @param numero Nouveau numéro de la semaine.
     */
    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    /**
     * Retourne une représentation textuelle de la semaine et de ses jours.
     * 
     * @return Une chaîne décrivant la semaine.
     */
    @Override
    public String toString() {
        String str = "\n" + "# semaine " + numero + "\n";
        for (int j = 0; j < jours.length; j++) {
            str += ("\n" + jours[j]);
        }
        return str;
    }
}
