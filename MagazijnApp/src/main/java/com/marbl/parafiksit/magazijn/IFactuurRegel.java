package com.marbl.parafiksit.magazijn;

public interface IFactuurRegel {

    /**
     * Geeft het ID van de factuur.
     *
     * @return Het ID van de factuur.
     */
    public int getFactuurId();

    /**
     * Geeft het ID van het onderdeel.
     *
     * @return Het ID van het onderdeel.
     */
    public int getOnderdeelCode();

    /**
     * Geeft het aantal van het onderdeel.
     *
     * @return Het aantal van het onderdeel.
     */
    public int getAantal();
}
