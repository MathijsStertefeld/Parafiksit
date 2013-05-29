package com.marbl.parafiksit.magazijn;

public interface IKlant {

    /**
     * De naam van de klant.
     *
     * @return De naam van de klant.
     */
    public String getNaam();

    /**
     * Geeft het ID van de klant.
     *
     * @return Het ID van de klant.
     */
    public int getId();

    /**
     * Geeft het adres van de klant.
     *
     * @return Het adres van de klant.
     */
    public String getAdres();
}
