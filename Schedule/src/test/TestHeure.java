package test;
import edt.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edt.Heure;

class TestHeure {

	@Test
	void testHeureSup() {
		assertThrows(Exception.class, () -> {
            new Heure(24, 21);
        });
	}
	@Test
	void testHeureInf() {
		assertThrows(Exception.class, () -> {
            new Heure(-2, 21);
        });
	}
	@Test
	void testMinup() {
		assertThrows(Exception.class, () -> {
            new Heure(16,79);
        });
	}
	@Test
	void testMinInf() {
		assertThrows(Exception.class, () -> {
            new Heure(16, -9);
        });
	}
	
	@Test
	void testBon() {
		assertDoesNotThrow(() -> {
            new Heure(16, 00);
        });
	}
	
	@Test
	void testDiff() throws Exception {
		
		Heure h1 = new Heure(10,30);
		Heure h2 = new Heure(10,30);
		assertEquals(0, h1.difference(h2));
		//ceci est censé renvoyer True étant donné qu'il n'y a aucune différence entre ces 2 heures
		
		Heure h3 = new Heure(11,00);
		Heure h4 = new Heure(10,30);
		assertEquals(30, h3.difference(h4));
		
		Heure h5 = new Heure(12,00);
		Heure h6 = new Heure(9,00);
		assertEquals(180, h5.difference(h6));
		
		Heure h7 = new Heure(12,10);
		Heure h8 = new Heure(12,00);
		assertEquals(10, h7.difference(h8));
		
		
		
	}

}
