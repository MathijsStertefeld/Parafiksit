/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marbl.client;

import com.marbl.messaging.requestreply.AsynchronousRequestor;
import com.marbl.messaging.requestreply.IReplyListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bas
 */
abstract class ComputerBrokerGateway {
    
    private ClientSerializer serializer;
    private AsynchronousRequestor<ClientRequest, ClientReply> gateway;
    
    public ComputerBrokerGateway(String factoryName, String requestQueue, String replyQueue){
        serializer = new ClientSerializer();
        
        try{
            gateway = new AsynchronousRequestor<ClientRequest, ClientReply>(factoryName, requestQueue, replyQueue, serializer);
        } catch (Exception ex) {
            Logger.getLogger(ComputerBrokerGateway.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
    public void order(ClientRequest request){
        gateway.sendRequest(request, new IReplyListener<ClientRequest, ClientReply>(){

            public void onReply(ClientRequest request, ClientReply reply) {
                onOrderReplyArrived(request, reply);
            }
        });
    }
    
    void start(){
        gateway.start();
    }
    
                
    abstract void onOrderReplyArrived(ClientRequest request, ClientReply reply);
}
