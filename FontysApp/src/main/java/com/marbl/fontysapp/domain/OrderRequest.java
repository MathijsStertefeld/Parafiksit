/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marbl.fontysapp.domain;

import java.util.ArrayList;

/**
 *
 * @author Leslie Aerts
 */
public class OrderRequest
{
    private String clientName;
    private String contactName;
    private String contactPhone;
    private String shippingStreet;
    private String shippingNumber;
    private String shippingPlace;
    private String shippingPostcalCode;
    private String comments;
    private ArrayList<String> operations;
    private ArrayList<String> parts;

    public OrderRequest(String clientName, String contactName, String contactPhone, String shippingStreet, String shippingNumber, String shippingPlace, String shippingPostcalCode, ArrayList<String> operations, ArrayList<String> parts)
    {
        this.clientName = clientName;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
        this.shippingStreet = shippingStreet;
        this.shippingNumber = shippingNumber;
        this.shippingPlace = shippingPlace;
        this.shippingPostcalCode = shippingPostcalCode;
        this.operations = operations;
        this.parts = parts;
    }

    public String getClientName()
    {
        return clientName;
    }

    public void setClientName(String clientName)
    {
        this.clientName = clientName;
    }

    public String getContactName()
    {
        return contactName;
    }

    public void setContactName(String contactName)
    {
        this.contactName = contactName;
    }

    public String getContactPhone()
    {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone)
    {
        this.contactPhone = contactPhone;
    }

    public String getShippingStreet()
    {
        return shippingStreet;
    }

    public void setShippingStreet(String shippingStreet)
    {
        this.shippingStreet = shippingStreet;
    }

    public String getShippingNumber()
    {
        return shippingNumber;
    }

    public void setShippingNumber(String shippingNumber)
    {
        this.shippingNumber = shippingNumber;
    }

    public String getShippingPostcalCode()
    {
        return shippingPostcalCode;
    }

    public void setShippingPostcalCode(String shippingPostcalCode)
    {
        this.shippingPostcalCode = shippingPostcalCode;
    }

    public String getComments()
    {
        return comments;
    }

    public void setComments(String comments)
    {
        this.comments = comments;
    }

    public ArrayList<String> getOperations()
    {
        return operations;
    }

    public void setOperations(ArrayList<String> operations)
    {
        this.operations = operations;
    }

    public ArrayList<String> getParts()
    {
        return parts;
    }

    public void setParts(ArrayList<String> parts)
    {
        this.parts = parts;
    }
}
