/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marbl.client;

import com.marbl.domain.PartInfo;
import com.marbl.domain.ShippingAddress;
import com.marbl.domain.WorkPerformedInfo;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 *
 * @author Bas
 */
public class ClientOrderReply {
    
    private String string;
    
    private String nameClient;
    private ShippingAddress shippingAddress;
    private String reparationDescription;
    private ArrayList<WorkPerformedInfo> workPerformedInfo;
    private BigDecimal totalForWorkPerformed;
    private ArrayList<PartInfo> partInfo;
    private BigDecimal totalForParts;
    private BigDecimal total;
    private String BankAccount;
    
    public ClientOrderReply(String s){
        string = s;
    }

    public ClientOrderReply(String nameClient, ShippingAddress shippingAddress, String reparationDescription, 
            ArrayList<WorkPerformedInfo> workPerformedInfo, BigDecimal totalForWorkPerformed, ArrayList<PartInfo> partInfo, 
            BigDecimal totalForParts, BigDecimal total, String BankAccount) {
        this.nameClient = nameClient;
        this.shippingAddress = shippingAddress;
        this.reparationDescription = reparationDescription;
        this.workPerformedInfo = workPerformedInfo;
        this.totalForWorkPerformed = totalForWorkPerformed;
        this.partInfo = partInfo;
        this.totalForParts = totalForParts;
        this.total = total;
        this.BankAccount = BankAccount;
    }
    
    
    public String getString()
    {
        return string;
    }
    
    public void setString(String s){
        string = s;
    }
}
