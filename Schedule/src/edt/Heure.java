package edt;

/**
 * Représente une heure avec des heures et des minutes.
 * Permet la gestion des heures dans un format 24h et les opérations sur les heures.
 */
public class Heure {

    /**
     * Heure de la journée (0-23).
     */
    public Integer heure;

    /**
     * Minutes de l'heure (0-59).
     */
    public Integer minutes;

    /**
     * Constructeur initialisant l'heure et les minutes.
     * Gère les valeurs dépassant 24h ou négatives en les ramenant dans la plage valide.
     * 
     * @param heure   L'heure (peut être hors plage, sera normalisée).
     * @param minutes Les minutes (peut être hors plage, sera normalisées).
     */
    public Heure(Integer heure, Integer minutes) {
        int totalMinutes = heure * 60 + minutes;
        totalMinutes = ((totalMinutes % (24 * 60)) + (24 * 60)) % (24 * 60); // gestion des négatifs et dépassements
        this.heure = totalMinutes / 60;
        this.minutes = totalMinutes % 60;
    }

    /**
     * Constructeur par défaut.
     */
    public Heure() {}

    /**
     * Retourne l'heure.
     * 
     * @return L'heure (0-23).
     */
    public Integer getHeure() {
        return heure;
    }

    /**
     * Définit l'heure.
     * 
     * @param heure L'heure à définir (doit être entre 0 et 23).
     * @throws Exception si l'heure est nulle ou hors plage.
     */
    public void setHeure(Integer heure) throws Exception {
        if (heure == null) {
            throw new Exception("L'heure et les minutes ne doivent pas être null.");
        }
        if (heure < 0 || heure > 23) {
            throw new Exception("Heure invalide : " + heure);
        }
        this.heure = heure;
    }

    /**
     * Retourne les minutes.
     * 
     * @return Les minutes (0-59).
     */
    public Integer getMinutes() {
        return minutes;
    }

    /**
     * Définit les minutes.
     * 
     * @param minutes Les minutes à définir (doivent être entre 0 et 59).
     * @throws Exception si l'heure ou les minutes sont nulles ou si les minutes sont hors plage.
     */
    public void setMinutes(Integer minutes) throws Exception {
        if (heure == null || minutes == null) {
            throw new Exception("L'heure et les minutes ne doivent pas être null.");
        }
        if (minutes < 0 || minutes > 59) {
            throw new Exception("Minutes invalides : " + minutes);
        }
        this.minutes = minutes;
    }

    /**
     * Calcule la différence en minutes entre cette heure et une autre heure.
     * 
     * @param h L'autre heure.
     * @return La différence absolue en minutes.
     */
    public int difference(Heure h) {
        int thisMinutes = this.heure * 60 + this.minutes;
        int otherMinutes = h.getHeure() * 60 + h.getMinutes();
        return Math.abs(thisMinutes - otherMinutes);
    }

    /**
     * Ajoute un nombre de minutes à cette heure en gérant le dépassement sur 24h.
     * 
     * @param min Le nombre de minutes à ajouter (peut être négatif).
     * @return Une nouvelle instance d'Heure après addition.
     */
    public Heure ajouterMinutes(int min) {
        int totalMinutes = this.heure * 60 + this.minutes + min;
        totalMinutes = ((totalMinutes % (24 * 60)) + (24 * 60)) % (24 * 60);
        int nouvelleHeure = totalMinutes / 60;
        int nouvellesMinutes = totalMinutes % 60;
        return new Heure(nouvelleHeure, nouvellesMinutes);
    }

    /**
     * Représentation textuelle de l'heure au format "HH:mm".
     * 
     * @return La chaîne formatée.
     */
    @Override
    public String toString() {
        return String.format("%02d", heure) + ":" + String.format("%02d", minutes);
    }
    /**
     * Retourne la valeur en minutes de l'heure en entière
     * 
     * @return la valeur en minutes de l'heure
     */
    public int getToutMinutes() {
    	return minutes+heure*60;
    }
}
