/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marbl.client;

/**
 *
 * @author Bas
 */
public class ClientOrderRequest {
    
    private String string;
    
    public ClientOrderRequest(String s){
        string = s;
    }
    
    public String getString()
    {
        return string;
    }
    
    public void setString(String s){
        string = s;
    }
    
    public Boolean containsParts(){
        return true;//placeholder
    }
}
