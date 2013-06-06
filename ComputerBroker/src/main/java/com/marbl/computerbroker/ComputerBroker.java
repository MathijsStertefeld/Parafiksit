/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marbl.computerbroker;

import com.marbl.client.ClientRequest;
import com.marbl.parafiksit.ParafiksitReply;
import com.marbl.warehouse.WarehouseReply;
import java.util.ArrayList;

/**
 *
 * @author Bas
 */
public class ComputerBroker {
    
    private ClientGateway clientGateway;
    private ParafiksitGateway parafiksitGateway;
    private WarehouseGateway warehouseGateway;
    
    private ArrayList<ClientRequestProcess> activeClientProcesses;
    
    public ComputerBroker(String factoryName, String clientRequestQueue, String parafiksitRequestQueue, String parafiksitReplyQueue, String warehouseReplyQueue)
    {
        super();
        
        activeClientProcesses = new ArrayList<ClientRequestProcess>();
        
        clientGateway = new ClientGateway(factoryName, clientRequestQueue) {

            @Override
            void onClientRequest(ClientRequest request) {
                ComputerBroker.this.onClientRequest(request);
            }
        };
        
        parafiksitGateway = new ParafiksitGateway(factoryName, parafiksitRequestQueue, parafiksitReplyQueue) {};
    
        warehouseGateway = new WarehouseGateway(factoryName, warehouseReplyQueue);
    }
    
    private void onClientRequest(ClientRequest request)
    {
        final ClientRequestProcesss p = new ClientRequestProcess(request, parafiksitGateway, clientGateway, warehouseGateway){
            
            @Override
            void notifySendClientReply(ClientRequestProcess process){
                activeClientProcesses.remove(process);
            }
            
            @Override
            void notifyReceivedParafiksitReply(ClientRequest clientRequest, ParafiksitReply reply){
                //???
            }
            
            @Override
            void notifyReceivedWarehouseReply(ClientRequest clientRequest, WarehouseReply reply){
                //???
            }
        };
        activeClientProcesses.add(p);
        
    }
    
    public void start(){
        clientGateway.start();
        parafiksitGateway.start();
        warehouseGateway.start();
    }
    
//    public void addWarehouse(String factoryName, String destination, String expression)
//    {
//        warehouseGateway.addWarehouse(factoryName, destination, expression);
//    }
    
}
