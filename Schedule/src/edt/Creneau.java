package edt;

import java.util.ArrayList;

/**
 * Représente un créneau horaire contenant une ou plusieurs séances.
 * 
 * @param <T> Le type de séance contenu dans le créneau.
 */
public class Creneau<T> {

    /**
     * Heure de début du créneau.
     */
    public Heure debut;

    /**
     * Durée du créneau en minutes.
     */
    public Integer duree;

    /**
     * Liste des séances associées à ce créneau.
     */
    public ArrayList<T> seance = new ArrayList<>();

    /**
     * Constructeur initialisant le créneau avec une heure de début et une durée.
     * 
     * @param debut Heure de début du créneau.
     * @param duree Durée du créneau en minutes.
     */
    public Creneau(Heure debut, Integer duree) {
        super();
        this.debut = debut;
        this.duree = duree;
    }

    /**
     * Constructeur initialisant le créneau avec une heure de début, une durée,
     * et une séance initiale.
     * 
     * @param debut Heure de début du créneau.
     * @param duree Durée du créneau en minutes.
     * @param s     Séance initiale à ajouter au créneau.
     */
    public Creneau(Heure debut, int duree, T s) {
        super();
        this.debut = debut;
        this.duree = duree;
        seance.add(s);
    }

    /**
     * Retourne une représentation textuelle du créneau, avec ses séances.
     * 
     * @return Une chaîne décrivant le créneau et ses séances.
     */
    @Override
    public String toString() {
        String s = "*" + debut + " - " + debut.ajouterMinutes(duree) + "\n";
        for (int i = 0; i < seance.size(); i++) {
            s += seance.get(i) + "\n";
        }
        return s;
    }

    /**
     * Retourne la liste des séances associées à ce créneau.
     * 
     * @return La liste des séances.
     */
    public ArrayList<T> getSeance() {
        return seance;
    }

    /**
     * Définit la liste des séances pour ce créneau.
     * 
     * @param seance La nouvelle liste des séances.
     */
    public void setSeance(ArrayList<T> seance) {
        this.seance = seance;
    }

    /**
     * Ajoute une séance au créneau.
     * 
     * @param s La séance à ajouter.
     */
    public void addSeance(T s) {
        seance.add(s);
    }

    /**
     * Retire et retourne la séance à l'index donné dans la liste des séances.
     * 
     * @param i L'index de la séance à retirer.
     * @return La séance retirée.
     */
    public T popSeance(int i) {
        T result = seance.get(i);
        seance.remove(i);
        return result;
    }

    /**
     * Retourne l'heure de début du créneau.
     * 
     * @return L'heure de début.
     */
    public Heure getHeureDebut() {
        return debut;
    }

    /**
     * Retourne la durée du créneau en minutes.
     * 
     * @return La durée.
     */
    public Integer getDuree() {
        return duree;
    }

    /**
     * Affiche toutes les séances du créneau dans la console.
     */
    public void AfficheSeance() {
        for (int i = 0; i < seance.size(); i++) {
            System.out.println(seance.get(i));
        }
    }

}
