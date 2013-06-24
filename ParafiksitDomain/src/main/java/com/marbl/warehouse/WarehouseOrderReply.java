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
public class WarehouseOrderReply
{
    private Factuur factuur;

    public WarehouseOrderReply(Factuur factuur)
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
