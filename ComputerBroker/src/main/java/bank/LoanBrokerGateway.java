/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bank;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.jms.Message;
import messaging.MessagingGateway;
import messaging.requestreply.AsynchronousReplier;
import messaging.requestreply.IRequestListener;

/**
 *
 * @author Bas
 */
abstract class LoanBrokerGateway {
    
    private BankSerializer serializer;
    //private MessagingGateway gateway;
    private AsynchronousReplier<BankQuoteRequest, BankQuoteReply> gateway;
    
    public LoanBrokerGateway(String factoryName, String bankRequestQueue, String bankReplyQueue)
    {
        serializer = new BankSerializer();
        try {
            gateway = new AsynchronousReplier<BankQuoteRequest, BankQuoteReply>(factoryName, bankRequestQueue, serializer) {

                @Override
                public void beforeSendReply(Message request, Message reply) {
                    
                    try {
                        String AGGREGATION_CORRELATION = "aggregation";
                        int agrcor = request.getIntProperty(AGGREGATION_CORRELATION);
                        reply.setIntProperty(AGGREGATION_CORRELATION, agrcor);
                    } catch (JMSException ex) {
                        Logger.getLogger(LoanBrokerGateway.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };
        } catch (Exception ex) {
            Logger.getLogger(LoanBrokerGateway.class.getName()).log(Level.SEVERE, null, ex);
        }    
        
        gateway.setRequestListener(new IRequestListener<BankQuoteRequest>() {

            public void receivedRequest(BankQuoteRequest request) {
                System.out.println("Bank received request from loanbroker");
                receivedQuoteRequest(request);
            }
        });        
    }
    
    abstract void receivedQuoteRequest(BankQuoteRequest request);
    
    void start()
    {
        //gateway.openConnection();
        gateway.start();
    }
    
    //public boolean sendBankReply(BankQuoteReply reply)
    //{
        //gateway.sendMessage(gateway.createMessage(serializer.replyToString(reply)));
        //return true;
    //}
    
    public void sendQuoteOffer(BankQuoteRequest request, BankQuoteReply reply)
    {
        gateway.sendReply(request, reply);
    }
}
