/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marbl.warehouse;

import com.marbl.warehouse.domain.Factuur;
import java.math.BigDecimal;

/**
 *
 * @author Bas
 */
public class WarehouseOrderReply
{
    private Factuur factuur;
    private BigDecimal totalPriceForParts;

    public WarehouseOrderReply(Factuur factuur)
    {
        this.factuur = factuur;
        totalPriceForParts = new BigDecimal(0);
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
