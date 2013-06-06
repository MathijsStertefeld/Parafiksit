/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package loanbroker;

import client.ClientReply;
import client.ClientRequest;
import client.ClientSerializer;
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
abstract class ClientGateway {

    private ClientSerializer serializer;
    //private MessagingGateway gateway;
    private AsynchronousReplier<ClientRequest, ClientReply> gateway;

    //public ClientGateway(String factoryName, String clientRequestQueue, String clientReplyQueue) {
    public ClientGateway(String factoryName, String clientRequestQueue){
        serializer = new ClientSerializer();
        try {
            gateway = new AsynchronousReplier<ClientRequest, ClientReply>(factoryName, clientRequestQueue, serializer) {

                @Override
                public void beforeSendReply(Message request, Message reply) {
                    //do nothing here
                }
            };
         } catch (Exception ex) {
            Logger.getLogger(ClientGateway.class.getName()).log(Level.SEVERE, null, ex);
        }    
        
        gateway.setRequestListener(new IRequestListener<ClientRequest>() {

            public void receivedRequest(ClientRequest request) {
                onClientRequest(request);
            }
        });
        
    //        gateway = new MessagingGateway(factoryName, clientReplyQueue, clientRequestQueue);
    //        gateway.setReceivedMessageListener(
    //                new MessageListener() {
    //                    public void onMessage(Message message) {
    //                        try {
    //                            System.out.println("loanbroker.clientmessage");
    //                            ClientRequest request = serializer.requestFromString(((TextMessage) message).getText());
    //                            onClientRequest(request);
    //                        } catch (JMSException ex) {
    //                            Logger.getLogger(ClientGateway.class.getName()).log(Level.SEVERE, null, ex);
    //                        }
    //
    //                    }
    //                });
       
    }

    abstract void onClientRequest(ClientRequest request);

    void start() {
        //gateway.openConnection();
        gateway.start();
    }

    void offerLoan(ClientRequest request, ClientReply reply) {
        //gateway.sendMessage(gateway.createMessage(serializer.replyToString(reply)));
        System.out.println("loanbroker.ClientGateway - trying to sendReply");
        gateway.sendReply(request, reply);
    }
}
