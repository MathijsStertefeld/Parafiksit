package com.marbl.parafiksit.magazijn;

import com.marbl.warehouse.domain.APInterface;
import com.marbl.warehouse.domain.Beheer;
import java.util.ArrayList;
import static org.junit.Assert.*;
import org.junit.Test;

public class APInterfaceTest {

    Beheer beheer;

    public APInterfaceTest() {
        beheer = new Beheer();
    }

    /**
     * Test of vraagOnderdeelOp method, of class APInterface.
     */
    @Test
    public void testVraagOnderdeelOp() {
        System.out.println("VraagOnderdeelOp");
        APInterface instance = new Beheer();
        int onderdeelCode = beheer.voegOnderdeelToe("Test", 25, 25);
        Object[] expResult = new Object[3];
        expResult[0] = "Test";
        expResult[1] = 25;
        expResult[2] = 25;
        Object[] result = instance.vraagOnderdeelOp(onderdeelCode);
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of FactuurToeVoegen method, of class APInterface.
     */
    @Test
    public void testFactuurToeVoegen() {
        System.out.println("FactuurToeVoegen");
        int code1 = beheer.voegOnderdeelToe("Test", 25, 25);
        int code2 = beheer.voegOnderdeelToe("Test", 25, 25);
        int klant1 = beheer.voegKlantToe("Test", "Test");
        int[] on1 = new int[2];
        int[] on2 = new int[2];
        on1[0] = code1;
        on1[1] = 5;
        on2[0] = code2;
        on2[1] = 5;

        ArrayList<int[]> onderdelen = new ArrayList<int[]>();
        onderdelen.add(on1);
        onderdelen.add(on2);
        APInterface instance = new Beheer();
        int factuurID = instance.FactuurToeVoegen(klant1, onderdelen);
        assertTrue(factuurID != -1);
    }
}