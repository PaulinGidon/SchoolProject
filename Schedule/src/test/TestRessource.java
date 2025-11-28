package test;

import edt.Edt;
import edt.Ressource;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestRessource {

	@Test
	void test_ressource() throws IOException {
		
		//la meilleur manière de tester si la lecture du fichier ressource est fonctionnel
		//est de creer un fichier temporaire et de vérifier si la méthode lis bien le fichier
		
		File fichier_temp = File.createTempFile("test_ressource", ".txt");
		
		//création d'une ressource fictive et test dessus
		String r = "[R215]\n"
				+ "nom: matière_fictive\n"
				+ "semaines: 14, 17, 18\n"
				+ "TD: 10\n"
				+ "CM: 5\n"
				+ "responsable: DUPONT JEAN \n"
				+ "intervenants: DUPONT JEAN, DUPONT JEAN, MARTIN Adrien, MARTIN Adrien";
		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fichier_temp))) {
			writer.write(r);
		}
		
		//Nous appelons un Edt pour pouvoir utiliser ses méthodes , ici la méthode de lectureRessource
		Edt edt = new Edt();
		ArrayList<Ressource> verification = edt.LectureRessource(fichier_temp);
		
		Ressource res = verification.get(0);
		
		
		//vérifie si la chaine de caractère suivant "nom: " correspond bien
        assertEquals("matière_fictive", res.getNom());
        //verifie de même pour le responsable
        assertEquals("DUPONT JEAN",res.getResponsable());
        
        //ces 2 assert vérifie si la méthode compte bien le nombre de semaine et le nombre d'intervenants
        assertEquals(4,res.getEnseignants());
        assertEquals(3,res.getSemainesPrevues());
        
		
		
		
		//TEST
		
		
				
		
	}

}
