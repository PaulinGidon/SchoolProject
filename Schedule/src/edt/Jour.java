package edt;

import java.util.ArrayList;

/**
 * Représente un jour de la semaine avec ses créneaux de TD et CM.
 */
public class Jour {
    
    /**
     * Liste des créneaux de TD pour ce jour.
     */
    private ArrayList<Creneau<TD>> creneauxTD = new ArrayList<>();
    
    /**
     * Liste des créneaux de CM pour ce jour.
     */
    private ArrayList<Creneau<CM>> creneauxCM = new ArrayList<>();
    
    /**
     * Jour de la semaine correspondant.
     */
    private JoursSemaines jourSemaines;

    /**
     * Constructeur par défaut.
     */
    public Jour() {
        super(); 
    }
    
    /**
     * Constructeur initialisant les créneaux de TD et CM.
     * 
     * @param crenauxTD La liste des créneaux TD.
     * @param crenauxCM La liste des créneaux CM.
     */
    public Jour(ArrayList<Creneau<TD>> crenauxTD, ArrayList<Creneau<CM>> crenauxCM) {
        super();
        this.creneauxTD = crenauxTD;
        this.creneauxCM = crenauxCM;
    }
    
    /**
     * Constructeur initialisant les créneaux de TD, CM et le jour de la semaine.
     * 
     * @param crenauxTD La liste des créneaux TD.
     * @param crenauxCM La liste des créneaux CM.
     * @param jourSemaines Le jour de la semaine.
     */
    public Jour(ArrayList<Creneau<TD>> crenauxTD, ArrayList<Creneau<CM>> crenauxCM, JoursSemaines jourSemaines) {
        super();
        this.jourSemaines = jourSemaines;
        this.creneauxTD = crenauxTD;
        this.creneauxCM = crenauxCM;
    }

    /**
     * Constructeur initialisant uniquement le jour de la semaine.
     * 
     * @param jourSemaines Le jour de la semaine.
     */
    public Jour(JoursSemaines jourSemaines) {
        super();
        this.jourSemaines = jourSemaines;
    }
    
    /**
     * Ajoute un créneau de CM à la liste.
     * 
     * @param c Le créneau CM à ajouter.
     */
    public void addCM(Creneau<CM> c) {
        creneauxCM.add(c);
    }

    /**
     * Supprime et retourne un créneau CM à l'indice donné.
     * 
     * @param i L'indice du créneau à supprimer.
     * @return Le créneau CM supprimé.
     */
    public Creneau<CM> popCM(int i) {
        Creneau<CM> result = creneauxCM.get(i); 
        creneauxCM.remove(i);
        return result;
    }

    /**
     * Ajoute un créneau de TD à la liste.
     * 
     * @param td Le créneau TD à ajouter.
     */
    public void addTD(Creneau<TD> td) {
        creneauxTD.add(td);
    }

    /**
     * Supprime et retourne un créneau TD à l'indice donné.
     * 
     * @param i L'indice du créneau à supprimer.
     * @return Le créneau TD supprimé.
     */
    public Creneau<TD> popTD(int i) { 
        Creneau<TD> result = creneauxTD.get(i);
        creneauxTD.remove(i);
        return result;
    } 

    /**
     * Retourne la liste des créneaux TD.
     * 
     * @return Liste des créneaux TD.
     */
    public ArrayList<Creneau<TD>> getCrenauxTD() {
        return creneauxTD; 
    }

    /**
     * Définit la liste des créneaux TD.
     * 
     * @param crenauxTD La nouvelle liste des créneaux TD.
     */
    public void setCrenauxTD(ArrayList<Creneau<TD>> crenauxTD) {
        this.creneauxTD = crenauxTD;
    }

    /**
     * Retourne la liste des créneaux CM.
     * 
     * @return Liste des créneaux CM.
     */
    public ArrayList<Creneau<CM>> getCrenauxCM() {
        return creneauxCM;
    }

    /**
     * Définit la liste des créneaux CM.
     * 
     * @param crenauxCM La nouvelle liste des créneaux CM.
     */
    public void setCrenauxCM(ArrayList<Creneau<CM>> crenauxCM) {
        this.creneauxCM = crenauxCM;
    }
 
    /**
     * Retourne le jour de la semaine associé.
     * 
     * @return Le jour de la semaine.
     */
    public JoursSemaines getJourSemaines() {
        return jourSemaines;
    }

    /**
     * Définit le jour de la semaine.
     * 
     * @param jourSemaines Le nouveau jour de la semaine.
     */
    public void setJourSemaines(JoursSemaines jourSemaines) {
        this.jourSemaines = jourSemaines;
    }

    /**
     * Retourne la liste des créneaux CM.
     * 
     * @return La liste des créneaux CM.
     */
    public ArrayList<Creneau<CM>> getListeCM() {
        return creneauxCM;
    }

    /**
     * Retourne la liste des créneaux TD.
     * 
     * @return La liste des créneaux TD.
     */
    public ArrayList<Creneau<TD>> getListeTD() {
        return creneauxTD;
    }

    /**
     * Retourne le jour de la semaine.
     * 
     * @return Le jour.
     */
    public JoursSemaines getJour() {
        return jourSemaines;
    }

    /**
     * Retourne une représentation textuelle du jour avec ses créneaux CM et TD.
     * 
     * @return La chaîne représentant le jour et ses créneaux.
     */
    @Override
    public String toString() {
        String str= "##" + jourSemaines + "\n";
        for(int k = 0; k < creneauxCM.size(); k++) {
            str += creneauxCM.get(k);
        }
        for(int k = 0; k < creneauxTD.size(); k++) {
            str += creneauxTD.get(k);
        }
        return str;
    }
}
