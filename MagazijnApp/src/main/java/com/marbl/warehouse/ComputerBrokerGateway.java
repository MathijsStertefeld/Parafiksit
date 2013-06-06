/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marbl.warehouse;

import java.util.logging.Level;
import javax.jms.Message;
import messaging.requestreply.AsynchronousReplier;
import messaging.requestreply.IRequestListener;

/**
 *
 * @author Bas
 */
abstract class ComputerBrokerGateway {

    private WarehouseSerializer serializer;
    private AsynchronousReplier<WarehouseRequest, WarehouseReply> gateway;

    public ComputerBrokerGateway(String factoryName, String warehouseRequestQueue, String warehouseReplyQueue) {
        serializer = new WarehouseSerializer();

        try {
            gateway = new AsynchronousReplier<WarehouseRequest, WarehouseReply>(factoryName, warehouseRequestQueue, serializer) {
                @Override
                public void beforeSendReply(Message request, Message reply) {
                    //???????
                }
            };
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ComputerBrokerGateway.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        gateway.setRequestListener(new IRequestListener<WarehouseRequest>() {

            public void receivedRequest(WarehouseRequest request) {
                receivedWarehouseRequest(request);
            }
        });
    }
    
    abstract void receivedWarehouseRequest(WarehouseRequest request);
    
    public void start()
    {
        gateway.start();
    }
    
    void sendReply(WarehouseRequest request, WarehouseReply reply)
    {
        gateway.sendReply(request, reply);
    }
    
}
