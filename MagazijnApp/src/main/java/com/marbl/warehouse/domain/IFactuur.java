package com.marbl.warehouse.domain;

import java.util.ArrayList;

public interface IFactuur {

    /**
     * Geeft de datum van de factuur.
     *
     * @return De datum van de factuur.
     */
    public String getDatum();

    /**
     * Geeft de ID van de klant.
     *
     * @return De ID van de klant.
     */
    public int getKlantId();

    /**
     * Geeft de ID van de factuur.
     *
     * @return De ID van de factuur.
     */
    public int getFactuurId();

    /**
     * Geeft een lijst met IFactuurRegel-objecten.
     *
     * @return Lijst met IFactuurRegel-objecten.
     */
    public ArrayList<IFactuurRegel> getOnderdelen();
}
