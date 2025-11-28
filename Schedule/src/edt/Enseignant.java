package edt;

import java.util.Objects;
import java.io.*;

/**
 * Classe représentant un enseignant avec un nom.
 */
public class Enseignant {
    /**
     * Nom de l'enseignant.
     */
    private String nom;

    /**
     * Constructeur principal.
     * 
     * @param nom Le nom de l'enseignant.
     */
    public Enseignant(String nom) {
        this.nom = nom != null ? nom.trim() : null;
    }

    /**
     * Retourne le nom de l'enseignant.
     * 
     * @return le nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * Définit le nom de l'enseignant.
     * 
     * @param nom Le nouveau nom à définir.
     */
    public void setNom(String nom) {
        this.nom = nom != null ? nom.trim() : null;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères de l'enseignant.
     * Supprime l'éventuel espace initial dans le nom.
     * 
     * @return une chaîne représentant l'enseignant.
     */
    @Override
    public String toString() {
        return "(" + nom + ")";
    }

    /**
     * Compare cet enseignant avec un autre objet.
     * Deux enseignants sont égaux si leurs noms sont égaux.
     * 
     * @param obj L'objet à comparer.
     * @return true si égal, false sinon.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Enseignant autre = (Enseignant) obj;
        return Objects.equals(nom, autre.nom);
    }

    /**
     * Calcule le code de hachage de l'enseignant basé sur son nom.
     * 
     * @return le hashCode
     */
    @Override
    public int hashCode() {
        return Objects.hash(nom);
    }
}
