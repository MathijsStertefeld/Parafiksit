/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marbl.computerbroker;

import com.marbl.client.ClientReply;
import com.marbl.client.ClientRequest;
import com.marbl.client.ClientSerializer;
import com.marbl.messaging.requestreply.AsynchronousReplier;
import com.marbl.messaging.requestreply.IRequestListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Message;

/**
 *
 * @author Bas
 */
abstract class ClientGateway {

    private ClientSerializer serializer;
    private AsynchronousReplier<ClientRequest, ClientReply> gateway;

    public ClientGateway(String factoryName, String clientRequestQueue) {
        serializer = new ClientSerializer();

        try {
            gateway = new AsynchronousReplier<ClientRequest, ClientReply>(factoryName, clientRequestQueue, serializer) {
                @Override
                public void beforeSendReply(Message request, Message reply) {
                    //????
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
    }
    
    abstract void onClientRequest(ClientRequest request);
    
    void start(){
        gateway.start();
    }
    
    void sendInvoice(ClientRequest request, ClientReply reply){
        gateway.sendReply(request, reply);
    }
}
