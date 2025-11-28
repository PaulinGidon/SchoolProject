package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import edt.Edt;
import edt.Semaine;

class TestSemaine {

	@Test
	void test_lecture_semaine() throws IOException {
		File fichier_temp_ressource = File.createTempFile("test_ressource", ".txt");
		File fichier_temp_semaine = File.createTempFile("test_seamaine", ".txt");
		
		//création d'une ressource fictive et test dessus
		String ressource_r = "[R215]\n"
				+ "nom: matière_fictive\n"
				+ "semaines: 14, 17, 18\n"
				+ "TD: 10\n"
				+ "CM: 5\n"
				+ "responsable: DUPONT JEAN \n"
				+ "intervenants: DUPONT JEAN, DUPONT JEAN, MARTIN Adrien, MARTIN Adrien";
		
		//Création d'un fichier semaine temporaire
		String semaine_r = "# semaine 6\n"
				+ "\n"
				+ "## LUNDI\n"
				+ "  * 08:00 - 09:00\n"
				+ "    CM R206 (CHEVALIER Laura)\n"
				+ "  * 09:00 - 10:00\n"
				+ "  * 10:15 - 12:15\n"
				+ "    TD-G1 R206 (LAURENT Louis)\n"
				+ "    TD-G4 R207 (LEROY Yanis)\n"
				+ "    TD-G2 R212 (DURAND Noah)\n"
				+ "    TD-G3 R213 (DAVID Quentin)\n"
				+ "  * 13:30 - 15:30\n"
				+ "    TD-G2 R206 (LAURENT Louis)\n"
				+ "    TD-G1 R207 (LEROY Yanis)\n"
				+ "    TD-G4 R212 (DURAND Noah)\n"
				+ "  * 15:45 - 17:45";
		
		try (BufferedWriter writer_r = new BufferedWriter(new FileWriter(fichier_temp_ressource))) {
			writer_r.write(ressource_r);
			
		}
		try (BufferedWriter writer_s = new BufferedWriter(new FileWriter(fichier_temp_semaine))) {
			writer_s.write(semaine_r);
			
		}
		
		//nous créons un edt pour travailler de manière réel
		
		Edt edt = new Edt();
		//Semaine[] semaines_test = edt.LectureRessource(fichier_temp_semaine,fichier_temp_ressource);
		
		
	}

}
