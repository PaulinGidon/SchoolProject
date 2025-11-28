package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edt.*;

class TestJour {

@Test
void TestJour() {
Jour JourTest=new Jour();
CM cmtest=new CM();
TD tdtest=new TD();
assertEquals(JourTest.getCrenauxCM().size(), 0);
JourTest.addCM(cmtest);
assertEquals(JourTest.getCrenauxCM().size(), 1);
assertSame(JourTest.popCM(0),cmtest);
assertEquals(JourTest.getCrenauxTD().size(), 0);
JourTest.addTD(tdtest);
assertEquals(JourTest.getCrenauxTD().size(), 1);
assertSame(JourTest.popTD(0),tdtest);
}

}