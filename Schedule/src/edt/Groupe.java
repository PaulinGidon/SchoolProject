package edt;

/**
 * Représente un groupe identifié par un nom.
 */
public class Groupe {

    /**
     * Nom du groupe.
     */
    public String nom;

    /**
     * Constructeur initialisant le groupe avec un nom donné.
     * 
     * @param nom Le nom du groupe.
     */
    public Groupe(String nom) {
        super();
        this.nom = nom;
    }

    /**
     * Retourne une représentation textuelle du groupe (son nom).
     * 
     * @return Le nom du groupe.
     */
    @Override
    public String toString() {
        return nom;
    }

    /**
     * Retourne le nom du groupe.
     * 
     * @return Le nom.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Définit le nom du groupe.
     * 
     * @param nom Le nouveau nom.
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Compare ce groupe avec un autre objet pour vérifier l'égalité.
     * Deux groupes sont égaux si leurs noms sont égaux.
     * 
     * @param obj L'objet à comparer.
     * @return true si les groupes sont égaux, false sinon.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Groupe autre = (Groupe) obj;
        if (nom == null) {
            return autre.nom == null;
        } else {
            return nom.equals(autre.nom);
        }
    }
}
