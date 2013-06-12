/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marbl.parafiksit;

import com.marbl.messaging.requestreply.AsynchronousReplier;
import com.marbl.messaging.requestreply.IRequestListener;
import java.util.logging.Level;
import javax.jms.Message;

/**
 *
 * @author Bas
 */
abstract class ComputerBrokerGateway {

    private ParafiksitSerializer serializer;
    private AsynchronousReplier<ParafiksitRequest, ParafiksitReply> gateway;

    public ComputerBrokerGateway(String factoryName, String parafiksitRequestQueue, String parafiksitReplyQueue) {
        serializer = new ParafiksitSerializer();

        try {
            gateway = new AsynchronousReplier<ParafiksitRequest, ParafiksitReply>(factoryName, parafiksitRequestQueue, serializer) {
                @Override
                public void beforeSendReply(Message request, Message reply) {
                    throw new UnsupportedOperationException("Not supported yet.");
                }
            };
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(com.marbl.parafiksit.ComputerBrokerGateway.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        gateway.setRequestListener(new IRequestListener<ParafiksitRequest>(){

            public void receivedRequest(ParafiksitRequest request) {
                receivedParafiksitRequest(request);
            }        
        });
    }
    
    abstract void receivedParafiksitRequest(ParafiksitRequest request);
    
    public void start(){
        gateway.start();
    }
    
    void sendReply(ParafiksitRequest request, ParafiksitReply reply){
        gateway.sendReply(request, reply);
    }
}
