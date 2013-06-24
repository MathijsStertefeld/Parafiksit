/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marbl.client.domain;

import java.math.BigDecimal;

/**
 *
 * @author Bas
 */
public class PartInfo{
    
    private String name;
    private BigDecimal price;
    
    public PartInfo(String name){
        this.name = name;
    }
    
    public BigDecimal getPrice(){
        return price;
    }
    
    public void setPrice(BigDecimal price){
        this.price = price;
    }
    
    public String getName(){
        return name;
    }
}
