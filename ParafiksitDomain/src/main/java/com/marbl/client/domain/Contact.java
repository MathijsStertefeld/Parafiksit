/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marbl.client.domain;

/**
 *
 * @author Leslie Aerts
 */
public class Contact
{
    private String firstName;
    private String lastName;
    private String contactPhone;

    public Contact(String firstName, String lastName, String contactPhone)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactPhone = contactPhone;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getContactPhone()
    {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone)
    {
        this.contactPhone = contactPhone;
    }
    
    public String getContactName()
    {
        return firstName + " " + lastName;
    }
}
