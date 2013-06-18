/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marbl.parafiksit;

/**
 *
 * @author Bas
 */
public class ParafiksitOrderRequest {
        private String string;
    
    public ParafiksitOrderRequest(String s){
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
