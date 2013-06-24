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
public class WorkPerformedInfo {
    
    private String description;
    
    public WorkPerformedInfo(String description){
        this.description = description;
    }
    
    public String getDescription(){
        return description;
    }
}
