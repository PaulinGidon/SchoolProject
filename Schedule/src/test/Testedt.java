package test;


import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import edt.*;

class Testedt {

	Edt appel= new Edt();
	
	Groupe grp4=new Groupe("Groupe4");
	Groupe grp1=new Groupe("Groupe1");
	
	Enseignant pourcelot=new Enseignant("Pourcelot");
	Enseignant caillouet=new Enseignant("Caillouet");
	Enseignant abel=new Enseignant("Abel");
	
	//Ressource R201=new Ressource("R201",caillouet);
	//Ressource R207=new Ressource("R207",pourcelot);
	
	//CM R201CM= new CM(R201,R201.getResponsable());
	//CM R207CM=new CM(R207,R207.getResponsable());
	
	//TD R201TD=new TD(R201,abel,grp4);
	//TD R207TD=new TD(R207,pourcelot,grp1);
	
	//Seance s1=new Seance(caillouet,R201);
	

	Heure h8 =new Heure(8,00);

	
	
	Semaine se1= new Semaine(1);
	
	@Test
	public void testListeEnseignant() {
		ArrayList<Enseignant> liste = appel.listeEnseignant("doc/exemples-fichiers/config/enseignants.txt");
		assertNotNull(liste);
		assertEquals(liste.size(),20);
		assertEquals(liste.get(0).getNom(),"CHEVALIER Laura");
		assertEquals(liste.get(10).getNom(),"GIRARD Léna");
		assertEquals(liste.get(19).getNom(),"ROUSSEL Marie");
		ArrayList<Enseignant> liste2 = appel.listeEnseignant("doc/exemples-fichiers/config/enseignants.txt");
		assertEquals(liste,liste2);
	}
	
	@Test
	public void testListeEnseignantInvalide() {
		ArrayList<Enseignant> liste = appel.listeEnseignant("mauvais/chemin/enseignants.txt");
	    assertNotNull(liste);
	    assertEquals(0, liste.size());
	}
	
	@Test
	public void testListGroupe() {
		ArrayList<Groupe> liste = appel.ListGroupe("doc/exemples-fichiers/config/groupes.txt");
		assertNotNull(liste);
		assertEquals(liste.size(),4);
		assertEquals(liste.get(0).getNom(),"G1");
		assertEquals(liste.get(3).getNom(),"G4");
		ArrayList<Groupe> liste2 = appel.ListGroupe("doc/exemples-fichiers/config/groupes.txt");
		assertEquals(liste,liste2);
	}
	
	@Test
	public void testListeGroupeInvalide() {
		ArrayList<Groupe> liste = appel.ListGroupe("mauvais/chemin/enseignants.txt");
	    assertNotNull(liste);
	    assertEquals(0, liste.size());
	}
	
	@Test
	public void testGetNameAndCodeRessource(String fichier_test) throws IOException {
		//comme pour la lectureRessource nous devons creer un fichier temporaire
		//Cette méthode renvoie une Hashmap , donc nous allons aussi creer un Hashmap de test
		
		
		File fichier_temp = File.createTempFile("test_ressource_CodeName", ".txt");
		
		//création d'une ressource fictive et test dessus
		
		String r = "[R206]\n"
				+ "nom: matière_fictive_test\n"
				+ "semaines: 14, 17, 18, 14, 55\n"
				+ "TD: 8\n"
				+ "CM: 2\n"
				+ "responsable: DUPONT JEAN \n"
				+ "intervenants: DUPONT JEAN, DUPONT JEAN, MARTIN Adrien, MARTIN Adrien";
		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fichier_temp))) {
			writer.write(r);
		}
		
		//Nous devons alors creer un edt pour tester 
		Edt edt = new Edt();
		HashMap<String, Ressource> resultat_du_test = edt.GetNameAndCodeRessource(fichier_temp.toString());
		
		//on créer une ressource pour lire le fichier temporaire et travailler dessus
		
		Ressource R1 = new Ressource();
		
		assertEquals(R1, resultat_du_test.get("[206]"));
		assertEquals(1,resultat_du_test.size());


	}

	//test de la fonction VoirEmploieDuTemps (demandé au rendo 0.3)

	@Test
	public void TestEmploieDuTemps_SemaineValide(){

		Edt edt = new Edt();

		//Ici on créer une fausse entrée utilisateur pour la semaine 2 imaginons
		String simulatedInput = "2\n";
		//cette ligne va "simuler" une fausse entrée utilisateur = nécessaire pour simuler la lecture de semaine
		System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
		
	    final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(outputStreamCaptor));
	    

		//et on vérifie si la sortie d'affichage est bien lu
		//on sauvegarde la sortie
		String affichageSemaineTest = outputStreamCaptor.toString().trim();
		

		assertTrue(affichageSemaineTest.contains("Affichage de l'emploi du temps..."));
	
		assertTrue(affichageSemaineTest.contains("## LUNDI"));
		assertTrue(affichageSemaineTest.contains("## MARDI"));
		assertTrue(affichageSemaineTest.contains("## MERCREDI"));
		assertTrue(affichageSemaineTest.contains("## JEUDI"));
		assertTrue(affichageSemaineTest.contains("## VENDREDI"));
	}

	@Test
	public void TestEmploieDuTemps_SemaineNonValide(){

		Edt edt = new Edt();

		String simulatedInput = "600\n";

		System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
		
	    final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
	    final ByteArrayOutputStream errorStreamCaptor = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(outputStreamCaptor));
	    System.setErr(new PrintStream(errorStreamCaptor));
	    

		//et on vérifie si la sortie d'affichage est bien lu
		//on sauvegarde la sortieZ
		String affichageSemaineTest = outputStreamCaptor.toString().trim();
		String affichageErrorSemaineTest = errorStreamCaptor.toString().trim();
		
		String errorMessage = "La semaine n'existe pas dans le dossier semaine";

		assertTrue(affichageErrorSemaineTest.contains(errorMessage));
		assertTrue(affichageErrorSemaineTest.contains("java.io.FileNotFoundException"));

		//on vérifie si le contenu n'existe pas 
		
		assertFalse(affichageSemaineTest.contains("## LUNDI"));
		assertFalse(affichageSemaineTest.contains("## MARDI"));
		assertFalse(affichageSemaineTest.contains("## MERCREDI"));
		assertFalse(affichageSemaineTest.contains("## JEUDI"));
		assertFalse(affichageSemaineTest.contains("## VENDREDI"));


	}
	
	@Test
	public void testVoirtableau() throws Exception {
		
		
		//capture de la sortie
		ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStreamCaptor));
		
		//on prépare une semaine en allant chercher des fichiers de l'arborescence sur lequels nous allons tester
		String dossierSemaine = "edt/src/doc/exemples-fichiers/config/planning/06.txt";
		String fichierRess = "edt/src/doc/exemples-fichiers/config/ressources.txt";
		
		//pour tester on peut lire une semaine à afficher et de preparer la création de la "semaine" 
		Edt edt = new Edt();
		Semaine[] semaine_a_tester = edt.lectureSemaine(dossierSemaine, fichierRess);
		
		//on appel à tester alors la méthode voir pour le tableau 
		
		edt.voir(semaine_a_tester, 6,"G4");
		
		//Maintenant on capture la sortie pour les Assert
		String TestAffichage = outputStreamCaptor.toString().trim();
		
		assertTrue(TestAffichage.contains("Planning de la semaine "+6+" pour le groupe G"+4+"\n"));
		assertTrue(TestAffichage.contains("\"|   Créneau   |\""));
		assertTrue(TestAffichage.contains("TD"));
		assertTrue(TestAffichage.contains("CM"));
		
		assertTrue(TestAffichage.contains("LUNDI"));
		assertTrue(TestAffichage.contains("MARDI"));
		assertTrue(TestAffichage.contains("MERCREDI"));
		assertTrue(TestAffichage.contains("JEUDI"));
		assertTrue(TestAffichage.contains("VENDREDI"));
		
		assertTrue(TestAffichage.contains("8:00 - 9:00"));
		assertTrue(TestAffichage.contains("9:00 - 10:00"));
		assertTrue(TestAffichage.contains("10:15 - 12:15"));
		assertTrue(TestAffichage.contains("13:30 - 15:30"));
		assertTrue(TestAffichage.contains("15:45 - 17:45"));
		
		
		//nous pouvons aussi rajouter le fais de compter le nombre de retour à la ligne
		//pour la taille parfaite du tableau
		
		String[] retourLigne = TestAffichage.split("\n");
		
		//Evidement pour une journée complète d'emploie du temps (8h-17h45)
		//le tableau fais 14 lignes de haut en bas au total
		
		assertEquals(14,retourLigne.length);
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}

