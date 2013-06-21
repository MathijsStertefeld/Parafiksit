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
    private String teststring;
    
    public ParafiksitOrderRequest(String s){
        teststring = s;
    }
    
    public String getString()
    {
        return teststring;
    }
    
    public void setString(String s){
        teststring = s;
    }
}
