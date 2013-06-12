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
    
    public ComputerBroker(String factoryName, String clientRequestQueue, String parafiksitRequestQueue, String parafiksitReplyQueue, String warehouseRequestQueue, String warehouseReplyQueue)
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
    
        warehouseGateway = new WarehouseGateway(factoryName, warehouseRequestQueue, warehouseReplyQueue);
    }
    
    private void onClientRequest(ClientRequest request)
    {
        final ClientRequestProcess p = new ClientRequestProcess(request, clientGateway, warehouseGateway, parafiksitGateway){
            
            
            @Override
            void notifyReceivedParafiksitReply(ClientRequest clientRequest, ParafiksitReply reply){
                throw new UnsupportedOperationException("Not supported yet.");
            }
            
            @Override
            void notifyReceivedWarehouseReply(ClientRequest clientRequest, WarehouseReply reply){
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            void notifySentClientReply(ClientRequestProcess process) {
                activeClientProcesses.remove(process);
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
