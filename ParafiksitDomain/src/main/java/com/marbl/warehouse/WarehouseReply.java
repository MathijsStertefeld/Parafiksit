/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marbl.warehouse;

import com.marbl.warehouse.domain.Factuur;

/**
 *
 * @author Bas
 */
public class WarehouseReply
{
    private Factuur factuur;

    public WarehouseReply(Factuur factuur)
    {
        this.factuur = factuur;
    }

    public Factuur getFactuur()
    {
        return factuur;
    }

    public void setFactuur(Factuur factuur)
    {
        this.factuur = factuur;
    }
}
