package edt;

/**
 * Classe représentant une séance de type TD (Travaux Dirigés).
 * Une séance TD est associée à une ressource, un enseignant, et peut être rattachée à un groupe.
 */
public class TD extends Seance {
    private Groupe groupe;

    /**
     * Constructeur complet.
     * 
     * @param ressource la ressource associée à la séance
     * @param enseignant l'enseignant de la séance
     * @param groupe le groupe concerné par la séance
     */
    public TD(Ressource ressource, Enseignant enseignant, Groupe groupe) {
        super(enseignant, ressource);
        this.groupe = groupe;
    } 

    /**
     * Constructeur sans groupe.
     * 
     * @param ressource la ressource associée à la séance
     * @param enseignant l'enseignant de la séance
     */
    public TD(Ressource ressource, Enseignant enseignant) {
        super(enseignant, ressource);
    }

    /**
     * Constructeur par défaut.
     */
    public TD() {}

    /**
     * Récupère le groupe associé à la séance.
     * 
     * @return le groupe
     */
    public Groupe getGroupe() {
        return groupe;
    }

    /**
     * Définit le groupe associé à la séance.
     * 
     * @param groupe le groupe à définir
     */
    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }

    /**
     * Représentation textuelle de la séance TD.
     * 
     * @return une chaîne décrivant la séance TD
     */
    @Override
    public String toString() {
        String codeRessource = (ressource != null) ? ressource.getCode() : "null";
        String nomEnseignant = (enseignant != null) ? enseignant.toString() : "null";
        String nomGroupe = (groupe != null) ? groupe.toString() : "null";

        return "TD-" + nomGroupe + " " + codeRessource + " " + nomEnseignant;
    }
}
