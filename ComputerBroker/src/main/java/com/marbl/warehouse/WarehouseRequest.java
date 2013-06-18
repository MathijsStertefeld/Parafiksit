/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marbl.warehouse;

/**
 *
 * @author Bas
 */
public class WarehouseRequest {
        private String string;
    
    public WarehouseRequest(String s){
        string = s;
    }
    
    public String getString()
    {
        return string;
    }
    
    public void setString(String s){
        string = s;
    }
}
