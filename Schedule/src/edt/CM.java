package edt;

/**
 * Classe représentant une séance de type CM (Cours Magistral).
 */
public class CM extends Seance {

    /**
     * Constructeur avec ressource et enseignant.
     * 
     * @param ressource la ressource associée à la séance
     * @param enseignant l'enseignant de la séance
     */
    public CM(Ressource ressource, Enseignant enseignant) {
        super(enseignant, ressource);
    }

    /**
     * Constructeur vide.
     */
    public CM() {}

    /**
     * Représentation textuelle de la séance CM.
     * 
     * @return une chaîne décrivant la séance CM
     */
    @Override
    public String toString() {
        String codeRessource = (ressource != null) ? ressource.getCode() : "null";//Si  ressource est nul alors on affiche null
        String nomEnseignant = (enseignant != null) ? enseignant.toString() : "null";//Si  enseignant est nul alors on affiche null
        return "CM " + codeRessource + " " + nomEnseignant;
    }
}