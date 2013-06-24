/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marbl.warehouse.domain;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Eagle
 */
public class DatabaseTest
{

    static Database database;

    public DatabaseTest()
    {
    }

    @BeforeClass
    public static void setUpClass()
    {
        try
        {
            database = new Database();
        }
        catch (SQLException ex)
        {
            fail("Database connection failed.");
            //Logger.getLogger(DatabaseTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @AfterClass
    public static void tearDownClass()
    {
    }

    @Before
    public void setUp()
    {
        try
        {
            database.deleteOnderdeel(-1);            
        }
        catch (SQLException ex)
        {

        }
        try
        {
            database.deleteKlant(-1);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(DatabaseTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @After
    public void tearDown()
    {
    }

    /**
     * Test of insert method, of class Database.
     */
    @Test
    public void testInsert_Onderdeel() throws Exception
    {
        System.out.println("Testing insert Onderdeel");
        Onderdeel onderdeel = new Onderdeel(-1, "Test onderdeel", 1, 10);

        // Add onderdeel to database.
        database.insert(onderdeel);
        // Check to see if onderdeel has been added:
        Onderdeel onderdeelReturned = database.selectOnderdeel(-1);
        assertNotNull(onderdeelReturned);
        assertEquals(onderdeel.getCode(), onderdeelReturned.getCode());
        assertEquals(onderdeel.getOmschrijving(), onderdeelReturned.getOmschrijving());
        // Delete onderdeel from database
        database.deleteOnderdeel(-1);

        // Check to see if onderdeel is removed:
        Onderdeel onderdeelRemoved = database.selectOnderdeel(-1);

        assertNull(onderdeelRemoved);
    }

    /**
     * Test of insert method, of class Database.
     */
    @Test
    public void testInsert_Klant() throws Exception
    {
        System.out.println("Testing insert Onderdeel");
        Klant klant = new Klant(-1, "Test klant", "Test adres");

        // Add onderdeel to database.
        database.insert(klant);
        // Check to see if onderdeel has been added:
        ArrayList<Klant> klanten = database.selectKlanten();
        Klant klantReturned = null;
        for (Klant k : klanten)
        {
            if (k.getCode() == -1)
            {
                klantReturned = k;
                break;
            }
        }

        assertNotNull(klantReturned);
        assertEquals(klant.getCode(), klantReturned.getCode());
        assertEquals(klant.getNaam(), klantReturned.getNaam());
        // Delete onderdeel from database
        database.deleteKlant(-1);

        // Check to see if onderdeel is removed:

        klanten = database.selectKlanten();
        Klant klantRemoved = null;
        for (Klant k : klanten)
        {
            if (k.getCode() == -1)
            {
                klantRemoved = k;
                break;
            }
        }
        assertNull(klantRemoved);
    }
}
