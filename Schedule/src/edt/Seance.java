package edt;

/**
 * Représente une séance associant un enseignant et une ressource.
 */
public class Seance {

    /**
     * L'enseignant responsable de la séance.
     */
    public Enseignant enseignant;

    /**
     * La ressource associée à la séance.
     */
    public Ressource ressource;

    /**
     * Constructeur de la classe Seance avec enseignant et ressource.
     * 
     * @param enseignant L'enseignant de la séance.
     * @param ressource  La ressource associée à la séance.
     */
    public Seance(Enseignant enseignant, Ressource ressource) {
        super();
        this.enseignant = enseignant;
        this.ressource = ressource;
    }

    /**
     * Constructeur par défaut.
     */
    public Seance() {}

    /**
     * Retourne l'enseignant de la séance.
     * 
     * @return L'enseignant.
     */
    public Enseignant getEnseignant() {
        return enseignant;
    }

    /**
     * Définit l'enseignant de la séance.
     * 
     * @param enseignant L'enseignant à définir.
     */
    public void setEnseignant(Enseignant enseignant) {
        this.enseignant = enseignant;
    }

    /**
     * Retourne la ressource associée à la séance.
     * 
     * @return La ressource.
     */
    public Ressource getRessource() {
        return ressource;
    }

    /**
     * Définit la ressource associée à la séance.
     * 
     * @param ressource La ressource à définir.
     */
    public void setRessource(Ressource ressource) {
        this.ressource = ressource;
    }
}
