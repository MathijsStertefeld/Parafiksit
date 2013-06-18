/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marbl.client;

/**
 *
 * @author Bas
 */
public class ClientOrderReply {
    
    private String string;
    
    public ClientOrderReply(String s){
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
