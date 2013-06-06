/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import messaging.MessagingGateway;
import messaging.requestreply.AsynchronousRequestor;
import messaging.requestreply.IReplyListener;

/**
 *
 * @author Bas
 */
abstract class LoanBrokerGateway {

    //private MessagingGateway gateway;
    private ClientSerializer serializer;
    
    private AsynchronousRequestor<ClientRequest, ClientReply> gateway;

    public LoanBrokerGateway(String factoryName, String requestQueue, String replyQueue) {
        serializer = new ClientSerializer();
        try {
            gateway = new AsynchronousRequestor<ClientRequest, ClientReply>(factoryName, requestQueue, replyQueue, serializer);
        } catch (Exception ex) {
            Logger.getLogger(LoanBrokerGateway.class.getName()).log(Level.SEVERE, null, ex);
        }    
        
        
        
            //gateway = new MessagingGateway(factoryName, requestQueue, replyQueue);
            
    //        gateway.setReceivedMessageListener(
    //            new MessageListener() {
    //                public void onMessage(Message message) {
    //                    try {
    //                        System.out.println("clientmessage");
    //                        ClientReply reply = serializer.replyFromString(((TextMessage) message).getText());
    //                        loanOfferArrived(reply);
    //                    } catch (JMSException ex) {
    //                        Logger.getLogger(LoanBrokerGateway.class.getName()).log(Level.SEVERE, null, ex);
    //                    }
    //                }
    //            }
    //        );
    }

    void applyForLoan(ClientRequest request) {
         //gateway.sendMessage(gateway.createMessage(serializer.requestToString(request)));
        gateway.sendRequest(request, new IReplyListener<ClientRequest, ClientReply>() {

            public void onReply(ClientRequest request, ClientReply reply) {
                loanOfferArrived(request, reply);
            }
        });
    }

    void start() {
        //gateway.openConnection();
        gateway.start();
    }

    abstract void loanOfferArrived(ClientRequest request, ClientReply reply);
}
