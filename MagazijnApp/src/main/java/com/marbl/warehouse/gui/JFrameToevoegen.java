package com.marbl.warehouse.gui;

import com.marbl.warehouse.domain.*;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class JFrameToevoegen extends javax.swing.JFrame implements ActionListener {

    private JComboBox jCbSelect;
    private JList list;
    private Magazijn magazijn;
    private String soort;
    private ArrayList<Onderdeel> onderdelen;
    private ArrayList<Klant> klanten;
    private Font fontB, fontN;
    private ArrayList<Component> componenten;

    /**
     * Creates new form JFrameToevoegen met ingoevoerde parameters. Deze
     * constructor mag alleen gebruikt worden voor het toevoegen van een
     * factuur. Gebruik voor het toevoegen van een onderdeel of een klant aub de
     * andere constructor.
     *
     * @param soort Een string die gelijk moet zijn aan: "Factuur", deze string
     * wordt gebruikt om het object te indentificeren.
     * @param magazijn Het hoofdmenu, zodat de setVisible methode weer veranderd kan
     * worden.
     * @param klanten Een lijst met klanten uit de database, waaruit gekozen kan
     * worden voor het aanmaken van de nieuwe factuur.
     * @param onderdelen Een lijst met onderdelen uit de database, waaruit
     * gekozen kan worden voor het aanmaken van de nieuwe factuur.
     */
    public JFrameToevoegen(Magazijn magazijn, String soort, Collection<Klant> klanten) {
        initComponents();
        componenten = new ArrayList<>();
        fontB = new Font("Times New Roman", 1, 12);
        fontN = new Font("Times New Roman", 0, 12);
        this.magazijn = magazijn;
        this.setLocation(400, 250);
        this.soort = soort;
        onderdelen = new ArrayList<>();
        this.klanten = new ArrayList<Klant>(klanten);
        jCbSelect = new JComboBox();
        jCbSelect.setBounds(20, 40, 190, 20);
        add(jCbSelect);
        for (Klant kl : klanten) {
            jCbSelect.addItem(kl.getCode() + ":  " + kl.getNaam());
        }
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.setTitle("Toevoegen:");
        createFactuurGUI();
    }

    /**
     * Creates new form JFrameToevoegen met ingoevoerde parameters. Deze
     * constructor mag alleen gebruikt worden voor het toevoegen van een
     * onderdeel of een klant. Gebruik voor het toevoegen van een factuur aub de
     * andere constructor.
     *
     * @param soort Een string die gelijk moet zijn aan: "Onderdeel" of "Klant",
     * deze string wordt gebruikt om het object te identificeren.
     * @param magazijn Het hoofdmenu, zodat de setVisible methode weer veranderd kan
     * worden.
     */
    public JFrameToevoegen(Magazijn magazijn, String soort) {
        componenten = new ArrayList<>();
        initComponents();
        fontB = new Font("Times New Roman", 1, 12);
        fontN = new Font("Times New Roman", 0, 12);
        this.magazijn = magazijn;
        this.soort = soort;
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.setTitle("Toevoegen:");
        
        switch (soort) {
            case "Klant": {
                createKlantGUI();
                break;
            }
            case "Onderdeel": {
                createOnderdeelGUI();
                break;
            }
        }
    }

    /**
     * Wordt gebruikt om een regel door te geven vanaf
     * JFrameFactuurRegelToevoegen voor Factuur.
     */
    public void giveString(String text, Onderdeel onderdeel) {
        addListItem(list, text);
        onderdelen.add(onderdeel);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jBtClose = new javax.swing.JButton();
        javax.swing.JButton jBtToevoegen = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.marbl.warehouse.MagazijnApplicatieApp.class).getContext().getResourceMap(JFrameToevoegen.class);
        jBtClose.setText(resourceMap.getString("jBtClose.text")); // NOI18N
        jBtClose.setName("jBtClose"); // NOI18N
        jBtClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtCloseActionPerformed(evt);
            }
        });

        jBtToevoegen.setText(resourceMap.getString("jBtToevoegen.text")); // NOI18N
        jBtToevoegen.setName("jBtToevoegen"); // NOI18N
        jBtToevoegen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtToevoegenActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBtToevoegen, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtClose, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(213, Short.MAX_VALUE)
                .addComponent(jBtToevoegen, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtClose, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * De methode die wordt aangeroepen als het form geclosed moet worden. de
     * setVisible(true) van het hoofdmenu wordt aangeroepen en dit form wordt
     * gedisposed.
     *
     * @param evt Het event
     */
    private void jBtCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtCloseActionPerformed
        magazijn.setVisible(true);
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_jBtCloseActionPerformed

    /**
     * De methode die wordt aangeroepen als een object toegevoegd moet worden.
     *
     * @param evt Het event
     */
    private void jBtToevoegenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtToevoegenActionPerformed
        try {
            switch (soort) {
                case "Klant": {
                    JTextField field = (JTextField) componenten.get(1);
                    String naam = field.getText();
                    field = (JTextField) componenten.get(2);
                    String adres = field.getText();
                    int klantID = 0;
                    for (Klant klant : magazijn.getDatabase().selectKlanten()) {
                        if (klantID <= klant.getCode()) {
                            klantID = klant.getCode() + 1;
                        }
                    }
                    magazijn.getDatabase().insert(new Klant(klantID, naam, adres));
                    JOptionPane.showMessageDialog(this, "De klant is correct toegevoegd aan de database.", "Gelukt", JOptionPane.INFORMATION_MESSAGE);
                    magazijn.setVisible(true);
                    this.setVisible(false);
                    this.dispose();
                    break;
                }
                case "Onderdeel": {
                    JTextField field = (JTextField) componenten.get(1);
                    String omschrijving = field.getText();
                    field = (JTextField) componenten.get(2);
                    int aantal = Integer.parseInt(field.getText());
                    field = (JTextField) componenten.get(3);
                    int prijs = Integer.parseInt(field.getText());
                    int onderdeelCode = 0;
                    for (Onderdeel onderdeel : magazijn.getDatabase().selectOnderdelen()) {
                        if (onderdeelCode <= onderdeel.getCode()) {
                            onderdeelCode = onderdeel.getCode() + 1;
                        }
                    }
                    magazijn.getDatabase().insert(new Onderdeel(onderdeelCode, omschrijving, aantal, prijs));
                    JOptionPane.showMessageDialog(this, "Het onderdeel is correct toegevoegd aan de database.", "Gelukt", JOptionPane.INFORMATION_MESSAGE);
                    magazijn.setVisible(true);
                    this.setVisible(false);
                    this.dispose();
                    break;
                }
                case "Factuur": {
                    ArrayList<FactuurRegel> factuurRegels = new ArrayList<>();
                    int factuurCode = 0;
                    for (Factuur factuur : magazijn.getDatabase().selectFacturen()) {
                        if (factuurCode <= factuur.getCode()) {
                            factuurCode = factuur.getCode() + 1;
                        }
                    }
                    for (int i = 0; i < list.getModel().getSize(); i++) {
                        factuurRegels.add(new FactuurRegel(factuurCode, onderdelen.get(i).getCode(), onderdelen.get(i).getAantal()));
                    }
                    Calendar cal = new GregorianCalendar();
                    int jaar = cal.get(Calendar.YEAR);
                    int maand  = cal.get(Calendar.MONTH) +1;
                    int dag = cal.get(Calendar.DAY_OF_MONTH);
                    String datum = Integer.toString(dag) + "-" + Integer.toString(maand) + "-" + Integer.toString(jaar);
                    int klantID = klanten.get(jCbSelect.getSelectedIndex()).getCode();
                    magazijn.getDatabase().insert(new Factuur(factuurCode, klantID, datum, factuurRegels));
                    JOptionPane.showMessageDialog(this, "De factuur is correct toegevoegd aan de database.", "Informatie", JOptionPane.INFORMATION_MESSAGE);
                    magazijn.setVisible(true);
                    this.setVisible(false);
                    this.dispose();
                    break;
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex, "Fout", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jBtToevoegenActionPerformed

    /**
     * Wordt aangeroepen als het toe te voegen object een onderdeel is. GUI
     * wordt gecreerd.
     */
    private void createOnderdeelGUI() {
        componenten.clear();
        JLabel lbInfo = new JLabel("Nieuw onderdeel aanmaken:");
        lbInfo.setBounds(20, 40, 250, 30);
        lbInfo.setFont(new Font("Times New Roman", 1, 15));
        add(lbInfo);
        componenten.add(lbInfo);

        JLabel lbOmschr = new JLabel("Omschr:");
        lbOmschr.setBounds(49, 90, 80, 30);
        lbOmschr.setFont(fontB);
        add(lbOmschr);
        componenten.add(lbOmschr);

        JLabel lbAantal = new JLabel("Aantal:");
        lbAantal.setBounds(50, 120, 80, 30);
        lbAantal.setFont(fontB);
        add(lbAantal);
        componenten.add(lbAantal);

        JLabel lbPrijs = new JLabel("Prijs in centen:");
        lbPrijs.setBounds(15, 150, 80, 30);
        lbPrijs.setFont(fontB);
        add(lbPrijs);
        componenten.add(lbPrijs);

        JTextField tfOmschr = new JTextField();
        tfOmschr.setBounds(90, 92, 100, 20);
        tfOmschr.setFont(fontN);
        add(tfOmschr);
        componenten.add(1, tfOmschr);

        JTextField tfAantal = new JTextField();
        tfAantal.setBounds(90, 122, 100, 20);
        tfAantal.setFont(fontN);
        add(tfAantal);
        componenten.add(2, tfAantal);

        JTextField tfPrijs = new JTextField();
        tfPrijs.setBounds(90, 152, 100, 20);
        tfPrijs.setFont(fontN);
        add(tfPrijs);
        componenten.add(3, tfPrijs);
    }

    /**
     * Wordt aangeroepen als het toe te voegen object een klant is. GUI wordt
     * gecreerd.
     */
    private void createKlantGUI() {
        componenten.clear();
        JLabel lbInfo = new JLabel("Nieuwe klant aanmaken:");
        lbInfo.setBounds(20, 40, 250, 30);
        lbInfo.setFont(new Font("Times New Roman", 1, 15));
        add(lbInfo);
        componenten.add(lbInfo);

        JLabel lbNaam = new JLabel("Naam:");
        lbNaam.setBounds(28, 90, 80, 30);
        lbNaam.setFont(fontB);
        add(lbNaam);
        componenten.add(lbNaam);

        JLabel lbAdres = new JLabel("Adres:");
        lbAdres.setBounds(27, 120, 80, 30);
        lbAdres.setFont(fontB);
        add(lbAdres);
        componenten.add(lbAdres);

        JTextField tfNaam = new JTextField();
        tfNaam.setBounds(70, 92, 150, 20);
        tfNaam.setFont(fontN);
        add(tfNaam);
        componenten.add(1, tfNaam);

        JTextField tfAdres = new JTextField();
        tfAdres.setBounds(70, 122, 150, 20);
        tfAdres.setFont(fontN);
        add(tfAdres);
        componenten.add(2, tfAdres);
    }

    /**
     * Wordt aangeroepen als het toe te voegen object gelijk staat aan:
     * "Factuur"
     */
    private void createFactuurGUI() {
        componenten.clear();
        JLabel lbInfo = new JLabel("Selecteer de klant:");
        lbInfo.setBounds(40, 5, 250, 30);
        lbInfo.setFont(new Font("Times New Roman", 1, 15));
        add(lbInfo);
        componenten.add(lbInfo);

        list = new JList();
        list.setBounds(10, 70, 208, 100);
        list.setFont(fontN);
        add(list);
        componenten.add(list);

        JButton bt = new JButton();
        bt.setBounds(10, 175, 100, 20);
        bt.setFont(fontN);
        bt.setText("Regel +");
        bt.addActionListener(this);
        bt.setActionCommand("RegelToevoegen");
        add(bt);
        componenten.add(bt);

        JButton btr = new JButton();
        btr.setBounds(120, 175, 100, 20);
        btr.setFont(fontN);
        btr.setText("Reset List");
        btr.addActionListener(this);
        btr.setActionCommand("Reset");
        add(btr);
        componenten.add(btr);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtClose;
    // End of variables declaration//GEN-END:variables

    /**
     * Wordt aangeroepen als de ComboBox index veranderd.
     *
     * @param e Het event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            switch (e.getActionCommand()) {
                case "RegelToevoegen": {
                    JFrameFactuurRegelToevoegen frt = new JFrameFactuurRegelToevoegen(this, magazijn.getDatabase().selectOnderdelen());
                    frt.setVisible(true);
                    this.setVisible(false);
                    break;
                }
                case "Reset": {
                    clearList(list);
                    break;
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex, "Fout", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Verwijdert alle elementen in de list.
     *
     * @param jlist De desbetreffende JList.
     */
    private void clearList(JList jlist) {
        Object[] SET = new Object[0];
        jlist.setListData(SET);
    }

    /**
     * Voegt een element toe aan de list.
     *
     * @param jlist De desbetreffende JList.
     * @param Text De text die moet worden weergegeven.
     */
    private void addListItem(JList jlist, String Text) {
        Object[] SET = new Object[jlist.getModel().getSize() + 1];
        for (int i = 0; i < jlist.getModel().getSize(); i++) {
            SET[i] = jlist.getModel().getElementAt(i);
        }

        SET[jlist.getModel().getSize()] = Text;
        jlist.setListData(SET);
        jlist.ensureIndexIsVisible(jlist.getModel().getSize() - 1);
    }
}
