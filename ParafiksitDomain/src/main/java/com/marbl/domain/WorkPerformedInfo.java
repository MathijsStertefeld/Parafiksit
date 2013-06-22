/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marbl.domain;

import java.math.BigDecimal;

/**
 *
 * @author Bas
 */
public class WorkPerformedInfo {
    
    private String description;
    private BigDecimal price;
    
    public WorkPerformedInfo(String description){
        this.description = description;
    }
    
    public void setPrice(BigDecimal price){
        this.price = price;
    }
        
    public BigDecimal getPrice(){
        return price;
    }
    
    public String getDescription(){
        return description;
    }
}
