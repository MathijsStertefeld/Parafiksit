package com.marbl.warehouse.domain;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {

    /**
     * Het connectie-object voor de database.
     */
    private Connection connection = null;

    /**
     * Maakt een nieuw databaseConnectie-object. Zoekt connectie met de database
     * met de properties die op worden gehaald uit de file: db.properties.
     */
    public Database() throws SQLException {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("src/main/resources/db.properties"));

            String serverEnPort = properties.getProperty("ServerEnPort");
            String url = "jdbc:oracle:thin:@" + serverEnPort + ":xe";
            String username = properties.getProperty("Username");
            String password = properties.getProperty("Password");

            Class.forName("oracle.jdbc.OracleDriver");
            connection = DriverManager.getConnection(url, username, password);
            connection.setAutoCommit(true);
        } catch (ClassNotFoundException | IOException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //<editor-fold desc="SELECT Methoden">
    /**
     * Haalt zo mogelijk een lijst van onderdelen op uit de database.
     *
     * @return Een lijst met Onderdeel-objecten, als het fout gaat of de tabel
     * leeg is wordt een lege lijst gereturneerd.
     */
    public ArrayList<Onderdeel> selectOnderdelen() throws SQLException {
        ArrayList<Onderdeel> onderdelen = new ArrayList<>();
        String sql = "SELECT * FROM ONDERDEEL";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int code = rs.getInt("CODE");
                    String omschrijving = rs.getString("OMSCHRIJVING");
                    int aantal = rs.getInt("AANTAL");
                    int prijs = rs.getInt("PRIJS");
                    onderdelen.add(new Onderdeel(code, omschrijving, aantal, prijs));
                }
            }
        }

        return onderdelen;
    }

    /**
     * Haalt zo mogelijk een lijst van factuurregels op uit de database.
     *
     * @return Een lijst met FactuurRegel-objecten, als het fout gaat of de
     * tabel leeg is wordt een lege lijst gereturneerd.
     */
    public ArrayList<FactuurRegel> selectFactuurRegels() throws SQLException {
        ArrayList<FactuurRegel> factuurRegels = new ArrayList<>();
        String sql = "SELECT * FROM FACTUURREGEL";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int factuurCode = rs.getInt("FACTUURCODE");
                    int onderdeelCode = rs.getInt("ONDERDEELCODE");
                    int aantal = rs.getInt("AANTAL");
                    factuurRegels.add(new FactuurRegel(factuurCode, onderdeelCode, aantal));
                }
            }
        }
        
        return factuurRegels;
    }

    /**
     * Haalt zo mogelijk een lijst van facturen op uit de database.
     *
     * @return Een lijst met Factuur-objecten, als het fout gaat of de tabel
     * leeg is wordt een lege lijst gereturneerd.
     */
    public ArrayList<Factuur> selectFacturen() throws SQLException {
        ArrayList<Factuur> facturen = new ArrayList<>();
        ArrayList<FactuurRegel> factuurRegels = selectFactuurRegels();
        String sql = "SELECT * FROM FACTUUR";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                int index = 0;
                
                while (rs.next()) {
                    int code = rs.getInt("CODE");
                    int klantID = rs.getInt("KLANTCODE");
                    String datum = rs.getString("DATUM");
                    ArrayList<FactuurRegel> regels = new ArrayList<>();
                    
                    for (FactuurRegel factuurRegel : factuurRegels) {
                        if (factuurRegel.getFactuurCode() == code) {
                            regels.add(factuurRegel);
                        }
                    }
                    
                    facturen.add(new Factuur(code, klantID, datum, regels));
                    index++;
                }
            }
        }

        return facturen;
    }

    /**
     * Haalt zo mogelijk een lijst van klanten op uit de database.
     *
     * @return Ee lijst met Klant-objecten, als het fout gaat of de tabel leeg
     * is wordt een lege lijst gereturneerd.
     */
    public ArrayList<Klant> selectKlanten() throws SQLException {
        ArrayList<Klant> klanten = new ArrayList<>();
        String sql = "SELECT * FROM KLANT";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int code = rs.getInt("CODE");
                    String naam = rs.getString("NAAM");
                    String adres = rs.getString("ADRES");
                    Klant kl = new Klant(code, naam, adres);
                    klanten.add(kl);
                }
            }
        }
        
        return klanten;
    }

    /**
     * Haalt zo mogelijk het onderdeel op uit de database die hoort bij de
     * ingevoerde onderdeelcode.
     *
     * @param onderdeelCode De code van het onderdeel dat opgehaald moet worden.
     * @return Een Onderdeel-object dat de waardes heeft van een row uit de
     * Onderdeel-tabel.
     */
    public Onderdeel selectOnderdeel(int onderdeelCode) throws SQLException {
        Onderdeel onderdeel = null;
        String sql = "SELECT * FROM ONDERDEEL WHERE CODE = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(0, onderdeelCode);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int code = rs.getInt("CODE");
                    String omschr = rs.getString("OMSCHRIJVING");
                    int aantal = rs.getInt("AANTAL");
                    int prijs = rs.getInt("PRIJS");
                    onderdeel = new Onderdeel(code, omschr, aantal, prijs);
                }
            }
        }

        return onderdeel;
    }
    //</editor-fold>

    //<editor-fold desc="INSERT Methoden">
    /**
     * Voegt een Onderdeel toe in de database.
     *
     * @param onderdeel Het onderdeel dat moet worden toegevoegd in de database.
     * @return True als het onderdeel correct is toegevoegd, anders false.
     */
    public void insert(Onderdeel onderdeel) throws SQLException {
        String sql = "INSERT INTO ONDERDEEL VALUES (?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, onderdeel.getCode());
            ps.setString(2, onderdeel.getOmschrijving());
            ps.setInt(3, onderdeel.getAantal());
            ps.setInt(4, onderdeel.getPrijs());
            ps.executeUpdate();
        }
    }

    /**
     * Voegt een klant toe aan de database.
     *
     * @param klant De klant die moet worden toegevoegd in de database.
     * @return True als de klant correct is toegevoegd, anders false.
     */
    public void insert(Klant klant) throws SQLException {
        String sql = "INSERT INTO KLANT VALUES (?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, klant.getCode());
            ps.setString(2, klant.getNaam());
            ps.setString(3, klant.getAdres());
            ps.executeUpdate();
        }
    }

    /**
     * Voegt een row toe aan de factuurregel tabel met bijbehorende factuurCode,
     * OnderdeelID en het aantal van het Onderdeel. (Deze methode wordt alleen
     * gebruikt door de methode insert in deze klasse).
     *
     * @param factuurRegel De regel die moet worden toegevoegd in de database.
     */
    public void insert(FactuurRegel factuurRegel) throws SQLException {
        String sql = "INSERT INTO FACTUURREGEL VALUES (?,?,?)";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, factuurRegel.getFactuurCode());
            ps.setInt(2, factuurRegel.getOnderdeelCode());
            ps.setInt(3, factuurRegel.getAantal());
            ps.executeUpdate();
        }
    }

    /**
     * Voegt een factuur toe aan de database, met bijbehorende ingevoerde
     * waardes. Voegt ook de factuurregels toe aan de database, waarin staat:
     * FactuurCode, OnderdeelID en aantal van het onderdeel.
     *
     * @param factuur De factuur die moet worden toegevoegd.
     * @return True als de factuur correct is toegevoegd, anders false.
     */
    public void insert(Factuur factuur) throws SQLException {
        String sql = "INSERT INTO FACTUUR VALUES (?,?,?)";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, factuur.getCode());
            ps.setInt(2, factuur.getKlantCode());
            ps.setString(3, factuur.getDatum());
            ps.executeUpdate();

            for (FactuurRegel factuurRegel : factuur.getRegels()) {
                insert(factuurRegel);
            }
        }
    }
    //</editor-fold>

    //<editor-fold desc="DELETE Methoden">
    /**
     * Verwijdert het onderdeel dat hoort bij het ingevoerde code.
     *
     * @param code De code van het onderdeel dat moet worden verwijdert.
     * @return True als het onderdeel correct is verwijdert, anders false.
     */
    public void deleteOnderdeel(int code) throws SQLException {
        String sql = "DELETE FROM ONDERDEEL WHERE CODE = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, code);
            ps.executeUpdate();
        }
    }

    /**
     * Verwijdert de klant uit de database die hoort bij de ingevoerde code.
     *
     * @param code De code van de klant die verwijdert moet worden.
     * @return True als de klant correct is verwijdert, anders false.
     */
    public void deleteKlant(int code) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("DELETE FROM KLANT WHERE CODE = ?")) {
            ps.setInt(1, code);
            ps.executeUpdate();
        }
    }
//</editor-fold>

    //<editor-fold desc="UPDATE Methoden">
    /**
     * Verandert het onderdeel dat hoort bij de ingoeverde code, de nieuwe
     * waardes zitten in het Onderdeel-object. (Alleen de omschrijving, het
     * aantal en de prijs kan veranderd worden, de code blijft hetzelfde!).
     *
     * @param onderdeelCode De code die hoort bij het onderdeel dat verandert
     * moet worden.
     * @param onderdeel De nieuwe gegevens van het onderdeel.
     * @return True als het onderdeel correct is veranderd, anders false.
     */
    public void update(Onderdeel onderdeel) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("UPDATE ONDERDEEL SET OMSCHRIJVING = ?, AANTAL = ?, PRIJS = ? WHERE CODE = ?")) {
            ps.setString(1, onderdeel.getOmschrijving());
            ps.setInt(2, onderdeel.getAantal());
            ps.setInt(3, onderdeel.getPrijs());
            ps.setInt(4, onderdeel.getCode());
            ps.executeUpdate();
        }
    }

    /**
     * Verandert de klant die hoort bij de ingevoerde code, de nieuwe waardes
     * zitten in het Klant-object. (Alleen de naam en het adres kan veranderd
     * worden, de code blijft hetzelfde!).
     *
     * @param klant De nieuwe gegevens van de klant.
     * @return True als de klant correct is veranderd, anders false.
     */
    public void update(Klant klant) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("UPDATE KLANT SET NAAM = ?, ADRES = ? WHERE CODE = ?")) {
            ps.setString(1, klant.getNaam());
            ps.setString(2, klant.getAdres());
            ps.setInt(3, klant.getCode());
            ps.executeUpdate();
        }
    }
    //</editor-fold>
}
