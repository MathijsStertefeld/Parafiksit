/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marbl.client;

import com.marbl.domain.Contact;
import com.marbl.domain.PartInfo;
import com.marbl.domain.ShippingAddress;
import com.marbl.domain.WorkPerformedInfo;
import java.util.ArrayList;
import java.util.List;

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
    private List<WorkPerformedInfo> operations;
    private List<PartInfo> parts;

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

    public List<PartInfo> getParts()
    {
        return parts;
    }

    public List<WorkPerformedInfo> getOperations()
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
