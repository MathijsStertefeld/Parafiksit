/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marbl.messaging;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Bas
 */
public class Channel implements IChannel {

    protected Connection connection;
    protected Session session;
    protected Destination destination;
    
    public Channel(String connectionName, String destinationName) throws NamingException, JMSException {
        Context jndiContext = new InitialContext();

        ConnectionFactory connectionFactory = (ConnectionFactory) jndiContext.lookup(connectionName);
        connection = connectionFactory.createConnection();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //if (destinationName.equals("") || 
        if(destinationName == null) {
            destination = null;
        } else {
            destination = (Destination) jndiContext.lookup(destinationName);
        }
    }

    public Destination getDestination() {
        return destination;
    }

    public void openConnection() throws JMSException {

        connection.start();
    }  
}
