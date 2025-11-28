package edt;

import java.util.ArrayList;

/**
 * Représente une ressource pédagogique.
 */
public class Ressource {
    
    /**
     * Nom de la ressource.
     */
    public String nom;
    
    /**
     * Enseignant responsable de la ressource.
     */
    public Enseignant responsable;
    
    /**
     * Code unique de la ressource.
     */
    public String code;
    
    /**
     * Liste des enseignants associés à cette ressource.
     */
    public ArrayList<Enseignant> enseignants = new ArrayList<>();
    
    /**
     * Liste des numéros des semaines prévues pour cette ressource.
     */
    public ArrayList<Integer> semainesPrevues = new ArrayList<>();
    
    /**
     * Constructeur principal.
     * 
     * @param nom Nom de la ressource.
     * @param responsable Enseignant responsable.
     * @param code Code unique de la ressource.
     * @param enseignants Liste des enseignants associés.
     * @param semainesPrevues Liste des semaines prévues.
     */
    public Ressource(String nom, Enseignant responsable, String code, ArrayList<Enseignant> enseignants,
                     ArrayList<Integer> semainesPrevues) {
        super();
        this.nom = nom;
        this.responsable = responsable;
        this.code = code;
        this.enseignants = enseignants;
        this.semainesPrevues = semainesPrevues;
    }
    
    /**
     * Constructeur vide.
     */
    public Ressource() {}
    
    /**
     * Retourne le code de la ressource.
     * 
     * @return Le code.
     */
    public String getCode() {
        return code;
    }
    
    /**
     * Définit le code de la ressource.
     * 
     * @param code Nouveau code.
     */
    public void setCode(String code) {
        this.code = code;
    }
    
    /**
     * Ajoute un enseignant à la liste.
     * 
     * @param e Enseignant à ajouter.
     */
    public void addEnseignant(Enseignant e) {
        enseignants.add(e);
    }
    
    /**
     * Supprime et retourne un enseignant à l'indice donné.
     * 
     * @param i Indice de l'enseignant à supprimer.
     * @return L'enseignant supprimé.
     */
    public Enseignant popEnseignant(int i){
        Enseignant result = enseignants.get(i);
        enseignants.remove(i);
        return result;
    }
    
    /**
     * Ajoute une semaine prévue.
     * 
     * @param i Numéro de la semaine à ajouter.
     */
    public void addSemaine(Integer i) {
        semainesPrevues.add(i);
    }
    
    /**
     * Supprime et retourne une semaine prévue à l'indice donné.
     * 
     * @param i Indice de la semaine à supprimer.
     * @return La semaine supprimée.
     */
    public Integer popSemainesPrevues(int i){
        Integer result = semainesPrevues.get(i);
        semainesPrevues.remove(i);
        return result;
    }
    
    /**
     * Retourne le nom de la ressource.
     * 
     * @return Le nom.
     */
    public String getNom() {
        return nom;
    }
    
    /**
     * Définit le nom de la ressource.
     * 
     * @param nom Nouveau nom.
     */
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    /**
     * Retourne l'enseignant responsable.
     * 
     * @return L'enseignant responsable.
     */
    public Enseignant getResponsable() {
        return responsable;
    }
    
    /**
     * Définit l'enseignant responsable.
     * 
     * @param responsable Nouvel enseignant responsable.
     */
    public void setResponsable(Enseignant responsable) {
        this.responsable = responsable;
    }
    
    /**
     * Retourne la liste des enseignants associés.
     * 
     * @return Liste des enseignants.
     */
    public ArrayList<Enseignant> getEnseignants() {
        return enseignants; 
    }
    
    /**
     * Définit la liste des enseignants associés.
     * 
     * @param enseignants Nouvelle liste d'enseignants.
     */
    public void setEnseignants(ArrayList<Enseignant> enseignants) {
        this.enseignants = enseignants;
    }
    
    /**
     * Retourne la liste des semaines prévues.
     * 
     * @return Liste des semaines.
     */
    public ArrayList<Integer> getSemainesPrevues() {
        return semainesPrevues;
    } 
    
    /**
     * Définit la liste des semaines prévues.
     * 
     * @param semainesPrevues Nouvelle liste des semaines prévues.
     */
    public void setSemainesPrevues(ArrayList<Integer> semainesPrevues) {
        this.semainesPrevues = semainesPrevues;
    }
    
    /**
     * Retourne une représentation textuelle de la ressource.
     * 
     * @return Chaîne représentant la ressource.
     */
    @Override
    public String toString() {
        String s = "\n nom : " + nom + "\nresponsable : " + responsable + "\ncode : " + code + " \nenseignants : \n";
        for (int i = 0; i < enseignants.size(); i++) {
            s += enseignants.get(i) + " | ";
        }
        s += " \nSemaine prévue :\n";
        for (int i = 0; i < semainesPrevues.size(); i++) {
            s += semainesPrevues.get(i) + " | ";
        }
        return s;
    }
}
