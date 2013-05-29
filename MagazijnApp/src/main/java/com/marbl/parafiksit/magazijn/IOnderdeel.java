package com.marbl.parafiksit.magazijn;

public interface IOnderdeel {

    /**
     * Geeft de code(ID) van het desbetreffende Onderdeel-object.
     *
     * @return De code(ID) van het desbetreffende Onderdeel-object.
     */
    public int getCode();

    /**
     * Geeft de omschrijving van het desbetreffende Onderdeel-object.
     *
     * @return De omschrijving van het desbetreffende Onderdeel-object.
     */
    public String getOmschrijving();

    /**
     * Geeft het aantal van het desbetreffende Onderdeel-object.
     *
     * @return Het aantal van het desbetreffende Onderdeel-object.
     */
    public int getAantal();

    /**
     * Geeft de prijs van het desbetreffende Onderdeel-object.
     *
     * @return De prijs van het desbetreffende Onderdeel-object.
     */
    public int getPrijs();

    /**
     * Set het aantal van het desbetreffende Onderdeel-object.
     *
     * @param aantal De nieuwe waarde voor het aantal van het onderdeel-object.
     */
    public void setAantal(int aantal);
}
