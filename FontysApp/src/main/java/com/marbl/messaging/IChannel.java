/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marbl.messaging;

import javax.jms.Destination;
import javax.jms.JMSException;

/**
 *
 * @author Bas
 */
public interface IChannel {
    void openConnection() throws JMSException;
    
    Destination getDestination();
}
