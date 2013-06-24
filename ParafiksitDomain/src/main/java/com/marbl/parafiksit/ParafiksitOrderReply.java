/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marbl.parafiksit;

import com.marbl.client.domain.ShippingAddress;
import com.marbl.client.domain.WorkPerformedInfo;
import java.util.List;

/**
 *
 * @author Bas
 */
public class ParafiksitOrderReply {
        private String nameClient;
        private ShippingAddress shipping;
        private List<WorkPerformedInfo> workPerformed;
        private Integer totalPriceForWorkPerformed;
        private String bankAccount;

    public ParafiksitOrderReply(String nameClient, ShippingAddress shipping, List<WorkPerformedInfo> workPerformed, Integer TotalPriceForWorkPerformed, String bankAccount)
    {
        this.nameClient = nameClient;
        this.shipping = shipping;
        this.workPerformed = workPerformed;
        this.totalPriceForWorkPerformed = TotalPriceForWorkPerformed;
        this.bankAccount = bankAccount;
    }

    public String getBankAccount()
    {
        return bankAccount;
    }

    public String getNameClient()
    {
        return nameClient;
    }

    public ShippingAddress getShipping()
    {
        return shipping;
    }

    public int getTotalPriceForWorkPerformed()
    {
        return TotalPriceForWorkPerformed;
    }

    public List<WorkPerformedInfo> getWorkPerformed()
    {
        return workPerformed;
    }
 
}
