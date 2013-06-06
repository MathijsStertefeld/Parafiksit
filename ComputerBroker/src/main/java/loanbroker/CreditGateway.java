/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package loanbroker;

import creditbureau.CreditReply;
import creditbureau.CreditRequest;
import creditbureau.CreditSerializer;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.marbl.messaging.MessagingGateway;
import com.marbl.messaging.requestreply.AsynchronousRequestor;
import com.marbl.messaging.requestreply.IReplyListener;

/**
 *
 * @author Bas
 */
abstract class CreditGateway {

    private CreditSerializer serializer;
    //private MessagingGateway gateway;
    private AsynchronousRequestor<CreditRequest, CreditReply> gateway;

    public CreditGateway(String factoryName, String creditRequestQueue, String creditReplyQueue) {
        serializer = new CreditSerializer();
        try {
            gateway = new AsynchronousRequestor<CreditRequest, CreditReply>(factoryName, creditRequestQueue, creditReplyQueue, serializer);
        } catch (Exception ex) {
            Logger.getLogger(CreditGateway.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    //        gateway = new MessagingGateway(factoryName, creditRequestQueue, creditReplyQueue);
    //        gateway.setReceivedMessageListener(
    //                new MessageListener() {
    //                    public void onMessage(Message message) {
    //                        try {
    //                            System.out.println("loanbroker.creditmessage");
    //                            CreditReply reply = serializer.replyFromString(((TextMessage) message).getText());
    //                            onCreditReply(reply);
    //                        } catch (JMSException ex) {
    //                            Logger.getLogger(CreditGateway.class.getName()).log(Level.SEVERE, null, ex);
    //                        }
    //                    }
    //                });

    }

    public void start() {
        //gateway.openConnection();
        gateway.start();
    }

    //abstract void onCreditReply(CreditReply reply);

    public void getCreditHistory(CreditRequest request, IReplyListener<CreditRequest, CreditReply> listener) {
        //gateway.sendMessage(gateway.createMessage(serializer.requestToString(request)));
        System.out.println("loanbroker.CreditGateway - trying to sendrequest");
        gateway.sendRequest(request, listener);
    }
}
