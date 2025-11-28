package edt;

import java.util.*;
import java.awt.Desktop;
import java.io.*;
import java.net.URI;
import java.nio.file.*;


/**
 * Représente un emploi du temps contenant les semaines, groupes, ressources, enseignants,
 * créneaux et le nombre de séances par ressource.
 * <p>
 * Cette classe fournit des méthodes pour initialiser, modifier et accéder
 * aux différentes composantes d'un emploi du temps.
 */
public class Edt {

    /** Tableau des 52 semaines de l'année. */
    public Semaine[] semaine = new Semaine[52];

    /** Liste des groupes présents dans l'emploi du temps. */
    public ArrayList<Groupe> groupes = new ArrayList<>();

    /** Liste des ressources associées (ex: salles, équipements). */
    public ArrayList<Ressource> ressources = new ArrayList<>();

    /** Liste des enseignants associés. */
    public ArrayList<Enseignant> enseignants = new ArrayList<>();

    /** Liste des créneaux horaires (avec leur type de séance). */
    public ArrayList<Creneau<? extends Seance>> creneaux = new ArrayList<>();

    /** Table de correspondance du nombre de séances par ressource. */
    public HashMap<String, ArrayList<Integer>> nbSeanceParRessource = new HashMap<>();

    /** Constructeur par défaut. */
    public Edt() {}
    

    /**
 * getSemaine : méthode publique.
 * @return résultat de type Semaine[]
 */
public Semaine[] getSemaine() {
		return semaine;
	}


	/**
 * getGroupes : méthode publique.
 * @return résultat de type ArrayList<Groupe>
 */
public ArrayList<Groupe> getGroupes() {
		return groupes;
	}


	/**
 * getRessources : méthode publique.
 * @return résultat de type ArrayList<Ressource>
 */
public ArrayList<Ressource> getRessources() {
		return ressources;
	}


	/**
 * getEnseignants : méthode publique.
 * @return résultat de type ArrayList<Enseignant>
 */
public ArrayList<Enseignant> getEnseignants() {
		return enseignants;
	}


	public ArrayList<Creneau<? extends Seance>> getCreneaux() {
		return creneaux;
	}


	public HashMap<String, ArrayList<Integer>> getNbSeanceParRessource() {
		return nbSeanceParRessource;
	}


	/**
     * Définit la liste des ressources utilisées dans l'emploi du temps.
     *
     * @param r une liste de ressources.
     */
    /**
 * setRessource : méthode publique.
 * @param r paramètre de type ArrayList<Ressource>
 */
public void setRessource(ArrayList<Ressource> r) {
        ressources = r;
    }

    /**
     * Définit la liste des enseignants.
     *
     * @param e une liste d'enseignants.
     */
    /**
 * setEnseignant : méthode publique.
 * @param e paramètre de type ArrayList<Enseignant>
 */
public void setEnseignant(ArrayList<Enseignant> e) {
        enseignants = e;
    }

    /**
     * Définit la liste des groupes.
     *
     * @param g une liste de groupes.
     */
    /**
 * setGroupe : méthode publique.
 * @param g paramètre de type ArrayList<Groupe>
 */
public void setGroupe(ArrayList<Groupe> g) {
        groupes = g;
    }

    /**
     * Définit la liste des créneaux horaires.
     *
     * @param c une liste de créneaux.
     */
    /**
 * setCreneau : méthode publique.
 * @param c paramètre de type ArrayList<Creneau<? extends Seance>>
 */
public void setCreneau(ArrayList<Creneau<? extends Seance>> c) {
        creneaux = c;
    }

    /**
     * Définit le tableau des semaines.
     *
     * @param s un tableau de semaines (max 52).
     * @throws Exception si le tableau contient plus de 52 semaines.
     */
    /**
 * setSemaine : méthode publique.
 * @param s paramètre de type Semaine[]
 */
public void setSemaine(Semaine[] s) throws Exception {
        if (s.length <= 52) {
            for (int i = 0; i < s.length; i++) {
                semaine[i] = s[i];
            }
        } else {
            throw new Exception("Tableau de semaines trop grand (max 52).");
        }
    }
	/**
	 * Définit la table de correspondance entre les ressources et leurs séances associées.
	 * Une copie profonde est effectuée pour éviter toute modification externe de la structure.
	 *
	 * @param nbSeanceParRessource Une {@code HashMap} associant les noms de ressources
	 *                             à des listes d'indices de créneaux (séances).
	 */
	/**
 * setNbSeanceParRessource : méthode publique.
 * @param nbSeanceParRessource paramètre de type ArrayList<Integer>>
 */
public void setNbSeanceParRessource(HashMap<String, ArrayList<Integer>> nbSeanceParRessource) {
	    this.nbSeanceParRessource = new HashMap<>(); 
	    for (String key : nbSeanceParRessource.keySet()) {
	         ArrayList<Integer> listeOriginale = nbSeanceParRessource.get(key);
	        ArrayList<Integer> nouvelleListe = new ArrayList<>(listeOriginale);
	        this.nbSeanceParRessource.put(key, nouvelleListe);
	    }
	}
	
	/**
	 * Méthode principale permettant de lancer le programme depuis le terminal.
	 *
	 * Elle prend en charge différentes commandes selon les arguments fournis :
	 * <ul>
	 *   <li>{@code creer} : Génère un emploi du temps à partir de fichiers de configuration.</li>
	 *   <li>{@code verifier} : Vérifie la cohérence d’un emploi du temps existant.</li>
	 *   <li>{@code voir} : Affiche l’emploi du temps dans le terminal pour une semaine donnée (optionnellement filtrée par groupe).</li>
	 *   <li>{@code web} : Lance un serveur web interactif pour visualiser l’emploi du temps dans un navigateur.</li>
	 * </ul>
	 *
	 * Pour plus de détails sur les options de chaque commande, voir :
	 * <ul>
	 *   <li>{@link #creerEmploiDuTemps(String[])} pour {@code creer}</li>
	 *   <li>{@link #verifierEmploiDuTemps()} pour {@code verifier}</li>
	 *   <li>{@link #voirEmploiDuTemps(String[])} pour {@code voir}</li>
	 *   <li>{@link #lancerServeurWeb(String[])} pour {@code web}</li>
	 * </ul>
	 *
	 * @param args Les arguments de la ligne de commande.
	 */
    /**
 * main : méthode statique publique.
 * @param args paramètre de type String[]
 */
public static void main(String[] args) {
        if (args.length < 1) { 
            afficherAide();
            return;
        }
         

        String commande = args[0];

        switch (commande) {
            case "creer":
            	
            	
            	try {
            		creerEmploiDuTemps(args);
                   }catch (Exception e) {
                	   System.out.println(e.getMessage());
                   }
            	break;
                

            case "verifier": 
            	
			try {
				verifierEmploiDuTemps(args);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
                break;
                

            case "voir": {
               try {
                voirEmploiDuTemps(args);
               }catch (Exception e) {
            	   System.out.println(e.getMessage());
               }
                break;
            }


            case "web":
            	try {
            		lancerServeurWeb(args);
            	}catch (Exception e) {
            		System.out.println(e.getMessage());
            	}
                break;

            default:
                System.out.println("Commande inconnue : " + commande);
                afficherAide();
        }
        
        

  
        }
    /**
     * Vérifie qu'un chemin donné existe et correspond à un fichier régulier.
     *
     * @param chemin Le chemin du fichier à vérifier.
     * @throws Exception Si le fichier n'existe pas ou s'il ne s'agit pas d'un fichier.
     */
    static void verifierFichier(String chemin) throws Exception {
        File f = new File(chemin);
        if (!f.exists()) {
            throw new Exception("Fichier introuvable : " + chemin);
        }
        if (!f.isFile()) {
            throw new Exception("Le chemin n'est pas un fichier : " + chemin);
        }
    }
    /**
     * Méthode récursive qui supprime un fichier ainsi que tout ce qu'il y a dedans.
     * La méthode vérifie si le chemin est un dossier ; si c'est le cas, elle appelle récursivement 
     * la méthode pour chaque fichier dans le dossier. Sinon, elle supprime directement le fichier.
     * 
     * @param dossier Le fichier ou dossier à supprimer.
     */
    /**
 * supprimerDossierRecursif : méthode statique publique.
 * @param dossier paramètre de type File
 */
public static void supprimerDossierRecursif(File dossier) {
        if (dossier.isDirectory()) {
            File[] fichiers = dossier.listFiles();
            if (fichiers != null) {
                for (File f : fichiers) { 
                    supprimerDossierRecursif(f);
                }
            }
        }
        dossier.delete(); 
    }  
    /**
     * Supprime tout ce qui se trouve après la dernière occurrence d'un caractère donné dans une chaîne.
     *
     * @param str La chaîne à traiter.
     * @param c Le caractère de référence.
     * @return La sous-chaîne allant du début jusqu'au dernier caractère {@code c} (exclu).
     *         Si le caractère {@code c} n'est pas présent, la chaîne d'origine est retournée.
     */

public static String removeAfterLastChar(String str, char c) { 
        int lastIndex = str.lastIndexOf(c);
        if (lastIndex == -1) {
            return str; // Le caractère n'est pas trouvé, on retourne la chaîne  originale
        }
        return str.substring(0, lastIndex);
    }
    /**
     * Affiche l'aide pour l'utilisation du programme dans le terminal.
     *
     * Liste les commandes disponibles pour manipuler l'emploi du temps :
     * <ul>
     *   <li>{@code creer} : Génère un emploi du temps à partir de fichiers de configuration.</li>
     *   <li>{@code verifier} : Vérifie la cohérence d’un emploi du temps existant.</li>
     *   <li>{@code voir} : Affiche l’emploi du temps dans le terminal.</li>
     *   <li>{@code web} : Lance un serveur web pour visualiser l’emploi du temps dans un navigateur.</li>
     * </ul>
     */
    private static void afficherAide() {
        System.out.println("Utilisation : edt <commande>");
        System.out.println("Commandes disponibles :");
        System.out.println("  creer      - Création d'un nouvel emploi du temps");
        System.out.println("  verifier   - Vérification d'un emploi du tempvoirs existant");
        System.out.println("  voir       - Visualisation de l'emploi du temps dans le terminal");
        System.out.println("  web        - Lance un serveur web interactif pour visualiser l'emploi du temps");
    }
    /**
     * Méthode utilitaire pour extraire un chemin (fichier ou dossier) depuis les arguments de la ligne de commande.
     *
     * @param args Tableau des arguments de la ligne de commande.
     * @param i Index actuel dans le tableau {@code args}, correspondant à l'option (--cfg, --dst, etc.).
     * @param nomOption Le nom de l'option attendue (à des fins de message d'erreur).
     * @param Dossier Indique si le chemin attendu doit être un dossier ({@code true}) ou un fichier ({@code false}).
     * @return Le chemin validé (ajoute un "/" final s’il s’agit d’un dossier sans slash terminal).
     * @throws Exception Si l'argument suivant est manquant ou si le chemin est invalide (inexistant ou de mauvais type).
     */
    private static String chercheChemin(String[] args, int i, String nomOption, boolean dossier) throws Exception {
        if (i + 1 >= args.length) {
            throw new Exception("Mauvais argument pour " + nomOption);
        }
        String chemin = args[++i];
        File f = new File(chemin);
        if (!f.exists()) {
        	if(dossier) {
        		System.out.println("Le dossier "+chemin+"n'existe pas il sera alors créé");
        		f.mkdirs();
        	}else {
            throw new Exception("Le chemin pour " + nomOption + " n'existe pas : " + chemin);
        	}
        }
        if (dossier && !f.isDirectory()) {
            throw new Exception("Le chemin pour " + nomOption + " doit être un dossier : " + chemin);
        }
        if (!dossier && !f.isFile()) {
            throw new Exception("Le chemin pour " + nomOption + " doit être un fichier : " + chemin);
        }
        if(dossier) {
        	if(!chemin.endsWith("/")) {
        		chemin += "/";
        	}
        }
        return chemin;
    }
  
   
    /**
     * Gère la création d’un emploi du temps à partir de fichiers de configuration.
     * 
     * Cette méthode lit les fichiers de configuration nécessaires (groupes, enseignants,
     * ressources, horaires), génère un emploi du temps, puis l’exporte dans un dossier de destination.
     * 
     * Options acceptées :
     * 
     * - --cfg <config-directory> :
     *   Spécifie le dossier contenant les fichiers :
     *   enseignants.txt, groupes.txt, horaires.txt (ou .toml), ressources.txt (ou .toml).
     *   Si cette option est omise, le dossier par défaut est ~/.config/edt/
     * 
     * - --dst <edt-directory> :
     *   Dossier de destination pour les fichiers 01.txt à 52.txt (une semaine par fichier).
     *   Si omis, un dossier basé sur l’un des fichiers fournis est utilisé.
     * 
     * - --force :
     *   Si activé, supprime les fichiers présents dans le dossier de destination avant création.
     * 
     * - --groupe <fichier> :
     *   Fichier personnalisé pour les groupes (remplace groupes.txt).
     * 
     * - --enseignant <fichier> :
     *   Fichier personnalisé pour les enseignants (remplace enseignants.txt).
     * 
     * - --ressource <fichier> :
     *   Fichier personnalisé pour les ressources (remplace ressources.txt).
     * 
     * - --horaire <fichier> :
     *   Fichier personnalisé pour les créneaux horaires (remplace horaires.txt).
     * 
     * Si aucun fichier personnalisé n'est spécifié, tous les fichiers sont chargés depuis --cfg (ou son dossier par défaut).
     * 
     * @param args les arguments de la ligne de commande
     * @throws Exception si des arguments sont invalides, des fichiers manquants ou une incohérence de configuration
     */

    private static void creerEmploiDuTemps(String[] args) throws Exception {
        String ressource = "/ressources.txt";
        String horaire = "/horaires.txt";
        String groupe = "/groupes.txt";
        String enseignant = "/enseignants.txt";
        String srcBase="~/.config/edt/planning/";
        String cfgBase = ".config/edt/";
        int ttPreci = 0;
        boolean force = false;
        boolean cfg = false;
        boolean src = false;

        try {
            for (int i = 1; i < args.length; i++) {
                switch (args[i]) {
                    case "--ressource":
                        ttPreci++;
                        ressource = chercheChemin(args, i, "--ressource", false);
                        i++;
                        break;

                    case "--horaire":
                        ttPreci++;
                        horaire = chercheChemin(args, i, "--horaire", false);
                        i++;
                        break;

                    case "--groupe":
                        ttPreci++;
                        groupe = chercheChemin(args, i, "--groupe", false);
                        i++;
                        break;

                    case "--enseignant":
                        ttPreci++;
                        enseignant = chercheChemin(args, i, "--enseignant", false);
                        i++;
                        break;
                        
                    case "--src":
                        srcBase = chercheChemin(args, i, "--src",true);
                        src = true;
                        i++;
                        break;

                    case "--cfg":
                        cfgBase = chercheChemin(args, i, "--cfg",true);
                        cfg = true;
                        i++;
                        break;


                    case "--force":
                        force = true;
                        break;

                    default:
                        throw new Exception("Mauvais argument : " + args[i]);
                }
            }
        } catch (Exception e) {
            throw e;
        }
    



        // Si aucun fichier précisé, on  les charge depuis cfgBase
        if (ttPreci==0 && cfg) {
            ressource = cfgBase + ressource;
            horaire = cfgBase + horaire;
            groupe = cfgBase + groupe;
            enseignant = cfgBase + enseignant;
        }
        else if (ttPreci==4 && cfg) { 
        	System.err.println("Certain arguments précis on était donné ainsi que  le dossier où tout les fichiers sont présents. Par defaut les fichier précis seront utiliser");
        	cfg=true;
        }
        else if(ttPreci!=0 &&  ttPreci!=4 )  {  
        	if (cfg) {
        		System.err.println("Certain argument précis on était donné, le fichier --cfg sera utilisé");
        		cfg=true;
        	}else {	
        		if(new File(cfgBase).exists()) {
        			System.err.println("Rien n'est précisé alors le fichier "+cfgBase+"/planning sera utiliser");
        			cfg=true;
        		}else {
        			throw new Exception ("Rien n'est préciser et le fichier "+cfgBase+" n'existe pas. L'emploi du temps ne sera pas créer");
        		}
        	}
        } 
        if(ttPreci==4)cfg=true;
        if(!cfg) {
        	throw new Exception("Aucune fichier ou de dossier de configuration n'a été préciser");
        }
        if (!src) {
        	System.err.println("Aucun fichier pour exporter l'emploi du temps n'a était préciser");
        	if (ttPreci==4) {
        		srcBase=removeAfterLastChar(ressource,'/') ;
        		System.err.println("On prendra le dossier de ressource par defaut "+srcBase);
        	}else {
        		System.err.println("Le fichier "+srcBase+" sera alors utilisé");
        	}
        	  
        }


        // Vérification de l'existence des fichiers
        verifierFichier(ressource);
        verifierFichier(enseignant);
        verifierFichier(groupe);
        verifierFichier(horaire);
        File f=new File(srcBase);
        if(force && f.exists() ) {
        	supprimerDossierRecursif(f);
        }
        else if(f.exists()) {
        	boolean existe=false; 
        	for (int i=0;i<52;i++) {
        		String fichier= srcBase+ String.format("%02d.txt", i + 1) ;
        		File file=new File(fichier);
        		if(file.exists()) {
        			existe=true;
        			break;
        		}
        	}
        	if(existe) {
        		System.err.println("Vous créer un emploi du temps dans un dossier contennat déja un emploi du temps .Vous pourvez utiliser l'option --force pour contourner ce problème , par contre ça suprimera les fichier présent actuellement");
        		throw new Exception ("Les fichier en train d'être créer existe déja"); 
        	}
        }
        // Construction de l'emploi du temps
        Edt edt = new Edt();
        edt.setEnseignant(edt.listeEnseignant(enseignant));
        edt.setGroupe(edt.ListGroupe(groupe));
        HashMap<String, ArrayList<Integer>> nbSeanceParRessourceRef=edt.importNbSeanceRessource(ressource);
        edt.setNbSeanceParRessource(cloneNbSeanceParRessource(nbSeanceParRessourceRef));
        edt.setRessource(edt.LectureRessource(new File(ressource)));
        edt.setCreneau(edt.importCreneau(horaire));
        edt.generer();
        verifier(null, edt.importNbSeanceRessource(ressource),edt.ListGroupe(groupe), edt.listeEnseignant(enseignant),edt.LectureRessource(new File(ressource)), edt.semaine);
        edt.exporter(srcBase,null,null);
         
        

    }
    /**
     * Vérifie la validité d'un emploi du temps à partir de fichiers de configuration et d'une semaine cible.
     * <p>
     * Cette méthode prend en paramètre les arguments de la ligne de commande pour localiser les fichiers
     * contenant les informations sur les ressources, les groupes, les enseignants, et éventuellement
     * la semaine à vérifier. Elle importe les données correspondantes, charge l'emploi du temps et vérifie
     * sa conformité en comparant le nombre de séances par ressource, la répartition des enseignants et des groupes.
     * <p>
     * Les options de la ligne de commande attendues sont :
     * <ul>
     *   <li><code>--ressource &lt;chemin&gt;</code> : fichier contenant les ressources et le nombre de séances.</li>
     *   <li><code>--groupe &lt;chemin&gt;</code> : fichier contenant les groupes.</li>
     *   <li><code>--enseignant &lt;chemin&gt;</code> : fichier contenant les enseignants.</li>
     *   <li><code>--cfg &lt;chemin&gt;</code> : dossier de configuration contenant les fichiers par défaut.</li>
     *   <li><code>--dst &lt;chemin&gt;</code> ou <code>--src &lt;chemin&gt;</code> : dossier contenant les fichiers de semaine.</li>
     *   <li>Un entier optionnel indiquant le numéro de la semaine à vérifier.</li>
     * </ul>
     * <p>
     * Si les fichiers ne sont pas fournis via les arguments, la méthode utilise les chemins par défaut
     * dans <code>~/.config/edt/</code>.
     *
     * @param args les arguments passés en ligne de commande.
     * @throws Exception en cas d'erreur de lecture des fichiers ou de format incorrect des arguments.
     */
    private static void verifierEmploiDuTemps(String[] args) throws Exception{
    	try {
    	
    	String ressourceFile = "";
        String groupeFile = "";
        String enseignantFile = "";
        Integer semaineCible = null;
        String srcBase="~/.config/edt/planning/";
        String cfgBase="~/.config/edt/";
        HashMap<String, ArrayList<Integer>> nbSeanceParRessource =null;
        ArrayList<Groupe> groupes = null;
        ArrayList<Enseignant> enseignants = null;
        ArrayList<Ressource> ressources = null;
        boolean src=false;
        boolean cfg=false;
        int ttPreci=0;

        // Lecture des arguments
        for (int i = 1; i < args.length; i++) {
            switch (args[i]) {
                case "--ressource":
                	
                    ressourceFile = chercheChemin(args, i, "--ressource", false);
                    ttPreci++;
                    i++;
                    break;
                case "--groupe":
                    groupeFile = chercheChemin(args, i, "--groupe", false);
                    ttPreci++;
                    i++;
                    break;
                case "--enseignant":
                    enseignantFile = chercheChemin(args, i, "--enseignant", false);
                    ttPreci++;
                    i++;
                    break;
                case "--src":
                	srcBase = chercheChemin(args, i, "--src",true);
                    src=true;
                    i++;
                    break;
                case "--cfg":
                	cfgBase = chercheChemin(args, i, "--cfg",true);

                    cfg=true;
                    i++;
                    break;
                 
                default:
                    try {
                        semaineCible = getStringValue(args[i], true);
                    } catch (Exception e) {
                        throw new Exception("Mauvais arguments : " + args[i]);
                    }
            }
            
        
        }
        if(!src&&!cfg&& ttPreci!=3) {
        	System.out.println("Aucun fichier ni dossier n'a été donné le fichier de configuration sera "+cfgBase+" et le dossier contenant les semaine sera "+srcBase);
        	verifierFichier(cfgBase);
        	verifierFichier(srcBase);
        }
        if(!cfg && ttPreci!=3) {
        	System.out.println("Le dossier contenant les fichier de configration n'a pas été donné, le dossier "+cfgBase+" sera utilsé");
        	verifierFichier(cfgBase);
        }
        
        if(!src&& cfg) {
        	
        	srcBase=cfgBase+"planning/";
        	System.out.println("Le dossier contenant les semaine n'a pas été donné le fichier "+srcBase+" sera utilisé");
        	src=true;
        }
        
        Edt edt = new Edt();
        edt.importer(srcBase);
        Semaine[] semaine=edt.semaine;
        if (ttPreci!=3) {
        	ressourceFile=cfgBase+"ressources.txt";
        	enseignantFile=cfgBase+"enseignants.txt";
        	groupeFile=cfgBase+"groupes.txt";
        }
        
        if (!ressourceFile.isEmpty()) {
        	try {
            verifierFichier(ressourceFile);
            nbSeanceParRessource = edt.importNbSeanceRessource(ressourceFile);
            ressources = edt.LectureRessource(new File(ressourceFile));
        	}catch(Exception e) {
        		nbSeanceParRessource=null;
        		ressources=null;
        	}
            
         
        }  
        if (!enseignantFile.isEmpty()) {
        	try {
            verifierFichier(enseignantFile);
            enseignants = edt.listeEnseignant(enseignantFile);
        }catch(Exception e) {
    		nbSeanceParRessource=null;
    		ressources=null;
    	}
           
        }

        if (!groupeFile.isEmpty()) {
        	try {
            verifierFichier(groupeFile);
            groupes = edt.ListGroupe(groupeFile);
        	}catch(Exception e) {
        		nbSeanceParRessource=null;
        		ressources=null;
        	}
            
        }
        
        if(verifier(semaineCible, nbSeanceParRessource ,groupes ,enseignants, ressources,semaine)) {
        	System.out.print("L'emploi du temps est bon");
        	if (semaineCible!=null) {
        		System.out.print(" pour la semaine "+semaineCible);
        	}
        	System.out.print("\n");
        }else {
        	System.out.print("L'emploi du temps est mauvais ");
        	if (semaineCible!=null) {
        		System.out.print(",pour la semaine"+semaineCible);
        	}
        	System.out.print(".\n");
        }
    	}catch(Exception e){
    		throw e;
    		
    	}
    }
    /**
     * Gère les arguments passés en ligne de commande pour afficher un emploi du temps.
     * 
     * Cette méthode utilise les paramètres pour déterminer quelle semaine et éventuellement
     * quel groupe afficher, et appelle la méthode {@code voir()} de l'objet {@code Edt}.
     * 
     * Arguments possibles :
     * --cfg <chemin>    : chemin vers le dossier de configuration des semaines (optionnel)
     * --groupe <nom>    : nom du groupe à afficher (optionnel)
     * <numéro_semaine>  : numéro de la semaine à afficher (obligatoire)
     * 
     * Exemple :
     * ./edt voir --cfg doc/exemples-fichiers/config/planning/ --groupe G1 6
     * 
     * @param args les arguments passés au programme
     * @throws Exception en cas d'erreur de syntaxe ou d'arguments manquants/invalides
     */
    private static void voirEmploiDuTemps(String[] args) throws Exception {
    	 Integer nbSemaine = null;
         String groupe = "";
         String cfgBase ="~/.config/edt/";
         String enseignant="";
         String ressource="";
         for (int i=1;i<args.length;i++) {       // On parcourt les arguments après "voir"
        	 switch (args[i]) {	 
        	 case "--cfg":
                 cfgBase = chercheChemin(args, i, "--cfg",true);
                 i++;
                 break;
        	 case "--groupe":
	        	 if (i + 1 >= args.length) {
	                 throw new Exception("Pas de groupe après --groupes");
	             } else {
	                 i++;
	                 groupe = args[i];
	             }
	             break;
        	 case "--enseignant":
        		 if (i + 1 >= args.length) {
	                 throw new Exception("Pas de d'enseignant après --enseignant");
	             } else {
	                 i++;
	                 enseignant = args[i];
	             }
        		 break;
        	 case "--ressource":
        		 if (i + 1 >= args.length) {
	                 throw new Exception("Pas de ressource après --ressource.");
	             } else {
	                 i++;
	                 ressource = args[i];
	             }
        		 break;
        	 case "--semaine":
        		 i++;
        	 default:
	        	  String s=args[i];
	        	  try {
	        	  nbSemaine=Integer.parseInt(s); 
	        	  }catch (NumberFormatException e) {
	        		  throw e;
	        	  }
	         }
         }
         
         
         if (cfgBase.equals("~/.config/edt/")) {
        	 System.out.println("Aucun fichier de cfg n'a été donné le dossier"+cfgBase);
        	 verifierFichier(cfgBase);
         }

         if (nbSemaine == null) {
             System.out.println("Il est nécessaire d'indiquer une semaine à afficher.");
         } else {
        	 Edt edt = new Edt();
             try {
                 String resultat;
                 if (groupe.isEmpty() &&ressource.isEmpty() && enseignant.isEmpty()) {
                	 resultat = edt.voir(edt.lectureSemaine(cfgBase),nbSemaine );
                 } else {
                     resultat = edt.voir(edt.lectureSemaine(cfgBase),nbSemaine,groupe,ressource,enseignant);
                 }
                 System.out.println(resultat);
             } catch (Exception e) {
                 e.printStackTrace();
             }
         }
        
    }

    /**
     * Lance un serveur web local pour afficher l'emploi du temps et tente d'ouvrir automatiquement
     * le navigateur avec l'URL générée.
     * 
     * L'URL peut être personnalisée à l'aide des options passées en argument :
     * 
     * Exemple d'utilisation :
     * ./edt web --semaine 6 --groupe 4
     * 
     * Si l'ouverture automatique du navigateur échoue, l'URL est affichée dans la console.
     * 
     * @param options Les options de ligne de commande, incluant :
     *                --semaine <numéro> : pour spécifier la semaine à afficher
     *                --groupe <numéro>  : pour spécifier le groupe à afficher
     * 
     * @throws Exception en cas de mauvaise syntaxe ou d'erreur lors de l'ouverture du navigateur
     */
    //edt web [--cfg <config-directory>] [--semaine <semaine>] [--groupe <group>] [--enseignant <enseignant>] [--ressource <ressource>] [--port <port>]
    private static void lancerServeurWeb(String[] options) {
        try {
            // Valeurs par défaut
            int semaineVoulu = 1;
            boolean semaineDonnee = false;
            boolean cfgDonne = false;
            String groupe = null;
            String dossierCfg = "~/.config/edt/";
            String enseignant = null;
            String ressource = null;
            int port = 2025;

            // Analyse des options
            for (int i = 1; i < options.length; i++) {
                switch (options[i]) {
                    case "--groupe":
                        if (i + 1 < options.length) groupe = options[++i];
                        break;
                    case "--enseignant":
                        if (i + 1 < options.length) enseignant = options[++i];
                        break;
                    case "--ressource":
                        if (i + 1 < options.length) ressource = options[++i];
                        break;
                    case "--port":
                        if (i + 1 < options.length) {
                            port = getStringValue(options[++i], false);
                            Main.port = port;
                        }
                        break;
                    case "--cfg":
                        if (i + 1 < options.length) {
                            dossierCfg = chercheChemin(options, i++, "--cfg", true);
                            cfgDonne = true;
                        }
                        break;
                    case "--semaine":
                        if (i + 1 < options.length) {
                            semaineVoulu = getStringValue(options[++i], false);
                            semaineDonnee = true;
                        }
                        break;
                    default:
                        if (!semaineDonnee) {
                            semaineVoulu = getStringValue(options[i], false);
                            semaineDonnee = true;
                        } else {
                            throw new Exception("Plus d'un argument sans option détecté. Il ne peut y avoir qu'un seul argument sans option (le numéro de la semaine).");
                        }
                        break;
                }
            }

            // Messages d'information
            System.out.println("Lancement du serveur web pour l'emploi du temps...");
            System.out.println("Pour se rendre sur l'emploi du temps : http://127.0.0.1:" + port + "/");
            if (!cfgDonne) {
                System.out.println("Le dossier de configuration n'a pas été donné, le dossier " + dossierCfg + " sera utilisé.");
            }
            if (!semaineDonnee) {
                System.out.println("Aucune semaine n’a été donnée, la semaine " + semaineVoulu + " sera utilisée par défaut.");
            }

            // Chargement des fichiers de configuration
            String ressourceBase = dossierCfg + "/ressources.txt";
            String horaireBase = dossierCfg + "/horaires.txt";
            String groupeBase = dossierCfg + "/groupes.txt";
            String enseignantBase = dossierCfg + "/enseignants.txt";
            verifierFichier(ressourceBase);
            verifierFichier(enseignantBase);
            verifierFichier(groupeBase);
            verifierFichier(horaireBase);

            // Initialisation de l'emploi du temps
            Edt edt = new Edt();
            edt.setEnseignant(edt.listeEnseignant(enseignantBase));
            edt.setGroupe(edt.ListGroupe(groupeBase));
            HashMap<String, ArrayList<Integer>> nbSeanceParRessourceRef = edt.importNbSeanceRessource(ressourceBase);
            edt.setNbSeanceParRessource(cloneNbSeanceParRessource(nbSeanceParRessourceRef));
            edt.setRessource(edt.LectureRessource(new File(ressourceBase)));
            edt.setCreneau(edt.importCreneau(horaireBase));
            edt.generer();

            // Recherche de la semaine
            Semaine semaine = null;
            for (Semaine s : edt.getSemaine()) {
                if (s.getNumero() == semaineVoulu) {
                    semaine = s;
                    break;
                }
            }
            if (semaine == null) {
                throw new Exception("La semaine cherchée " + semaineVoulu + " n'existe pas.");
            }

            CustomHandler.edt = edt;

            // Lancement du serveur
            Main.main(new String[0]);

            // Construction de l'URL
            StringBuilder urledt = new StringBuilder("http://127.0.0.1:" + port + "/?");
            urledt.append("semaine=").append(semaineVoulu);
            if (groupe != null){urledt.append("&groupe=").append(groupe);}
            if (enseignant != null) urledt.append("&enseignant=").append(enseignant);
            if (ressource != null) urledt.append("&ressource=").append(ressource);
            urledt.append("&cfg="+dossierCfg);

            // Ouverture du navigateur

            try {
                if (Desktop.isDesktopSupported()) {
                    Desktop desktop = Desktop.getDesktop();
                    if (desktop.isSupported(Desktop.Action.BROWSE)) {
                        desktop.browse(new URI(urledt.toString()));
                    } else {
                        System.out.println("L'action BROWSE n'est pas supportée. Ouvrez l'URL manuellement : " + urledt);
                    }
                } else {
                    System.out.println("Desktop non supporté. Ouvrez l'URL manuellement : " + urledt);
                }
            } catch (Exception e) {
                System.out.println("Erreur lors de l'ouverture automatique du navigateur.");
                System.out.println("Ouvrez l'URL manuellement : " + urledt);
                System.err.println("Détail de l'erreur : " + e.getMessage());
            }

            System.out.println("Serveur lancé avec succès.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Exporte l'emploi du temps en créant un fichier pour chaque semaine (de 01.txt à 52.txt) dans le dossier spécifié.
     * Si le dossier n'existe pas, il est automatiquement créé.
     * Les semaines exportées correspondent à l'état actuel de l'emploi du temps dans cette instance.
     *
     * @param path Le chemin du dossier dans lequel enregistrer les fichiers hebdomadaires.
     */
 

public void exporter(String chemin,Integer nbSemaine,String groupe)throws Exception {
    	try {
        if (!chemin.endsWith(File.separator)) {
        	chemin += File.separator;
        }

        File dossier = new File(chemin);
        if (!dossier.exists()) {
            dossier.mkdirs();
        }
        if(groupe==null) {
        for (int i = 0; i < semaine.length; i++) {
        	if (nbSemaine != null && semaine[i].getNumero() != nbSemaine) continue;
            String nomFichier = chemin + String.format("%02d.txt", i + 1);
            ecritureExport(nomFichier,null,i);
        }
        }else if(groupe!=null && nbSemaine!=null){
        	for (int i = 0; i < semaine.length; i++) {
        		if (semaine[i].getNumero() != nbSemaine) continue;
        		String nomFichier = chemin +"table-sem"+nbSemaine+"-"+groupe+".txt";
        		ecritureExport(nomFichier,groupe,i);
        		 
        }
    }
    	}catch (Exception e) {
    		throw e;
    	}
    }
    private void ecritureExport(String nomFichier,String groupe,int i)throws Exception {
    	File file = new File(nomFichier);
		try (FileWriter writer = new FileWriter(file)) {
            if (semaine[i] != null) {
            	if (groupe!=null) {
                writer.write(voir(semaine, semaine[i].getNumero(),groupe,"",""));
            	}else {
            	writer.write(voir(semaine, semaine[i].getNumero()));
            	}
            } else {
                writer.write("");
            }
            System.out.println("Fichier écrit : " + nomFichier);
        } catch (IOException e) {
            throw e;
        }
	
    }


    /**
     * Importe les données de l'emploi du temps à partir d’un dossier contenant les fichiers hebdomadaires.
     * Chaque fichier doit représenter une semaine. Les fichiers doivent être nommés de 01.txt à 52.txt.
     *
     * @param path Le chemin du dossier contenant les fichiers d'emploi du temps.
     * @throws Exception Si le dossier n'existe pas ou si une erreur survient lors de la lecture.
     */
    /**
 * importer : méthode publique.
 * @param path paramètre de type String
 */
public void importer(String path)throws Exception {
    	try {
    	if(new File(path).exists()) {
    		semaine=lectureSemaine(path);
    		
    		
    	}
    	else {
    		throw new Exception ("Le dossier "+path+" n'existe pas");
    	}
    	}catch (Exception e) {
			throw e;
			}
    	
    }
/**
 * Génère l'emploi du temps complet pour l'année en remplissant un tableau de 52 semaines,
 * en plaçant les séances de CM et de TD selon les ressources disponibles et les créneaux définis.
 * 
 * Cette méthode met à jour les semaines de l'objet courant en fonction des ressources, des groupes, 
 * et du nombre de séances prévues par ressource.
 * 
 * @throws Exception si une ressource indiquée dans le nombre de séances n'existe pas dans la liste des ressources
 *                   ou si des séances prévues ne peuvent pas être placées (manque de créneaux).
 */

public void generer() throws Exception {
    try {
        int nbGroupe = groupes.size();
        HashMap<String, ArrayList<Integer>> nbSeanceParRessource = multToutId(cloneNbSeanceParRessource(this.nbSeanceParRessource), 1, 0.5f); // 1 séance TD = 2h

        ArrayList<HashMap<String, ArrayList<Integer>>> nbSeanceParRessourceParGroupe = new ArrayList<>();
        HashMap<String, ArrayList<Integer>> nbSeanceParRessourceRef = cloneNbSeanceParRessource(nbSeanceParRessource);
        Semaine[] semaines = new Semaine[52];

        for (int i = 1; i < 53; i++) { // 52 semaines
            boolean CMCreer = false;
            boolean TDCreer = false;

            Semaine semaine = new Semaine(i);
            nbSeanceParRessourceParGroupe.clear();
            nbSeanceParRessource = cloneNbSeanceParRessource(nbSeanceParRessourceRef);

            for (int j = 0; j < nbGroupe; j++) {
                nbSeanceParRessourceParGroupe.add(cloneNbSeanceParRessource(nbSeanceParRessource));
            }

            for (int j = 0; j < 5; j++) { // 5 jours 
                Jour jour = new Jour(getNbJour(j));

                for (int k = 0; k < creneaux.size(); k++) {
                    Creneau<? extends Seance> creneauH = creneaux.get(k);
                    ArrayList<?> seances = creneauH.getSeance();

                    if (!seances.isEmpty()) {
                        Object firstSeance = seances.get(0);

                        // Cas CM
                        if (firstSeance instanceof CM) {
                        	
                            if (k <= 1) { 
                            	Creneau<CM> cmCreneau =  new Creneau<CM>(creneauH.getHeureDebut(), creneauH.getDuree());
                                for (String codeRessource : nbSeanceParRessource.keySet()) {
                                    if (nbSeanceParRessource.get(codeRessource).get(0) > 0) {
                                        Ressource ressource = trouverRessourceParCode(ressources, codeRessource);

                                        if (ressource == null) {
                                            throw new Exception("Code inexistant, les ressources données et le nombre de séances par ressource ne sont pas communs");
                                        } else if (ressource.getSemainesPrevues().contains(i)) {
                                        	
                                            cmCreneau.addSeance(new CM(ressource, ressource.getResponsable()));
                                            
                                            CMCreer = true;
                                            nbSeanceParRessource.get(codeRessource).set(0, nbSeanceParRessource.get(codeRessource).get(0) - 1);
                                            break;
                                         }
                                    }
                                }
                                jour.addCM(cmCreneau);
                            }

                        // Cas TD
                            
                        } else if (firstSeance instanceof TD) {
                        	Creneau<TD> tdCreneau = new Creneau<TD>(creneauH.getHeureDebut(), creneauH.getDuree());
                            if (k > 1) {  
                                ArrayList<Enseignant> enseignantsPris = new ArrayList<>();

                                for (int m = 0; m < nbGroupe; m++) {
                                    HashMap<String, ArrayList<Integer>> nbSeanceGroupe = nbSeanceParRessourceParGroupe.get(m);

                                    for (String codeRessource : nbSeanceGroupe.keySet()) {
                                        if (nbSeanceGroupe.get(codeRessource).get(1) != 0) {
                                            Ressource ressource = trouverRessourceParCode(ressources, codeRessource);

                                            if (ressource == null) {
                                                throw new Exception("Code inexistant, les ressources données et le nombre de séances par ressource ne sont pas communs");
                                            } else if (ressource.getSemainesPrevues().contains(i)) {
                                                Enseignant enseignantTD = ressource.getEnseignants().get(m);

                                                if (!enseignantsPris.contains(enseignantTD)) {
                                                    enseignantsPris.add(enseignantTD);
                                                    tdCreneau.addSeance(new TD(ressource, enseignantTD, groupes.get(m)));
                                                    TDCreer = true;
                                                    nbSeanceGroupe.get(codeRessource).set(1, nbSeanceGroupe.get(codeRessource).get(1) - 1);
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }

                                jour.addTD(tdCreneau);
                            }

                        } else {
                            throw new Exception("Autre type de séance : " + firstSeance.getClass().getSimpleName());
                        }
                    }
                }

                semaine.addJour(jour);
            }

            verifierSeancePlacee(nbSeanceParRessource,this.ressources,i, CMCreer,0);
            
            TDPlace(nbSeanceParRessourceParGroupe,this.ressources, i,TDCreer);

            semaines[i - 1] = semaine;
        }

        this.setSemaine(semaines);
    } catch (Exception e) {
        throw e;
    }
}
    

    /**
 * getListNomEnseignants : méthode statique publique.
 * @param enseignants paramètre de type ArrayList<Enseignant>
 * @return résultat de type ArrayList<String>
 */
public static ArrayList<String> getListNomEnseignants(ArrayList<Enseignant> enseignants){
    	ArrayList<String> res=new ArrayList<String>();
    	for(int i=0;i<enseignants.size();i++){
    		res.add(enseignants.get(i).getNom());
    		}
    	return res;
    	}
        /**
         * Retourne une description du problème pour un groupe TD.
         * @param td Le TD concerné
         * @return Chaîne de caractères décrivant le problème du groupe
         */
        private static String prblGrp(TD td) {
            return "  * " + td.getGroupe().getNom();
        }

        /**
         * Retourne une description du problème pour un enseignant.
         * @param s La séance concernée (CM ou TD)
         * @return Chaîne de caractères décrivant le problème de l'enseignant
         */
        private static String prblEnseignant(Seance s) {
            return "  * " + s.getEnseignant().getNom();
        }
        /**
 * verifier : méthode statique publique.
 * @param semaineCible paramètre de type Integer
 * @param nbSeanceParRessource paramètre de type ArrayList<Integer>>
 * @param groupes paramètre de type ArrayList<Groupe>
 * @param enseignants paramètre de type ArrayList<Enseignant>
 * @param ressources paramètre de type ArrayList<Ressource>
 * @param semaines paramètre de type Semaine[]
 * @return résultat de type boolean
 */
        public static boolean verifier(
        	    Integer semaineCible,
        	    HashMap<String, ArrayList<Integer>> nbSeanceParRessource,
        	    ArrayList<Groupe> groupes,
        	    ArrayList<Enseignant> enseignants,
        	    ArrayList<Ressource> ressources,
        	    Semaine[] semaines
        	) throws Exception {
        	    Edt edt = new Edt();
        	    boolean result = true;
        	    String report = "";

        	    HashMap<String, ArrayList<Integer>> nbSeanceParRessourceRef = new HashMap<>();
        	    if (nbSeanceParRessource != null) {
        	        nbSeanceParRessourceRef = cloneNbSeanceParRessource(nbSeanceParRessource);
        	    }

        	    ArrayList<String> nomsEnseignants = new ArrayList<>();
        	    if (enseignants != null) {
        	        for (Enseignant e : enseignants) {
        	            if (e != null && e.getNom() != null)
        	                nomsEnseignants.add(e.getNom().trim().toLowerCase());
        	        }
        	    }

        	    if (semaines != null) {
        	        for (int i = 0; i < semaines.length; i++) {
        	            Semaine s = semaines[i];
        	            if (s == null || (semaineCible != null && !semaineCible.equals(s.getNumero()))) {
        	                if (s == null) {
        	                    System.err.println("La semaine " + i + " n'est pas remplie");
        	                    result = false;
        	                }
        	                continue;
        	            }

        	            boolean semaineValid = true;
        	            boolean CMCreer = false;
        	            boolean TDCreer = false;

        	            String conflits = "## Conflits\n";
        	            String problemeRessource = "## Heure Manquante\n";
        	            String problemeGroupe = "## Problèmes Groupe\n";
        	            String problemeEnseignant = "## Problèmes Enseignant\n";

        	            nbSeanceParRessource = cloneNbSeanceParRessource(nbSeanceParRessourceRef);
        	            ArrayList<HashMap<String, ArrayList<Integer>>> nbSeanceParRessourceParGroupe = new ArrayList<>();
        	            if (groupes != null) {
        	                for (int j = 0; j < groupes.size(); j++) {
        	                    nbSeanceParRessourceParGroupe.add(cloneNbSeanceParRessource(nbSeanceParRessourceRef));
        	                }
        	            }

        	            for (Jour jour : s.getJours()) {
        	                if (jour == null) continue;

        	                // CM
        	                for (Creneau<CM> cmCreneau : jour.getCrenauxCM()) {
        	                    boolean conflitTemp = false;
        	                    for (CM cm : cmCreneau.getSeance()) {
        	                        if (cm == null) continue;
        	                        Enseignant enseignant = cm.getEnseignant();
        	                        Ressource ressource = cm.getRessource();

        	                        String nom = (enseignant != null && enseignant.getNom() != null) ? enseignant.getNom().trim().toLowerCase() : null;
        	                        if (enseignants != null && (nom == null || !nomsEnseignants.contains(nom))) {
        	                            semaineValid = false;
        	                            conflitTemp = true;
        	                            problemeEnseignant += prblEnseignant(cm) + " ,enseignant inconnu\n";
        	                        }

        	                        if (ressources != null && ressource != null) {
        	                            String code = ressource.getCode();
        	                            if (nbSeanceParRessource.containsKey(code)) {
        	                                int heuresRestantes = nbSeanceParRessource.get(code).get(0);
        	                                heuresRestantes -= cmCreneau.getDuree() / 60;
        	                                nbSeanceParRessource.get(code).set(0, heuresRestantes);
        	                                CMCreer = true;
        	                            }
        	                        }
        	                    }
        	                    if (conflitTemp) {
        	                        conflits += "  * " + jour.getJourSemaines() + " " +
        	                                    cmCreneau.getHeureDebut() + " - " +
        	                                    cmCreneau.getHeureDebut().ajouterMinutes(cmCreneau.getDuree()) + "\n";
        	                    }
        	                }

        	                // TD
        	                for (Creneau<TD> tdCreneau : jour.getCrenauxTD()) {
        	                    boolean conflitTemp = false;
        	                    ArrayList<String> groupesUtilises = new ArrayList<>();
        	                    ArrayList<String> enseignantsUtilises = new ArrayList<>();

        	                    for (TD td : tdCreneau.getSeance()) {
        	                        if (td == null) continue;
        	                        Groupe groupe = td.getGroupe();
        	                        Enseignant enseignant = td.getEnseignant();
        	                        Ressource ressource = td.getRessource();

        	                        String nom = (enseignant != null && enseignant.getNom() != null) ? enseignant.getNom().trim().toLowerCase() : null;
        	                        String nomGroupe = (groupe != null) ? groupe.getNom() : null;

        	                        if (nomGroupe != null && groupesUtilises.contains(nomGroupe)) {
        	                            semaineValid = false;
        	                            conflitTemp = true;
        	                            problemeGroupe += prblGrp(td) + " ,groupe en doublon\n";
        	                        } else if (nomGroupe != null) {
        	                            groupesUtilises.add(nomGroupe);
        	                        }

        	                        if (groupes != null && nomGroupe != null) {
        	                            ArrayList<String> listNomGroupes = new ArrayList<>();
        	                            for (Groupe g : groupes) {
        	                                if (g != null && g.getNom() != null)
        	                                    listNomGroupes.add(g.getNom());
        	                            }
        	                            if (!listNomGroupes.contains(nomGroupe)) {
        	                                problemeGroupe += "Le groupe " + nomGroupe + " n'existe pas.\n";
        	                            }

        	                            if (ressources != null && ressource != null) {
        	                                String code = ressource.getCode();
        	                                int id = groupes.indexOf(groupe);
        	                                if (id != -1 && id < nbSeanceParRessourceParGroupe.size()) {
        	                                    HashMap<String, ArrayList<Integer>> map = nbSeanceParRessourceParGroupe.get(id);
        	                                    if (map.containsKey(code)) {
        	                                        int heures = map.get(code).get(1);
        	                                        heures -= tdCreneau.getDuree() / 60;
        	                                        map.get(code).set(1, heures);
        	                                        TDCreer = true;
        	                                    }
        	                                }
        	                            }
        	                        }

        	                        if (enseignants != null && (nom == null || !nomsEnseignants.contains(nom))) {
        	                            semaineValid = false;
        	                            conflitTemp = true;
        	                            problemeEnseignant += prblEnseignant(td) + " ,enseignant inconnu\n";
        	                        }

        	                        if (nom != null && enseignantsUtilises.contains(nom)) {
        	                            semaineValid = false;
        	                            conflitTemp = true;
        	                            problemeEnseignant += prblEnseignant(td) + " ,enseignant doublé sur le créneau\n";
        	                        } else if (nom != null) {
        	                            enseignantsUtilises.add(nom);
        	                        }

        	                        if (ressource != null) {
        	                            TDCreer = true;
        	                        }
        	                    }

        	                    if (conflitTemp) {
        	                        conflits += "  * " + jour.getJourSemaines() + " " +
        	                                    tdCreneau.getHeureDebut() + " - " +
        	                                    tdCreneau.getHeureDebut().ajouterMinutes(tdCreneau.getDuree()) + "\n";
        	                    }
        	                }
        	            }

        	            if (nbSeanceParRessource != null && ressources != null) {
        	                try {
        	                    edt.verifierSeancePlacee(nbSeanceParRessource, ressources, i + 1, CMCreer, 0);
        	                    edt.TDPlace(nbSeanceParRessourceParGroupe, ressources, i + 1, TDCreer);
        	                } catch (Exception e) {
        	                    semaineValid = false;
        	                    for (String code : nbSeanceParRessource.keySet()) {
        	                        int idGroupe = 0;
        	                        int heuresCMRestantes = nbSeanceParRessource.get(code).get(0);
        	                        Ressource ressource = trouverRessourceParCode(ressources, code);
        	                        if (ressource != null && ressource.getSemainesPrevues().contains(i + 1) && heuresCMRestantes != 0 && CMCreer) {
        	                            problemeRessource += "  * " + code + " CM " + heuresCMRestantes + "h \n";
        	                        }
        	                        for (HashMap<String, ArrayList<Integer>> grpMap : nbSeanceParRessourceParGroupe) {
        	                            int heuresTDRestantes = grpMap.get(code).get(1);
        	                            if (ressource != null && ressource.getSemainesPrevues().contains(i + 1) && heuresTDRestantes != 0 && TDCreer) {
        	                                problemeRessource += "  * " + code + " TD-" + (groupes != null && idGroupe < groupes.size() ? groupes.get(idGroupe) : "?" ) + " " + heuresTDRestantes + "h \n";
        	                            }
        	                            idGroupe++;
        	                        }
        	                    }
        	                }
        	            }

        	            if (!semaineValid) {
        	                result = false;
        	                report += "\n# Semaine " + s.getNumero() + "\n" +
        	                          conflits + problemeRessource + problemeGroupe + problemeEnseignant;
        	            }
        	        }
        	    }

        	    if (result) {
        	        System.out.println("OK");
        	    } else {
        	        System.err.println(report);
        	    }

        	    return result;
        	}








    
    
    
    /**
     * Lit un fichier texte contenant une liste de noms d'enseignants et les convertit en objets {@code Enseignant}.
     *
     * Chaque ligne du fichier représente un enseignant. Seules les lignes contenant au moins 3 caractères sont prises en compte.
     *
     * @param nomFichier Le chemin du fichier texte contenant les noms des enseignants.
     * @return Une liste d'objets {@code Enseignant} correspondant aux lignes valides du fichier.
     */
	/**
 * listeEnseignant : méthode publique.
 * @param nomFichier paramètre de type String
 * @return résultat de type ArrayList<Enseignant>
 */
public ArrayList<Enseignant> listeEnseignant(String nomFichier){
		ArrayList<Enseignant> liste = new ArrayList<Enseignant>();
		try {
			FileReader fichier = new FileReader(nomFichier);
			BufferedReader reader = new BufferedReader(fichier);
			while(reader.ready()) {
				String professeur = reader.readLine();
				if(professeur.length()>2) {
					liste.add(new Enseignant(professeur));
				}
			}
			reader.close();
			fichier.close();
		}catch(FileNotFoundException e) {
			 System.err.println("Fichier non trouvé : " + nomFichier);
			e.printStackTrace();
		}catch(IOException e) {
			System.err.println("Erreur de lecture : " + nomFichier);
			e.printStackTrace();
		}
		return liste;
	}
	

	   
	
	
	/**
	 * Lit un fichier texte contenant des identifiants de groupes et retourne une liste d'objets {@code Groupe}.
	 *
	 * Chaque ligne du fichier doit contenir exactement 2 caractères pour être considérée comme valide (ex: "G1", "A2").
	 *
	 * @param nomFichier Le chemin du fichier texte contenant les noms des groupes.
	 * @return Une liste d'objets {@code Groupe} correspondant aux lignes valides du fichier.
	 */
	/**
 * ListGroupe : méthode publique.
 * @param nomFichier paramètre de type String
 * @return résultat de type ArrayList<Groupe>
 */
public ArrayList<Groupe> ListGroupe (String nomFichier){
		ArrayList <Groupe> liste = new ArrayList <Groupe>(); 
		try {
			FileReader fichier = new FileReader(nomFichier);
			BufferedReader reader = new BufferedReader(fichier);
			while(reader.ready()) {
				String groupe = reader.readLine();
				if(groupe.length()==2) {
					liste.add(new Groupe(groupe));
				}
			}
			reader.close();
			fichier.close();
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
		return liste;
	}	
	/**
	 * Importe une liste de créneaux à partir d’un fichier de configuration.
	 *
	 * Chaque bloc de données du fichier décrit un type de séance (CM ou TD), sa durée et ses horaires de début.
	 * Un objet {@code Creneau} est créé pour chaque horaire indiqué, avec une séance vide du type approprié.
	 *
	 * @param fichier Le chemin du fichier de configuration à lire.
	 * @return Une liste de créneaux contenant des séances de type {@code CM} ou {@code TD}.
	 * @throws Exception Si une erreur survient lors de la lecture ou du traitement du fichier.
	 */
	public ArrayList<Creneau<? extends Seance>> importCreneau(String fichier) throws Exception {
	        ArrayList<Creneau<? extends Seance>> resultat = new ArrayList<>();
	        Path chemin = Paths.get(fichier);
	        List<String> lignes = Files.readAllLines(chemin);

	        String typeC = "";
	        int duree = 0;
	        ArrayList<Heure> listDebut = new ArrayList<>();

	        for (int i = 0; i <= lignes.size(); i++) {
	            String ligne = (i < lignes.size()) ? lignes.get(i).trim() : "";

	            // Si ligne vide ou début d’un nouveau bloc, on crée les créneaux du bloc précédent
	            if (ligne.isEmpty() || (ligne.startsWith("[") && ligne.endsWith("]"))) {
	                // Création des créneaux pour le bloc courant
	                for (Heure h : listDebut) {
	                    if ("CM".equals(typeC)) {
	                        Creneau<CM> c = new Creneau<>(h, duree * 60);
	                        c.addSeance(new CM());
	                        resultat.add(c);
	                    } else if ("TD".equals(typeC)) {
	                        Creneau<TD> c = new Creneau<>(h, duree * 60);
	                        c.addSeance(new TD());
	                        resultat.add(c);
	                    }
	                }
	                // Réinitialisation pour le prochain bloc 
	                listDebut = new ArrayList<>();
	                duree = 0;
	            }

	            // Détection début de bloc
	            if (ligne.startsWith("[") && ligne.endsWith("]")) {
	                typeC = ligne.substring(1, ligne.length() - 1).trim();
	                continue;
	            }

	            // Lecture de la durée 
	            if (ligne.startsWith("durée")) {
	                duree = Integer.parseInt(ligne.substring(ligne.indexOf(":") + 1).trim());
	            }

	            // Lecture des horaires
	            if (ligne.startsWith("horaires")) {
	                String extrait = ligne.substring(ligne.indexOf(":") + 1).trim();
	                String[] temps = extrait.split(",");
	                for (String t : temps) {
	                    String[] hm = t.trim().split(":");
	                    if (hm.length == 2) {
	                        int h = Integer.parseInt(hm[0]);
	                        int m = Integer.parseInt(hm[1]);
	                        listDebut.add(new Heure(h, m));
	                    } else {
	                        throw new Exception("Format horaire invalide : " + t); 
	                    }
	                }
	            }
	        }

	        return resultat;
	    }
		
	
	/**
	 * Lit le fichier Ressource.txt et transforme son contenu en objets Ressource.
	 *
	 * Le but de cette méthode est de construire dynamiquement une liste de ressources à partir d’un fichier texte
	 * structuré. Chaque ressource est définie par un code, un nom, un responsable, une liste d’intervenants et
	 * les semaines concernées.
	 *
	 * ⚠ Cette méthode accepte un fichier temporaire comme paramètre afin de faciliter les tests unitaires (Junit).
	 *
	 * Format attendu dans le fichier :
	 * [Rxxx]
	 * nom: Nom de la ressource
	 * responsable: Nom du responsable
	 * intervenants: intervenant1, intervenant2, ...
	 * semaines: 1, 2, 3, ...
	 *
	 * @param fichierTemporaire Le fichier contenant les ressources à importer.
	 * @return Une liste de ressources instanciées à partir du contenu du fichier.
	 * @throws IOException Si une erreur d’entrée/sortie survient pendant la lecture.
	 */
	
	/**
 * LectureRessource : méthode publique.
 * @param fichierTemporaire paramètre de type File
 * @return résultat de type ArrayList<Ressource>
 */
public ArrayList<Ressource> LectureRessource(File fichierTemporaire) throws IOException {
	    ArrayList<Ressource> ressources = new ArrayList<>();
	    
	    String code = null;
	    String nom = null;
	    Enseignant responsable = null;
	    ArrayList<Enseignant> enseignants = new ArrayList<>();
	    ArrayList<Integer> semaines = new ArrayList<>();

	    try (BufferedReader lecteur = new BufferedReader(new FileReader(fichierTemporaire))) { 
	        String ligne;

	        while ((ligne = lecteur.readLine()) != null) {
	            ligne = ligne.trim();
	            // Nouvelle ressource détectée
	            if (ligne.startsWith("[") && ligne.endsWith("]")) {
	                // Ajouter l'ancienne ressource à la liste
	                if (code != null && nom != null && responsable != null) {
	                    Ressource r = new Ressource(nom, responsable, code, enseignants, semaines);
	                    ressources.add(r);
	                }

	                // Initialisation pour la nouvelle ressource
	                code = ligne.substring(1, ligne.length() - 1);
	                nom = null;
	                responsable = null;
	                enseignants = new ArrayList<>();
	                semaines = new ArrayList<>();
	            } 
	            
	            
	            else if (ligne.startsWith("nom:")) {
	                nom = ligne.substring(ligne.indexOf(":") + 1).trim();
	            } 
	            else if (ligne.startsWith("semaines:")) {
	                String[] semaineStrs = ligne.substring(ligne.indexOf(":") + 1).split(",");
	                for (String s : semaineStrs) { 
	                    semaines.add(Integer.parseInt(s.trim()));
	                }
	            } 
	            
	            else if (ligne.startsWith("responsable:")) {
	                String nomResponsable = ligne.substring(ligne.indexOf(":") + 1).trim();
	                responsable = new Enseignant(nomResponsable);
	            } 
	            else if (ligne.startsWith("intervenants:")) {
	                String[] intervenantsStr = ligne.substring(ligne.indexOf(":") + 1).split(",");
	                
	                for (String n : intervenantsStr) {

	                    enseignants.add(new Enseignant(n.trim())); 
	                }
	            }
	            // Ignorer TD et CM si non utilisés
	        } 

	        // Ajouter la dernière ressource
	        if (code != null && nom != null && responsable != null) {
	            Ressource r = new Ressource(nom, responsable, code, enseignants, semaines);
	            ressources.add(r);
	        }

	    } catch (FileNotFoundException e) {
	        System.err.println("Fichier non trouvé : " + fichierTemporaire.getPath());
	        e.printStackTrace();
	    } catch (IOException e) { 
	        System.err.println("Erreur de lecture du fichier");
	        e.printStackTrace(); 
	    }

	    return ressources;  
	} 
	/**
	 * Cette méthode utilitaire permet de retourner le nombre d'heures par ressource:
	 * Elle retourne une hashmap avec pour clé le code d'une ressource (Rxxx) et comme valeur une arraylist avec en index  le nombre d'heure de CM et en index le nombre d'heures de TD
	 * @param code
	 * @param nbCM
	 * @param nbTD
	 * @return HashMap<String, ArrayList<Integer>>
	 */
	private static  HashMap<String, ArrayList<Integer>> creerUnNbSeanceRessource(String code, int nbCM, int nbTD) {
	    HashMap<String, ArrayList<Integer>> map = new HashMap<>();

	    ArrayList<Integer> list = new ArrayList<>();
	    list.add(nbCM);
	    list.add(nbTD); 

	    map.put(code, list);
	    return map;
	}
	/**
	 * Méthode utilitaire permettant d'extraire un entier depuis une ligne de texte, indépendamment des caractères non numériques.
	 *
	 * Cette méthode analyse chaque caractère et reconstitue l’entier à partir des chiffres présents dans l’ordre.
	 * Elle est utile pour récupérer des valeurs comme "CM: 20h" ou "TD: 15 séances" si strict est false.
	 *
	 * @param str La chaîne de caractères à analyser.
	 * @param strict permet de préciser si on veut retourné une exception si la chaine n'est pas composéer uniquement de chiffre
	 * @return L'entier extrait des chiffres présents dans la chaine de charactère (ex : "CM: 20h" → 20 /!\ ne marche que si strict est false).
	 * @throws Exception En cas de strict si une 
	 */
	/**
 * getStringValue : méthode statique publique.
 * @param str paramètre de type String
 * @param strict paramètre de type boolean
 * @return résultat de type int
 */
public static int getStringValue(String str,boolean strict)throws Exception {
		int r=0;
		for( char c: str.toCharArray()) {
        	if (Character.isDigit(c)){
        		r*=10;
        		r+=Character.getNumericValue(c);
        	}else if(strict) {
        		throw new Exception("La chaine de charactère passer en paramètre n'est pas composér uniquement de chiffre. Enlever l'option strict pour que ça marche");
        	}
        }
		return r;
	}
	/**
	 * Importe les informations de nombre d’heures de CM et TD pour chaque ressource à partir d’un fichier texte.
	 * 
	 * Le fichier doit être structuré de la façon suivante :
	 * [Rxxx]
	 * CM: nombre de cours magistraux
	 * TD: nombre de travaux dirigés
	 * 
	 * Pour chaque ressource (code entre crochets), une entrée est ajoutée dans une HashMap où :
	 * - la clé est le code de la ressource (ex. "R101"),
	 * - la valeur est une ArrayList contenant :
	 *   - en index 0 : le nombre d’heures de CM,
	 *   - en index 1 : le nombre d’heures de TD.
	 * 
	 * @param nomFichier Le chemin vers le fichier contenant les données des ressources.
	 * @return Une HashMap contenant les données importées : clé = code ressource, valeur = liste [nbCM, nbTD].
	 * @throws Exception En cas de problème de lecture du fichier ou de format incorrect.
	 */
	public HashMap<String, ArrayList<Integer>> importNbSeanceRessource(String nomFichier) throws Exception { 
		HashMap<String, ArrayList<Integer>> mapResultat = new HashMap<>();
		try {
            Path chemin = Paths.get(nomFichier);
            List<String> lignes = Files.readAllLines(chemin);
            String code=null;
            Integer nbCM=null;
            Integer nbTD=null;
            
            for(String ligne : lignes) {
            	if (ligne.startsWith("[") && ligne.endsWith("]")) {
            		if (nbCM!=null && nbTD!=null)
            			mapResultat.putAll(creerUnNbSeanceRessource(code,nbCM,nbTD));
            		code = ligne.substring(1, ligne.length() - 1);
            		nbCM=null;
            		nbTD=null;
            	}
            	else if (ligne.startsWith("CM:")) {
            		nbCM=getStringValue(ligne,false);
	            } 
	            else if (ligne.startsWith("TD:")) {
	            	nbTD=getStringValue(ligne,false);
	            } 
            }
            mapResultat.putAll(creerUnNbSeanceRessource(code,nbCM,nbTD));
		}catch(Exception e) {
			e.printStackTrace(); 
		}
		return mapResultat;
	}
	/**
	 * Recherche une ressource dans une liste en fonction de son code.
	 *
	 * @param ressourcesListe La liste de Ressource dans laquelle effectuer la recherche.
	 * @param codeRecherche Le code de la ressource à rechercher.
	 * @return L’objet Ressource correspondant au code, ou null si non trouvé.
	 */
	/**
 * trouverRessourceParCode : méthode statique publique.
 * @param ressourcesListe paramètre de type ArrayList<Ressource>
 * @param codeRecherche paramètre de type String
 * @return résultat de type Ressource
 */
public static Ressource trouverRessourceParCode(ArrayList<Ressource> ressourcesListe, String codeRecherche) {
	    if (ressourcesListe == null || codeRecherche == null) return null;
	    for (int i = 0; i < ressourcesListe.size(); i++) {
	        String code = ressourcesListe.get(i).getCode();
	        if (code.equals(codeRecherche)) {
	            return ressourcesListe.get(i);
	        }
	    }

	    return null;
	}
	/**
	 * Crée un créneau vide (sans cours) entre deux horaires si le drapeau createCreneau est actif.
	 * 
	 * Le créneau est ajouté à la liste des CM si moins de 2 CM sont présents, 
	 * sinon à la liste des TD si moins de 3 TD sont présents.
	 *
	 * @param h1 Heure de début du créneau.
	 * @param h Heure de fin (heure).
	 * @param m Heure de fin (minute).
	 * @param listCM Liste des créneaux de CM à compléter.
	 * @param listTD Liste des créneaux de TD à compléter.
	 * @param createCreneau Indique s’il faut créer le créneau (true) ou non (false).
	 * @return true si un créneau a été créé, false sinon.
	 */
	private boolean CreateCreneauVide(Heure h1, int h, int m, ArrayList<Creneau<CM>> listCM, ArrayList<Creneau<TD>> listTD, boolean createCreneau) {
	    if (createCreneau) {
	        Heure h2 = new Heure(h, m);
	        if (h1 != null && h2 != null) {
	            if (listCM.size() < 2) {
	                listCM.add(new Creneau<>(h1, h1.difference(h2)));
	            } else if (listTD.size() < 3) {
	                listTD.add(new Creneau<>(h1, h1.difference(h2)));
	            }
	            return true;
	        }
	        
	    }
	    return createCreneau;
	}

	/**
	 * Lit les fichiers de planning hebdomadaires dans un dossier donné et construit un tableau
	 * contenant les 52 semaines de l'année sous forme d'objets Semaine.
	 * 
	 * Chaque fichier doit être nommé sous la forme "01.txt", "02.txt", ..., "52.txt" dans le dossier indiqué,
	 * et contenir les données des jours de la semaine avec les créneaux horaires, CM et TD.
	 * 
	 * @param dossierPlanning le chemin du dossier contenant les fichiers de planning hebdomadaires
	 * @return un tableau de 52 objets Semaine représentant les semaines de l'année
	 * @throws Exception si une erreur de lecture ou de format survient lors de la lecture des fichiers
	 */

	

	/**
 * lectureSemaine : méthode publique.
 * @param dossierPlanning paramètre de type String
 * @return résultat de type Semaine[]
 */
public Semaine[] lectureSemaine(String dossierPlanning) throws Exception {
	    Semaine[] tabSemaine = new Semaine[52];

	    for (int j = 0; j < 52; j++) {
	        String nomFichier = dossierPlanning + String.format("%02d.txt", j + 1);
	        Jour[] tabJour = new Jour[5];
	        int indexJour = 0;

	        try {
	            Path chemin = Paths.get(nomFichier);
	            List<String> lignes = Files.readAllLines(chemin);

	            String day = null;
	            boolean modeCreateJ = false;
	            Heure h1 = null;
	            int heure = 0, min = 0;
	            boolean createCreneau = false;
	            ArrayList<Creneau<CM>> listCM = new ArrayList<>();
	            ArrayList<Creneau<TD>> listTD = new ArrayList<>();

	            for (String ligne : lignes) {
	                String ligneToutColle = ligne.replaceAll("\\s+", "");
	                String ligneSansMarge = ligne.stripLeading();

	                if (ligneToutColle.isEmpty()) continue;
	                if (ligneToutColle.startsWith("#") && !ligneToutColle.startsWith("##")) continue;

	                if (ligneToutColle.startsWith("##")) {
	                    if (modeCreateJ && day != null) {
	                        createCreneau = CreateCreneauVide(h1, heure, min, listCM, listTD, createCreneau);
	                        tabJour[indexJour++] = new Jour(listTD, listCM, JoursSemaines.valueOf(day));
	                        listCM = new ArrayList<>();
	                        listTD = new ArrayList<>();
	                    }
	                    day = ligneToutColle.substring(2).toUpperCase();
	                    modeCreateJ = true;
	                    continue;
	                }

	                if (createCreneau && !ligneSansMarge.startsWith("CM") && !ligneSansMarge.startsWith("TD")) {
	                    createCreneau = CreateCreneauVide(h1, heure, min, listCM, listTD, createCreneau);
	                }

	                if (ligneToutColle.startsWith("*")) {
	                    createCreneau = CreateCreneauVide(h1, heure, min, listCM, listTD, createCreneau);
	                    int comp = 1;
	                    heure = 0;
	                    min = 0;
	                    for (char c : ligneToutColle.toCharArray()) {
	                        if (Character.isDigit(c)) {
	                            if (comp % 2 == 1) heure = heure * 10 + Character.getNumericValue(c);
	                            else min = min * 10 + Character.getNumericValue(c);
	                        } else if (c == '-' || c == ':') {
	                            comp++;
	                            if (c == '-') {
	                                h1 = new Heure(heure, min);
	                                heure = 0;
	                                min = 0;
	                            }
	                        }
	                    }
	                    createCreneau = true;
	                }

	                if (ligneSansMarge.startsWith("CM")) {
	                    Heure h2 = new Heure(heure, min);
	                    createCreneau = false;

	                    String[] parts = ligneSansMarge.split(" ", 3);
	                    String code = parts[1];
	                    String nom = parts.length > 2 ? parts[2].replaceAll("[()]", "") : "";

	                    Ressource res = new Ressource();
	                    res.setCode(code);
	                    Enseignant enseignant = new Enseignant(nom);
	                    listCM.add(new Creneau<>(h1, h1.difference(h2), new CM(res, enseignant)));
	                    h1 = null;
	                } else if (ligneSansMarge.startsWith("TD")) {
	                    Heure h2 = new Heure(heure, min);
	                    createCreneau = false;

	                    String[] parts = ligneSansMarge.split(" ", 2);
	                    String[] part2 = parts[0].split("-", 2);
	                    String grp = part2[1];
	                    String reste = parts.length > 1 ? parts[1] : "";

	                    String code = "", nom = "";
	                    boolean modeCode = true;

	                    for (char c : reste.toCharArray()) {
	                        if (c == '-') continue;
	                        if (c == ' ') modeCode = false;
	                        if (modeCode) code += c;
	                        else if (c != '(' && c != ')') nom += c;
	                    }

	                    Ressource res = new Ressource();
	                    res.setCode(code);
	                    Enseignant enseignant = new Enseignant(nom);
	                    Groupe groupe = new Groupe(grp);

	                    if (h1 != null) {
	                        listTD.add(new Creneau<>(h1, h1.difference(h2), new TD(res, enseignant, groupe)));
	                        h1 = null;
	                    } else if (!listTD.isEmpty()) {
	                        listTD.get(listTD.size() - 1).addSeance(new TD(res, enseignant, groupe));
	                    } else {
	                        System.err.println("Erreur : tentative d'ajout de TD à un créneau inexistant");
	                    }
	                }
	            }

	            createCreneau = CreateCreneauVide(h1, heure, min, listCM, listTD, createCreneau);

	            if (modeCreateJ && day != null) {
	                tabJour[indexJour++] = new Jour(listTD, listCM, JoursSemaines.valueOf(day));
	            }

	            while (indexJour < 5) {
	                tabJour[indexJour] = new Jour(new ArrayList<>(), new ArrayList<>(), JoursSemaines.values()[indexJour]);
	                indexJour++;
	            }

	        } catch (Exception e) {
	            System.err.println("Erreur lecture fichier : " + nomFichier);
	            e.printStackTrace();
	        }

	        tabSemaine[j] = new Semaine(tabJour, j + 1);
	    }

	    return tabSemaine;
	}

/**
 * Formate un tableau ASCII donné sous forme de chaîne brute.
 * Ajuste dynamiquement la largeur de chaque colonne, sans utiliser StringBuilder.
 *
 * @param inputTable Le tableau ASCII brut avec séparateurs `|` et lignes horizontales `+`.
 * @return Le tableau bien formaté avec des colonnes ajustées.
 */
public static String formaterTableauAscii(String inputTable) {
    String[] lignes = inputTable.strip().split("\n");
    List<String[]> contenu = new ArrayList<>();

    for (String ligne : lignes) {
        if (ligne.startsWith("|")) {
            String[] cellules = ligne.substring(1, ligne.length() - 1).split("\\|", -1);
            for (int i = 0; i < cellules.length; i++) {
                cellules[i] = cellules[i].trim();
            }
            contenu.add(cellules);
        }
    }
    int colonnes = contenu.stream().mapToInt(c -> c.length).max().orElse(0);

    for (int i = 0; i < contenu.size(); i++) {
        String[] ligne = contenu.get(i);
        if (ligne.length < colonnes) {
            String[] nouvelleLigne = new String[colonnes];
            System.arraycopy(ligne, 0, nouvelleLigne, 0, ligne.length);
            Arrays.fill(nouvelleLigne, ligne.length, colonnes, "");
            contenu.set(i, nouvelleLigne);
        }
    }
    int[] largeurs = new int[colonnes];
    for (String[] ligne : contenu) {
        for (int i = 0; i < colonnes; i++) {
            largeurs[i] = Math.max(largeurs[i], ligne[i].length());
        }
    }
    String ligneSeparatrice = "+";
    for (int largeur : largeurs) {
        ligneSeparatrice += "-".repeat(largeur + 2) + "+";
    }
    String resultat = "";
    for (int i = 0; i < contenu.size(); i++) {
        if (i == 0) {
            resultat += ligneSeparatrice + "\n";
        }
        String ligneFormatee = "|";
        for (int j = 0; j < colonnes; j++) {
            String cellule = contenu.get(i)[j];
            ligneFormatee += " " + String.format("%-" + largeurs[j] + "s", cellule) + " |";
        }
        resultat += ligneFormatee + "\n" + ligneSeparatrice + "\n";
    }

    return resultat;
}

/**
 * Affiche l'emploi du temps d'une semaine pour un groupe, une ressource ou un enseignant.
 * Le résultat est formaté sous forme de tableau ASCII.
 *
 * @param tab tableau de semaines
 * @param nbS numéro de la semaine à afficher
 * @param grp nom du groupe filtré (ou "" pour ignorer)
 * @param res code de la ressource filtrée (ou "" pour ignorer)
 * @param ens nom de l'enseignant filtré (ou "" pour ignorer)
 * @return chaîne ASCII formatée de l'emploi du temps
 */
public String voir (Semaine[] tab,int nbS,String grp,String code,String enseignant ) {
	
	String str="Planning de la semaine "+nbS+" pour le groupe "+grp+"\n"; 
	String ligneSep="+----------------------------------------------------------------------------------+\n";
	String strTempo="";
	for (int i=0;i<tab.length;i++) {  
		if (tab[i].getNumero()  != nbS) continue;
			Semaine s=tab[i];
			str+=ligneSep+"|   Créneau   |";
			for (int j=0;j<5;j++) {//5 car même si il y a 6 lignes on affiche la premiere et la deuxieme  on peut aussi utiliser s.getJours().length+1
				strTempo+="\n"+ligneSep;

				for(int k=0;k<5;k++) {//5 car même si il y a 6 colones on affiche la premiere et la deuxieme en même temps sauf "Créneau", on peut aussi utiliser s.getJours()[j].getCrenauxCM().size()+s.getJours()[j].getCrenauxTD().size()
					
					if(j==0) {
						String strTemp=s.getJours()[k].getJourSemaines().toString(); ;
						str+=strTemp+"|";
					}
					if(j<=1) {
						Creneau<CM> creneau=s.getJours()[k].getCrenauxCM().get(j); 
						String strTemp="";
						if(k==0) {//pour k==0 c'est pour pas le répeter plusieurs fois j<=1 c'est pour ne pas empieter sur les TD, de plus il n'y a que 2CM 
							Heure heureDeb=creneau.getHeureDebut();
							strTempo+="| "+heureDeb+"-"+heureDeb.ajouterMinutes(creneau.getDuree())+" |";
						}
							
							for(int l=0;l<creneau.getSeance().size();l++) {
								CM cm=creneau.getSeance().get(l);
								if ((code.isEmpty() || cm.getRessource().getCode().equals(code)) &&
					                    (enseignant.isEmpty() || cm.getEnseignant().getNom().trim().equalsIgnoreCase(enseignant.trim()))) {
										strTemp+="CM "+cm.getRessource().getCode()+' '+cm.getEnseignant().getNom();  
										}
							}

							strTempo+=strTemp+"|";
								
						
						
					}
					else if(j>=2) {//pour k==0 c'est pour pas le répeter plusieurs fois j>=2 c'est pour ne pas empieter sur les CM
						Creneau<TD> creneau=s.getJours()[k].getCrenauxTD().get(j-2);
						String strTemp="";
						if(k==0) { 
						
						Heure heureDeb=creneau.getHeureDebut();
						strTempo+="| "+heureDeb+"-"+heureDeb.ajouterMinutes(creneau.getDuree())+" |";
						}
						for(int l=0;l<creneau.getSeance().size();l++) {
							TD td=creneau.getSeance().get(l);
								if ((grp.isEmpty() || td.getGroupe().getNom().equals(grp)) &&
							        (code.isEmpty() || td.getRessource().getCode().equals(code)) &&
							        (enseignant.isEmpty() || td.getEnseignant().getNom().trim().equalsIgnoreCase(enseignant.trim()))) {

									strTemp+="TD "+td.getRessource().getCode()+' '+td.getEnseignant().getNom()+ ' '+td.getGroupe().getNom();  
									}
						}
						strTempo+=strTemp+"|";
						}	
						
				}
			}
			
			
		} 
	
		return formaterTableauAscii( str+strTempo+"\n"+ligneSep);
	}
	/**
	 * Retourne une représentation textuelle de la semaine spécifiée, au format de configuration.
	 *
	 * @param tab le tableau contenant toutes les semaines
	 * @param nbS le numéro de la semaine à afficher
	 * @return une chaîne de caractères représentant la semaine au format configuration
	 */
		/**
 * voir : méthode publique.
 * @param tab paramètre de type Semaine[]
 * @param nbS paramètre de type int
 * @return résultat de type String
 */
public String voir (Semaine[] tab,int nbS) {
			String str="";
			for (int i=0;i<tab.length;i++) {
				if (tab[i].getNumero()  != nbS) continue;
					str="\n"+ "# semaine "+tab[i].getNumero()+"\n";
					for (int j=0;j<tab[i].getJours().length;j++) {
						str+=("\n"+tab[i].getJours()[j]);
				}
			
		}
	return str;
	}
		/**
		 * Retourne une représentation textuelle de toutes les semaines fournies, au format configuration.
		 *
		 * @param tab le tableau contenant toutes les semaines
		 * @return une chaîne de caractères représentant toutes les semaines au format configuration
		 */
		/**
 * voir : méthode publique.
 * @param tab paramètre de type Semaine[]
 * @return résultat de type String
 */
public String voir (Semaine[] tab) {
			String str="";
			for (int i=0;i<tab.length;i++) {
					str+="\n"+tab[i];
					
			
		}
	return str;
	} 
		/**
		 * Multiplie l'élément d'indice {@code id} de chaque liste contenue dans la map par un facteur {@code x}.
		 * 
		 * La map associe une chaîne de caractères (code de ressource par exemple) à une liste d'entiers.
		 * Toutes les listes doivent avoir la même taille.
		 * 
		 * @param map la map contenant les listes d'entiers à modifier
		 * @param id l'indice de l'élément à multiplier dans chaque liste
		 * @param x le facteur multiplicatif appliqué à l'élément ciblé
		 * @return la map mise à jour avec les nouvelles valeurs
		 * @throws Exception si la map est vide, si les listes n'ont pas la même taille, ou si l'indice est invalide
		 */
			public HashMap<String, ArrayList<Integer>> multToutId(HashMap<String, ArrayList<Integer>> map, int id, float x) throws Exception {
		        if (map.isEmpty()) {
		            throw new Exception("La HashMap est vide."); 
		        }
		        int taille = map.entrySet().iterator().next().getValue().size();// Récupère la taille d'une ArrayList
		        if (id < 0 || id >= taille) {
		            throw new Exception("L'Id choisi est trop grand pour la taille des listes.");
		        }
		        for (Map.Entry<String, ArrayList<Integer>> entry : map.entrySet()) {
		            ArrayList<Integer> liste = entry.getValue();
		            if (liste.size() != taille) { 
		                throw new Exception("Les ArrayList ne sont pas toutes de la même taille.");
		            }
		            int temp = (int) (liste.get(id) * x);
		            liste.set(id, temp);
		        }

		        return map; 
		    }
			/**
			 * Retourne le jour de la semaine correspondant à un entier donné, selon l'enum {@code JoursSemaines}.
			 * 
			 * 0 = LUNDI, 1 = MARDI, ..., 6 = DIMANCHE.
			 * 
			 * @param n un entier représentant le jour de la semaine (0 à 6)
			 * @return le jour de la semaine correspondant dans l'enum {@code JoursSemaines}
			 * @throws Exception si la valeur fournie ne correspond à aucun jour de la semaine
			 */
			
			/**
 * getNbJour : méthode publique.
 * @param n paramètre de type int
 * @return résultat de type JoursSemaines
 */
public JoursSemaines getNbJour(int n) throws Exception {
			    JoursSemaines jourS = null;
			    switch (n) {
			        case 0:
			            jourS = JoursSemaines.LUNDI;
			            break;
			        case 1:
			            jourS = JoursSemaines.MARDI;
			            break;
			        case 2:
			            jourS = JoursSemaines.MERCREDI;
			            break;
			        case 3: 
			            jourS = JoursSemaines.JEUDI;
			            break;
			        case 4:
			            jourS = JoursSemaines.VENDREDI;
			            break;
			        case 5:
			            jourS = JoursSemaines.SAMEDI;
			            System.out.println("Attention le jour choisi n'est pas un jour de travail");
			            break;
			        case 6:
			            jourS = JoursSemaines.DIMANCHE;
			            System.out.println("Attention le jour choisi n'est pas un jour de travail");
			            break;
			        default:
			            throw new Exception("Le numéro de semaine mit n'est pas pris en compte dans l'enum JoursSemaines");
			    }
			    return jourS;
			}
			/**
			 * Clone une map contenant, pour chaque ressource (identifiée par son code), une liste de deux entiers représentant 
			 * le nombre de séances CM et TD restantes à placer.
			 *
			 * @param original la map originale à cloner, associant un code ressource à une liste [nbCM, nbTD]
			 * @return une copie profonde de la map d'origine
			 */
			static HashMap<String, ArrayList<Integer>> cloneNbSeanceParRessource(HashMap<String, ArrayList<Integer>> original) {
			    HashMap<String, ArrayList<Integer>> clone = new HashMap<>();
			    for (String key : original.keySet()) {
			        ArrayList<Integer> listClone = new ArrayList<>(original.get(key));
			        clone.put(key, listClone);
			    }
			    return clone;
			}
			
			/**
			 * Vérifie si tous les créneaux restants (CM ou TD) ont pu être placés pour chaque ressource,
			 * pour une semaine donnée. Utilisée à la fois pour les CM et les TD.
			 *
			 * @param nbSeanceParRessource Table des ressources avec le nombre de séances restantes à placer.
			 * @param ressources Liste des ressources disponibles.
			 * @param i La semaine à vérifier.
			 * @param doitCreer Indique si les créneaux doivent être créés (true) ou non (false).
			 * @throws Exception Si une ressource n'existe pas ou s'il reste des créneaux non placés.
			 */
			
public void verifierSeancePlacee(HashMap<String, ArrayList<Integer>> nbSeanceParRessourceMap,
                    ArrayList<Ressource> toutesLesRessources,
                    int numeroSemaineActuelle,
                    boolean doitAvoirEteCreer,
                    int indexTypeDeSeance) throws Exception {
						for (String codeRessource : nbSeanceParRessourceMap.keySet()) {
						Ressource ressourceTrouvee = trouverRessourceParCode(toutesLesRessources, codeRessource);
						
						if (ressourceTrouvee == null) {
						throw new Exception("Erreur de données : le code ressource '" + codeRessource + "' n'existe pas dans la liste des ressources.");
						}
						
						int heuresRestantes = nbSeanceParRessourceMap.get(codeRessource).get(indexTypeDeSeance);
						String nomTypeDeSeance = (indexTypeDeSeance == 0) ? "CM" : "TD";
						
						if (heuresRestantes != 0 && ressourceTrouvee.getSemainesPrevues().contains(numeroSemaineActuelle) && doitAvoirEteCreer) {
						
						throw new Exception("Semaine " + numeroSemaineActuelle + " : Il manque " + heuresRestantes +
						                  " heures de " + nomTypeDeSeance + " pour la ressource '" + codeRessource + "'.");
						}
}
}

			/**
			 * Vérifie si tous les créneaux de TD ont pu être placés pour chaque ressource et chaque groupe,
			 * pour une semaine donnée. Si ce n'est pas le cas, une exception est levée.
			 *
			 * @param nbSeanceParRessourceParGroupe Liste contenant, pour chaque groupe, une table des ressources associées à leurs séances restantes.
			 * @param i La semaine à vérifier.
			 * @param TDCreer Indique si les créneaux de TD doivent être créés (true) ou non (false).
			 * @throws Exception Si une ressource mentionnée n'existe pas ou si des créneaux de TD restent non placés.
			 */
public void TDPlace(ArrayList<HashMap<String, ArrayList<Integer>>> nbSeanceParRessourceParGroupe,
                    ArrayList<Ressource> ressources, int i, boolean TDCreer) throws Exception {
		    for (HashMap<String, ArrayList<Integer>> mapGroupe : nbSeanceParRessourceParGroupe) {
		        // On passe '1' pour vérifier les TD
		        verifierSeancePlacee(mapGroupe, ressources, i, TDCreer, 1);
    }
}

	    
		                    
}