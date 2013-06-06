/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package creditbureau;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Message;
import messaging.MessagingGateway;
import messaging.requestreply.AsynchronousReplier;
import messaging.requestreply.IRequestListener;

/**
 *
 * @author Bas
 */
abstract class LoanBrokerGateway {
    
    private CreditSerializer serializer;
    //private MessagingGateway gateway;
    private AsynchronousReplier<CreditRequest, CreditReply> gateway;
    
    public LoanBrokerGateway(String factoryName, String creditRequestQueue, String creditReplyQueue) {
        serializer = new CreditSerializer();
        try {
            //gateway = new AsynchronousReplier<CreditRequest, CreditReply>(factoryName, creditReplyQueue, serializer);
            gateway = new AsynchronousReplier<CreditRequest, CreditReply>(factoryName, creditRequestQueue, serializer) {

                @Override
                public void beforeSendReply(Message request, Message reply) {
                    //do nothing here
                }
            };
        } catch (Exception ex) {
            Logger.getLogger(LoanBrokerGateway.class.getName()).log(Level.SEVERE, null, ex);
        }            
        
        gateway.setRequestListener(new IRequestListener<CreditRequest>() {

            public void receivedRequest(CreditRequest request) {
                receivedCreditRequest(request);
            }
        });
        
    //        gateway = new MessagingGateway(factoryName, creditReplyQueue, creditRequestQueue);
    //        gateway.setReceivedMessageListener(
    //            new MessageListener() {
    //                public void onMessage(Message message) {
    //                    try {
    //                        System.out.println("creditbureaumessage");
    //                        CreditRequest request = serializer.requestFromString(((TextMessage) message).getText());
    //                        receivedCreditRequest(request);
    //                    } catch (JMSException ex) {
    //                        Logger.getLogger(LoanBrokerGateway.class.getName()).log(Level.SEVERE, null, ex);
    //                    }
    //                }
    //            });
    }

    abstract void receivedCreditRequest(CreditRequest request);

    public void start() {
        //gateway.openConnection();
        gateway.start();
    }

    void sendCreditHistory(CreditRequest request, CreditReply reply) { 
        //gateway.sendMessage(gateway.createMessage(serializer.replyToString(reply)));
        gateway.sendReply(request, reply);
    }
}
