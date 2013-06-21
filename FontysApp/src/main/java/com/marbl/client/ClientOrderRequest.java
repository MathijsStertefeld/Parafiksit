/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marbl.client;

import com.marbl.fontysapp.domain.Contact;
import com.marbl.fontysapp.domain.PartInfo;
import com.marbl.fontysapp.domain.ShippingAddress;
import com.marbl.fontysapp.domain.WorkPerformedInfo;
import java.util.ArrayList;

/**
 *
 * @author Leslie Aerts
 */
public class ClientOrderRequest
{

    private String clientName;
    private Contact contact;
    private ShippingAddress shippingAddress;
    private String comments;
    private ArrayList<WorkPerformedInfo> operations;
    private ArrayList<PartInfo> parts;

    public ClientOrderRequest(String clientName, String contactName, String contactPhone, String shippingStreet, String shippingNumber, String shippingPlace, String shippingPostalCode, ArrayList<WorkPerformedInfo> operations, ArrayList<PartInfo> parts)
    {
        this.clientName = clientName;
        this.contact = new Contact(contactName, contactPhone);
        this.shippingAddress = new ShippingAddress(shippingStreet, shippingNumber, shippingPostalCode, shippingPlace);
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

    public Contact getContact()
    {
        return contact;
    }

    public void setContact(Contact contact)
    {
        this.contact = contact;
    }

    public String getComments()
    {
        return comments;
    }

    public void setComments(String comments)
    {
        this.comments = comments;
    }

    public void setShippingAddress(ShippingAddress shippingAddress)
    {
        this.shippingAddress = shippingAddress;
    }

    public void setParts(ArrayList<PartInfo> parts)
    {
        this.parts = parts;
    }

    public void setOperations(ArrayList<WorkPerformedInfo> operations)
    {
        this.operations = operations;
    }

    public ShippingAddress getShippingAddress()
    {
        return shippingAddress;
    }

    public ArrayList<PartInfo> getParts()
    {
        return parts;
    }

    public ArrayList<WorkPerformedInfo> getOperations()
    {
        return operations;
    }

    public String getString()
    {
        return clientName + " " + comments;
    }

    public boolean containsParts()
    {
        if (parts.size() > 0)
        {
            return true;

        } else
        {
            return false;
        }
    }

}
