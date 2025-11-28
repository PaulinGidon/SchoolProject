package edt;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
	public static int port=2025;
 public static void main(String[] args) throws IOException {
  HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
  server.createContext("/", new CustomHandler());
  server.createContext("/test", new TestHandler());
  server.start();
 }
}

class TestHandler implements HttpHandler {
	@Override
	public void handle(HttpExchange exchange) throws IOException {

		
		String response = "<p>Ok</p><style>p{width:40vh;\nheight:40vh;}</style>";
		exchange.sendResponseHeaders(200, response.getBytes("UTF-8").length);
		OutputStream os = exchange.getResponseBody();
		os.write(response.getBytes("UTF-8"));
		os.close();
	}
}

class CustomHandler implements HttpHandler {
	public static  Edt edt;
	
	/**
	 * Prend en argument un string contenant le code de la ressource et renvoie la couleur associé à la ressource.
	 * Elle est utilisé afin d'automatiser le switch
	 * @param RESSOURCE Code de la ressource
	 * @return la couleur utilisé en css
	 */
	public String getCouleurRessource(String RESSOURCE){
		switch(RESSOURCE){
				case "R201":
                    return "PaleGreen";
                case "R202":
                    return "Yellow";
                case "R203":
                    return "LightCoral";
                case "R204":
                    return "RoyalBlue";
                case "R205":
                    return "Red";
                case "R206":
                    return "Cyan";
                case "R207":
                    return "Magenta";
                case "R208":
                    return "Orange";
                case "R209":
                    return "Teal";
                case "R210":
                    return "Gold";
                case "R211":
                    return "YellowGreen";
                case "R212":
                    return "Peru";
                case "R213":
                    return "PowderBlue";
                case "R214":
                    return "Violet";
                default:
                    return "Black";}
	}
	 public static <T> void trierAvecAssociation(List<Integer> valeurs, List<T> associes) {
	        if (valeurs.size() != associes.size()) {
	            throw new IllegalArgumentException("Les deux listes doivent avoir la même taille.");
	        }

	        // Crée une liste d’indices
	        List<Integer> indices = new ArrayList<>();
	        for (int i = 0; i < valeurs.size(); i++) {
	            indices.add(i);
	        }

	        // Trie les indices selon les valeurs
	        indices.sort(Comparator.comparingInt(valeurs::get));

	        // Applique le nouvel ordre aux deux listes
	        List<Integer> valeursTriees = new ArrayList<>(valeurs.size());
	        List<T> associesTries = new ArrayList<>(associes.size());

	        for (int i : indices) {
	            valeursTriees.add(valeurs.get(i));
	            associesTries.add(associes.get(i));
	        }

	        // Met à jour les listes d’origine
	        for (int i = 0; i < valeurs.size(); i++) {
	            valeurs.set(i, valeursTriees.get(i));
	            associes.set(i, associesTries.get(i));
	        }
	    }
	/**
	 * Calcul automatiquement la taille des cellules en fonction de la durée du cours, sur la base de 1h=10vh
	 * @param duree est la durée du cours
	 * @return une taille calculé, transformé en style css placeable dans la balise html 
	 */
	public String calculeTailleCell(int duree) {
		int taille = (int) ((75.0 / 60) * duree); //CHanger le dividende de 60 pour changer la taille par heure 
		String tailleS =  taille + "px;";
		return tailleS;

	}
	/**
	 * Prend une cellule pour l'ajouter à un hashMap passé en argument, si le créneau horaire existe dejà il est
	 * placé à l'intérieur sinon il crée une nouvelle clé pour le placé à l'intérieur
	 * @param hashMap hashMap duquel on veut ajouter la cellule
	 * @param cell cellule contenant le html
	 * @param heureDebut heure passé en commande
	 * @param heureFin heure de fin passé en commande
	 * @return Le HasMap contenant la nouvelle cellule ajouté est renvoyé
	 */

	private HashMap<List<Integer>, String[]> ajouterCell(HashMap<List<Integer>, String[]> map, int jourIndex, int debut, int fin, String contenu) {
	    List<Integer> key = Arrays.asList(debut, fin);
	    String[] ligne = map.getOrDefault(key, new String[5]); // 5 jours : lundi à vendredi
	    ligne[jourIndex] = contenu;
	    map.put(key, ligne);
	    return map;
	}

	/**
	 * Créé un main contenant un tableau
	 * le tableau contiendra tout l'emploi du temps avec une colone par jour et une ligne par créneau
	 * elle parcours la semaine afin de récupérer tout les arguments dont elle a besoin
	 * @param semaine Une semaine utilisé depuis laquel on recupère les différents arguments tel que le jour, le cours...
	 * @param groupeChoisi Argument dictant si un groupe précis est voulu, et si oui lequel
	 * @param enseignantChoisi Argument dictant si un enseignant précis est voulu, et si oui lequel
	 * @param ressourceChoisie Argument dictant si une ressource précise est voulue, et si oui lequel
	 * @return Le main contenant le tableau avec l'emplois du temps sous forme html
	 * @throws IOException Renvoie une erreur si la liste CM contient plus d'un cours 
	 */
	public String createTableauHtml(Semaine semaine, String groupeChoisi, String enseignantChoisi, String ressourceChoisie) {
	    HashMap<List<Integer>, String[]> contenuTableau = new HashMap<>();
	    Creneau<? extends Seance> creneauTemp = null;

	    for (Jour jour : semaine.getJours()) {
	        if (jour == null) continue;
	        int jourIndex = jour.getJourSemaines().ordinal();

	        for (Creneau<CM> creneauCM : jour.getCrenauxCM()) {
	            int debut = creneauCM.getHeureDebut().getToutMinutes();
	            int fin = debut + creneauCM.getDuree();

	            if (creneauTemp != null) {
	                int finTemp = creneauTemp.getHeureDebut().getToutMinutes() + creneauTemp.getDuree();
	                if (finTemp < debut) {
	                    String contenuVide = "<div style='background-color:#6f6f6f; height: 100%; width: 100%; box-sizing: border-box;'></div>";
	                    contenuTableau = ajouterCell(contenuTableau, jourIndex, finTemp, debut, contenuVide);
	                }
	            }

	            boolean seanceAjoutee = false;
	            for (CM cm : creneauCM.getSeance()) {
	                if ((ressourceChoisie.isEmpty() || cm.getRessource().getCode().equals(ressourceChoisie)) &&
	                    (enseignantChoisi.isEmpty() || cm.getEnseignant().getNom().trim().equalsIgnoreCase(enseignantChoisi.trim()))) {

	                    String couleur = getCouleurRessource(cm.getRessource().getCode());
	                    String contenu = "<div style='background-color:" + couleur
	                        + "; margin-bottom: 2px; padding: 2%; border-radius: 5px;'>" +
	                        cm.getRessource().getNom() + "<br>" +
	                        cm.getRessource().getCode() + "<br>" +
	                        cm.getRessource().getResponsable() + "</div>";

	                    contenuTableau = ajouterCell(contenuTableau, jourIndex, debut, fin, contenu);
	                    seanceAjoutee = true;
	                }
	            }

	            if (!seanceAjoutee) {
	                String contenuVide = "<div style='background-color:#cccccc; height: 100%; width: 100%; box-sizing: border-box;'></div>";
	                contenuTableau = ajouterCell(contenuTableau, jourIndex, debut, fin, contenuVide);
	            }

	            creneauTemp = creneauCM;
	        }

	        for (Creneau<TD> creneauTD : jour.getCrenauxTD()) {
	            int debut = creneauTD.getHeureDebut().getToutMinutes();
	            int fin = debut + creneauTD.getDuree();

	            if (creneauTemp != null) {
	                int finTemp = creneauTemp.getHeureDebut().getToutMinutes() + creneauTemp.getDuree();
	                if (finTemp < debut) {
	                    String contenuVide = "<div style='background-color:#6f6f6f; height: 100%; width: 100%; box-sizing: border-box;'></div>";
	                    contenuTableau = ajouterCell(contenuTableau, jourIndex, finTemp, debut, contenuVide);
	                }
	            }

	            String contenu = "";
	            boolean seanceAjoutee = false;

	            for (TD td : creneauTD.getSeance()) {
	                if ((groupeChoisi.isEmpty() || td.getGroupe().getNom().equals(groupeChoisi)) &&
	                    (ressourceChoisie.isEmpty() || td.getRessource().getCode().equals(ressourceChoisie)) &&
	                    (enseignantChoisi.isEmpty() || td.getEnseignant().getNom().trim().equalsIgnoreCase(enseignantChoisi.trim()))) {

	                    String couleur = getCouleurRessource(td.getRessource().getCode());
	                    contenu += "<div style='background-color:" + couleur
	                        + "; margin-bottom: 2px; padding: 5%; border-radius: 5px;'>" +
	                        td.getRessource().getNom() + "<br>" +
	                        td.getRessource().getCode() + "<br>" +
	                        td.getEnseignant().getNom() + "<br>" +
	                        td.getGroupe().getNom() + "</div>";
	                    seanceAjoutee = true;
	                }
	            }

	            if (seanceAjoutee) {
	                contenuTableau = ajouterCell(contenuTableau, jourIndex, debut, fin, contenu);
	            } else {
	                String contenuVide = "<div style='background-color:#cccccc; height: 100%; width: 100%; box-sizing: border-box;'></div>";
	                contenuTableau = ajouterCell(contenuTableau, jourIndex, debut, fin, contenuVide);
	            }

	            creneauTemp = creneauTD;
	        }
	    }

	    // Début de la page HTML complète avec UTF-8
	    StringBuilder html = new StringBuilder();
	    html.append("<!DOCTYPE html>")
	        .append("<html lang='fr'>")
	        .append("<head>")
	        .append("<meta charset='UTF-8'>")
	        .append("<title>Emploi du Temps</title>")
	        .append("<style>")
	        .append(".table { width: 100%; border-collapse: collapse; }")
	        .append("th, td { border: 1px solid #ccc; padding: 8px; text-align: center; }")
	        .append("th.horaire { width: 15%; }")
	        .append("td.jour { width: 17%; }")
	        .append("</style>")
	        .append("</head><body><main><table class=\"table\">\n<thead><tr>\n")
	        .append("<th class=\"horaire\">Horaire</th>\n")
	        .append("<th class=\"lundi\">Lundi</th>\n")
	        .append("<th class=\"mardi\">Mardi</th>\n")
	        .append("<th class=\"mercredi\">Mercredi</th>\n")
	        .append("<th class=\"jeudi\">Jeudi</th>\n")
	        .append("<th class=\"vendredi\">Vendredi</th>\n")
	        .append("</tr></thead><tbody>\n");

	    ArrayList<Integer> arrayDeb = new ArrayList<>();
	    ArrayList<String> arrayLigne = new ArrayList<>();

	    for (List<Integer> plage : contenuTableau.keySet()) {
	        int debut = plage.get(0);
	        int fin = plage.get(1);
	        int duree = fin - debut;
	        arrayDeb.add(debut);

	        String horaire = String.format("%02dh%02d - %02dh%02d", debut / 60, debut % 60, fin / 60, fin % 60);
	        String hauteur = calculeTailleCell(duree);

	        StringBuilder ligne = new StringBuilder("<tr style=\"height:" + hauteur + "; min-height:" + hauteur + ";\">\n");
	        ligne.append("<td data-label=\"heure\">").append(horaire).append("</td>\n");

	        String[] colonnes = contenuTableau.get(plage);
	        for (int i = 0; i < semaine.getJours().length; i++) {
	            String contenu = colonnes[i] != null ? colonnes[i] : "";
	            ligne.append("<td class=\"jour\">").append(contenu).append("</td>\n");
	        }

	        ligne.append("</tr>\n");
	        arrayLigne.add(ligne.toString());
	    }

	    trierAvecAssociation(arrayDeb, arrayLigne);
	    for (String ligne : arrayLigne) {
	        html.append(ligne);
	    }

	    if (contenuTableau.isEmpty()) {
	        html.append("<tr><td colspan=\"6\" style=\"text-align:center; padding: 20px;\">Aucun créneau à afficher</td></tr>\n");
	    }

	    html.append("</tbody></table></main></body></html>");
	    return html.toString();
	}




	@Override
	public void handle(HttpExchange exchange) throws IOException {
		Semaine SEMAINE=new Semaine(1);
		

		String query = exchange.getRequestURI().getQuery();

		//le code ci dessous va adapter la requete serveur selon les arguments passé en paramètre dans le main
		
		

		int semaine = -1;
		String groupe = "";
		String enseignant = "";
		String ressource = "";
		String cfg = "";

		if (query != null) {
		    for (String param : query.split("&")) {
		        String[] keyValue = param.split("=", 2); // limite à 2 au cas où la valeur contient '='
		        if (keyValue.length == 2) {
		            String key = URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8);
		            String value = URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8);

		            switch (key) {
		                case "cfg":
		                    cfg = value;
		                    break;
		                case "semaine":
		                    try {
		                        semaine = Integer.parseInt(value);
		                    } catch (NumberFormatException ignored) {
		                        System.out.println("Semaine non valide : " + value);
		                    }
		                    break;
		                case "groupe":
		                    groupe = value;
		                    break;
		                case "enseignant":
		                    enseignant = value;
		                    break;
		                case "ressource":
		                    ressource = value;
		                    break;
		            }
		        }
		    }
		

			 // Chargement des fichiers de configuration
            String ressourceBase = cfg + "/ressources.txt";
            String horaireBase = cfg + "/horaires.txt";
            String groupeBase = cfg + "/groupes.txt";
            String enseignantBase = cfg + "/enseignants.txt";
            try {

				Edt.verifierFichier(ressourceBase);

			Edt.verifierFichier(enseignantBase);
            Edt.verifierFichier(groupeBase);
            Edt.verifierFichier(horaireBase);
            

            // Initialisation de l'emploi du temps
            edt = new Edt();
            edt.setEnseignant(edt.listeEnseignant(enseignantBase));
            edt.setGroupe(edt.ListGroupe(groupeBase));

            edt.setNbSeanceParRessource(edt.importNbSeanceRessource(ressourceBase));
            
            edt.setRessource(edt.LectureRessource(new File(ressourceBase)));
            edt.setCreneau(edt.importCreneau(horaireBase));
            
            edt.generer();
            } catch (Exception e) {
            	System.out.println(e);
				throw new IOException(e.getMessage());
			}
			
            // Recherche de la semaine
             SEMAINE = null;
            for (Semaine s : edt.getSemaine()) {
                if (s.getNumero() == semaine) {
                    SEMAINE = s;
                    break;
                }
            }
            if (SEMAINE == null) {
                throw new IOException("La semaine cherchée " + semaine + " n'existe pas.");
            }
			
		}
		try {
			
			Edt.verifier(semaine, edt.getNbSeanceParRessource() ,edt.getGroupes() ,edt.getEnseignants(), edt.getRessources(),edt.getSemaine());
			
		} catch (Exception e) {
			throw new IOException(e);
		}
		
		String tableau = createTableauHtml(SEMAINE, groupe, enseignant, ressource);

		String affichageInfo = "<p class=\"grp_semaine\">";
			if (semaine != -1) {
				affichageInfo += "Semaine " + semaine+"\t";
			}
			if (groupe.equals("")) {
				affichageInfo += "Groupe " + groupe + "\t";
			}
			if (enseignant.equals("")) {
				affichageInfo += "Enseignant " + enseignant+"\t";
			}
			if (ressource.equals("")) {
				affichageInfo += "Ressource " + ressource;
			}
			affichageInfo += "</p>";	
			String formulaire="<form action=\"/\" method=\"get\" id=\"rechercher\">\n"
		            +"<p id=\"warning\">Attention : il faut rentrer toutes les valeurs !</p>\n"
		            + " <input type=\"hidden\" name=\"cfg\" value=\"doc/exemples-fichiers/config/\">\n"
		            + " <label for=\"semaine\"> Semaine : </label>\n"
		            + " <input type=\"number\" id=\"s\" name=\"semaine\" min=\"1\" max=\"52\">\n"
		            + " <label for=\"groupe\"> Groupe : </label>\n"
		            + " <input type=\"text\" id=\"g\" name=\"groupe\" placeholder=\"Ex:G2\">\n"
		            + " <label for=\"ressource\"> Ressource : </label>\n"
		            + " <input type=\"text\" id=\"r\" name=\"ressource\" placeholder=\"Ex:R204\">\n"
		            + " <button type\"submit\" value=\"afficher l'emploi du temps\">Rechercher dans l'emploi du temps</button>\n";
		            formulaire+="""
		            <style>
		                    #warning{
		                        color:white;
		                        font-size: 2vh;
		                    }

		                    #rechercher{
		                        margin-top: 100px;
		                        padding: 20px;
		                    }
		                    input{
		                        background-color: rgb(186, 186, 186);
		                        border-radius: 5px;
		                        min-height: 50px;
		                    }

		                    label{
		                        font-size: 2.5vh;
		                        color: white;
		                        margin-top : 50px;
		                        min-height: 50px;
		                        margin-left:5px
		                    }

		                    button{
		                        border-radius: 10px;
		                        background-color: rgb(196, 198, 204);
		                        font-color: black;
		                        font-size: 120%;
		                        min-height: 50px;
		                        margin-left:15px

		                    }
		                        </style>
		                    """;
		//style general
					affichageInfo+="<style>    \n"

		+ "body{\n"
			+ " background-color:rgb(13, 91, 120);\n"
		+ "}\n"

		+ ".grp_semaine{"
			+ " text-align : center;\n"
			+ " border: 2px solid black;\n"
			+ " background-color: white;\n"
			+ " font-size : 1.5rem;\n"
		+ "}\n"

		+ "main{\n"
			+ " background-color: #f0f0f0;\n"
		+ "}\n"

		+ ".table{\n"
			+ "border-collapse: collapse;\n"
		+ "}\n"

		+ ".jour{\n"
			+ "padding:0px;\n"
		+ "}\n"

		+ ".uneH{\n"
			+ "height:10vh;\n"
		+ "}\n"

		+ ".deuxH{\n"
			+ "height:20vh;\n"
        + "}\n"

		+"</style>    "
		//style format telephone
		+"<style>    \n"
		+ "@media (max-width: 1000px){\n"
			+ " .format::after{\n"
				+ "    content:'Telephone';\n"
			+ "}\n"

			+ "th,\n"
			+ "td {\n"
				+ "  border: 10px solid rgb(160 160 160);\n"
				+ "  padding: 8px 10px;\n"
			+ "}\n"

			+ "th{\n"
			+ "font-size:50%;\n"
			+ "}"

			+ ".table{\n"
				+ "width:100%;"
			+ "}\n"

			+ " tr {\n"
				+ "  display: table;\n"
				+ "  width: 100%;\n"
				+ "  table-layout: fixed;\n"
			+ "}\n"        

			+ "th,\n"
			+ "td {\n"
				+ "  border: 1px solid rgb(160 160 160);\n"
				+ "  padding: 8px 10px;\n"
				+ " height : 100%;\n"
			+ "}\n"
			
			+ "th.lundi, th.mardi, th.mercredi, th.jeudi, th.vendredi {\n"
			+ "  background-color: gray;\n"
			+ "  color: black;\n"
			+ "}\n"
			
			+ "td {\n"+
				"  border: 1px solid rgb(160 160 160);\n"
				+ "  padding: 8px 10px;\n"+
				"    position: relative;\n" +
				"    border: none;\n" +
				"    border-bottom: 1px solid #eee;\n" +
				"    height: auto;\n" 
			+ "}\n"

			+ " td,{\n"
				+ " text-align: center;\n" 
				+ " position: relative;\n" 
				+ " height: 20vh;\n" 
			+ "}\n"

			+ "td:first-child,\n"+"th:first-child {\n"
				+ "  background-color: gray; \n"
			+ "}\n"

			+".table tbody tr{\n"
			+ "  height: 20vh;\n"
			+ "}\n"


			+".creneau{\n"
				+ "display: flex;\n"
				+ "flex-direction: row;\n"
				+"flex-wrap: wrap;\n"
				+"box-sizing: border-box;\n"
				+ "  height: 20vh; /\n" 
			+"}\n"

			+".creneau >div{\n"
				+ "display: flex;\n"
				+ "align-items: center;\n"
				+ "justify-content: center;\n"
				+ "flex: 1 1 auto;\n"
				+ "box-sizing: border-box;\n"
				+ " font-size:60%;\n"
			+"}\n"

		+ "}"
		+"</style> \n"
		//style format pc
		+"<style>    "
		+ "@media (min-width: 1001px){\n"
			+ " .format::after{\n"
				+ " content:'Ordinateur';\n"
			+ "    }\n"
			+ ".table{\n"
				+ "width:100%;\n"
			+ "}\n"

			+ "main{\n"
			+ "  padding: 0.5vh;\n" 
			+ "}\n"

			+ "td:not(:first-child),\n"+"th:not(:first-child) {\n"
				+ "  background-color: lightgray; \n"	
			+ "}\n"

			+ "td:first-child,\n"
			+ "th:first-child {\n"
				+ " background-color: gray; \n"
				+ " height: 4vh; \n" 
			+ "}\n"

			+ "th,\n"+"td {\n"
				+ "  border: 1px solid rgb(160 160 160);\n"
				+ "  padding: 8px 10px;\n"
			+ "}\n"

			+".horaire{\n"
				+"width:10vh;\n"
			+"}\n"

			+".jour{\n"
				+"padding:0;\n"
			+"}\n"

			+".creneau{\n"
				+ "display: flex;\n"
				+ "flex-direction: row;\n"
				+"flex-wrap: wrap;\n"
				+"box-sizing: border-box;\n"
				+ "  height: 10vh; /\n" 
			+"}\n"

			+".creneau >div{\n"
				+ "display: flex;\n"
				+"align-items: center;\n"
				+"justify-content: center;\n"
				+"flex: 1 1 auto;\n"
				+"box-sizing: border-box;\n"
			+"}\n"

			+ "th.lundi, th.mardi, th.mercredi, th.jeudi, th.vendredi {\n"
				+ " background-color: gray;\n"
				+ " color: black;\n"
			+ "}\n"

		+ "</style>"

		;
			String response = affichageInfo + tableau+formulaire;
			exchange.getResponseHeaders().set("Content-Type", "text/html; charset=utf-8");

			exchange.sendResponseHeaders(200, response.getBytes("UTF-8").length);
			OutputStream os = exchange.getResponseBody();
			os.write(response.getBytes("UTF-8"));
			os.close();
	}

	}

