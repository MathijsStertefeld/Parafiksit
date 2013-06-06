/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marbl.messaging;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.naming.NamingException;

/**
 *
 * @author Bas
 */
public class Receiver extends Channel implements IReceiver{
    
    private MessageConsumer consumer;    
    
    public Receiver(String connectionName, String destinationName) throws NamingException, JMSException
    {        
        super(connectionName, destinationName); 
        consumer = session.createConsumer(destination);        
    }    

    public void setMessageListener(MessageListener listener) {
        try {
            consumer.setMessageListener(listener);
        } catch (JMSException ex) {
            Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
}
