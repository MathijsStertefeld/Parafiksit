package messaging;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.naming.NamingException;



/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Bas
 */
public class MessagingGateway {
    
    private ISender sender;
    private IReceiver receiver;
    private String senderName;
    private String receiverName;
    
    public MessagingGateway(String factoryName, String senderName, String receiverName)
    {
        try {            
            receiver = new Receiver(factoryName, receiverName);
            sender = new Sender(factoryName, senderName);
            this.senderName = senderName;
            this.receiverName = receiverName;
        } catch (NamingException ex) {
            Logger.getLogger(MessagingGateway.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JMSException ex) {
            Logger.getLogger(MessagingGateway.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public MessagingGateway(String factoryName, String receiverName)
    {
        try {
            receiver = new Receiver(factoryName, receiverName);
            sender = new Sender(factoryName, null);
            System.out.println("MessagingGateway constructer met 2 argumenten aangeroepen. Check of dit klopt!");
            this.senderName = "geen zender!";
            this.receiverName = receiverName;
        } catch (NamingException ex) {
            Logger.getLogger(MessagingGateway.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JMSException ex) {
            Logger.getLogger(MessagingGateway.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean sendMessage(Message message)
    {
        try {
            sender.sendMessage(message);
            System.out.println("      Sender + receiver: " + senderName + " - " + receiverName);
            return true;
        } catch (JMSException ex) {
            Logger.getLogger(MessagingGateway.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public boolean sendMessage(Destination destination, Message message)
    {
        try {
            sender.sendMessage(message, destination);
             System.out.println("      (Destination)Sender + receiver: " + senderName + " - " + receiverName);
            return true;
        } catch (JMSException ex) {
            Logger.getLogger(MessagingGateway.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public TextMessage createMessage(String body)
    {
        try {
            return sender.createMessage(body);
        } catch (JMSException ex) {
            Logger.getLogger(MessagingGateway.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public void setReceivedMessageListener(MessageListener listener)
    {
        receiver.setMessageListener(listener);
    }
    
    public void openConnection()
    {
        try {
            receiver.openConnection();
            if(sender != null)
                sender.openConnection();
        } catch (JMSException ex) {
            Logger.getLogger(MessagingGateway.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Destination getReceiverDestination()
    {
        return receiver.getDestination();
    }
    
    public Destination getSenderDestination()
    {
        return sender.getDestination();
    }
    
}