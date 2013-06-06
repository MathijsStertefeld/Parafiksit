/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marbl.computerbroker;

import com.marbl.messaging.requestreply.AsynchronousRequestor;
import com.marbl.messaging.requestreply.IReplyListener;
import com.marbl.parafiksit.ParafiksitReply;
import com.marbl.parafiksit.ParafiksitRequest;
import com.marbl.parafiksit.ParafiksitSerializer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bas
 */
abstract class ParafiksitGateway {
    private ParafiksitSerializer serializer;
    private AsynchronousRequestor<ParafiksitRequest, ParafiksitReply> gateway;
    
    public ParafiksitGateway(String factoryName, String parafiksitRequestQueue, String parafiksitReplyQueue)
    {
        serializer = new ParafiksitSerializer();
        try {
            gateway = new AsynchronousRequestor<ParafiksitRequest, ParafiksitReply>(factoryName, parafiksitRequestQueue, parafiksitReplyQueue, serializer);
        } catch (Exception ex) {
            Logger.getLogger(ParafiksitGateway.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void start(){
        gateway.start();
    }
    
    public void registerOrder(ParafiksitRequest request, IReplyListener<ParafiksitRequest, ParafiksitReply> listener){
        gateway.sendRequest(request, listener);
    }
}
