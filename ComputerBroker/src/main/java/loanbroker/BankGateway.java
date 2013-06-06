/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package loanbroker;

import bank.BankQuoteReply;
import bank.BankQuoteRequest;
import bank.BankSerializer;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import loanbroker.bank.BankQuoteAggregate;
import loanbroker.bank.BanksSenderRouter;
import com.marbl.messaging.IReceiver;
import com.marbl.messaging.ISender;
import com.marbl.messaging.MessagingGateway;
import com.marbl.messaging.Receiver;
import com.marbl.messaging.requestreply.AsynchronousRequestor;
import com.marbl.messaging.requestreply.IReplyListener;

/**
 *
 * @author Bas
 */
public class BankGateway {

    private static final String AGGREGATION_CORRELATION = "aggregation";
    private BankSerializer serializer;
    private int aggregateCounter = 0;
    private BanksSenderRouter sender;
    private IReceiver receiver;
    //private MessagingGateway gateway;
    //private AsynchronousRequestor<BankQuoteRequest, BankQuoteReply> gateway;
    private Hashtable<Integer, BankQuoteAggregate> replyAggregate;

    public BankGateway(String factoryName, String receiveDestination) {
        try {
            sender = new BanksSenderRouter();
            receiver = new Receiver(factoryName, receiveDestination);
            receiver.setMessageListener(new MessageListener() {
                public void onMessage(Message msg) {
                    messageReceived((TextMessage) msg);
                }
            });
            serializer = new BankSerializer();
            replyAggregate = new Hashtable<Integer, BankQuoteAggregate>();
        } catch (Exception ex) {
            Logger.getLogger(BankGateway.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void addBank(String factoryName, String destination, String expression){
        sender.addBank(factoryName, destination, expression);
    }

    public void start() throws JMSException {
        receiver.openConnection();
        sender.openConnection();
    }

    /**
     * @todo Implement the following method: 1. get the AGGREGATION_CORRELATION
     * of the received message 2. get the BankQuoteAggregate that is registered
     * for this AGGREGATION_CORRELATION 3. de-serialize the message into a
     * BankQuoteReply 4. add the BankQuoteReply to the BankQuoteAggregate 5. if
     * this is the last expected reply, 5.a. notify the BankQuoteAggregate
     * listener and u 5.b. unregister the BankQuoteAggregate
     * @param msg the message that has just been received
     */
    private synchronized void messageReceived(TextMessage msg) {
        try {
            int agrcor = msg.getIntProperty(AGGREGATION_CORRELATION);
            BankQuoteAggregate agr = replyAggregate.get(agrcor);
            BankQuoteReply reply = serializer.replyFromString(msg.getText());
            if(agr.addReply(reply))
            {
                agr.notifyListener();
                replyAggregate.remove(agrcor);
            }
        } catch (JMSException ex) {
            Logger.getLogger(BankGateway.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @todo Implement the following method:
     * 1. serialize the request into a string
     * 2. get all eligible banks (ISender) for this request and count them
     * 3. for each eligible bank:
     *    3a. create a new message  for the request
     *    3b. set the JMSReplyTo to the receiver's destination
     *    3c. set the AGGREGATION_CORRELATION to the current aggregateCounter
     *    3d. let the bank send the message
     * 4. if the message was sent to at least one bank: 
     *    4a. create and register a new BankQuoteAggregate for the current value of the aggregateCounter
     *    4b. increase the aggregateCounter
     * 5. if there was no eligible banks (no message was sent), create a
     *     new BankQuoteReply(0, "There are no eligible banks for this loan.", 10)
     *     and notify the listener about its 'arrival'.
     * @param request
     * @param replyListener
     */
    public synchronized void sendRequest(BankQuoteRequest request, IReplyListener<BankQuoteRequest, BankQuoteReply> listener) {
        String req = serializer.requestToString(request);
        Iterable<ISender> eligibleBanks = sender.getEligibleBanks(request);
        
        int bankCounter = 0;
        Iterator it = eligibleBanks.iterator();
        while(it.hasNext())
        {
            Object next = it.next();
            bankCounter++;
        }
        
        for(ISender is : eligibleBanks)
        {
            try {
                TextMessage msg = is.createMessage(req);
                msg.setJMSReplyTo(receiver.getDestination());    
                msg.setIntProperty(AGGREGATION_CORRELATION, aggregateCounter);
                is.sendMessage(msg);
            } catch (JMSException ex) {
                Logger.getLogger(BankGateway.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if(bankCounter > 0)
        {
            BankQuoteAggregate bankQuoteAggregate = new BankQuoteAggregate(request, bankCounter, listener);
            replyAggregate.put(aggregateCounter, bankQuoteAggregate);
            aggregateCounter++;
        }
        else
        {
            BankQuoteReply bankQuoteReply = new BankQuoteReply(0, "No eligable bank found.", 10);
            listener.onReply(request, bankQuoteReply);
        }    
    }
    
//    public void getBankQuote(BankQuoteRequest request, IReplyListener<BankQuoteRequest, BankQuoteReply> listener) {
//        //gateway.sendMessage(gateway.createMessage(serializer.requestToString(request)));
//        System.out.println("loanbroker.BankGateway - trying to sendrequest");
//        gateway.sendRequest(request, listener);
//    }
}
