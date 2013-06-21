/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marbl.client;

/**
 *
 * @author Leslie Aerts
 */
class Contact
{
    private String contactName;
    private String contactPhone;

    public Contact(String contactName, String contactPhone)
    {
        this.contactName = contactName;
        this.contactPhone = contactPhone;
    }

    public String getContactName()
    {
        return contactName;
    }

    public String getContactPhone()
    {
        return contactPhone;
    }

    public void setContactName(String contactName)
    {
        this.contactName = contactName;
    }

    public void setContactPhone(String contactPhone)
    {
        this.contactPhone = contactPhone;
    }
    
    
}
