/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marbl.computerbroker;

import com.marbl.messaging.IReceiver;
import com.marbl.messaging.requestreply.AsynchronousRequestor;
import com.marbl.messaging.requestreply.IReplyListener;
import com.marbl.warehouse.WarehouseReply;
import com.marbl.warehouse.WarehouseRequest;
import com.marbl.warehouse.WarehouseSerializer;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bas
 *///Bankgateway = voorbeeld hiervoor!
public class WarehouseOrderGateway {
    
    private WarehouseSerializer serializer;
    private AsynchronousRequestor<WarehouseRequest, WarehouseReply> gateway;
    
    public WarehouseOrderGateway(String factoryName, String warehouseRequestQueue, String warehouseReplyQueue)
    {
        serializer = new WarehouseSerializer();
        try {
            gateway = new AsynchronousRequestor<WarehouseRequest, WarehouseReply>(factoryName, warehouseRequestQueue, warehouseReplyQueue, serializer);
        } catch (Exception ex) {
            Logger.getLogger(WarehouseOrderGateway.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void start(){
        gateway.start();
    }
    
    public void orderParts(WarehouseRequest request, IReplyListener<WarehouseRequest, WarehouseReply> listener){
        System.out.println("Sending request from broker to warehouse");
        gateway.sendRequest(request, listener);
    }
    
}
