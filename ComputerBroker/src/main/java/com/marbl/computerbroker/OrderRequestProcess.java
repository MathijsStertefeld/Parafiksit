/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marbl.computerbroker;

import com.marbl.client.ClientOrderReply;
import com.marbl.client.ClientOrderRequest;
import com.marbl.messaging.requestreply.IReplyListener;
import com.marbl.parafiksit.ParafiksitOrderReply;
import com.marbl.parafiksit.ParafiksitOrderRequest;
import com.marbl.warehouse.WarehouseReply;
import com.marbl.warehouse.WarehouseRequest;

/**
 *
 * @author Bas
 */
abstract class OrderRequestProcess {

    private ClientOrderRequest clientRequest = null;
    private WarehouseRequest warehouseRequest = null;
    private WarehouseReply warehouseReply = null;
    private ParafiksitOrderRequest parafiksitRequest = null;
    private ParafiksitOrderReply parafiksitReply = null;
    private ClientOrderReply clientReply = null;
    private ClientOrderGateway clientGateway;
    private WarehouseOrderGateway warehouseGateway;
    private ParafiksitOrderGateway parafiksitGateway;
    private IReplyListener<WarehouseRequest, WarehouseReply> warehouseReplyListener;
    private IReplyListener<ParafiksitOrderRequest, ParafiksitOrderReply> parafiksitReplyListener;

    public OrderRequestProcess(ClientOrderRequest clientRequest, ClientOrderGateway clientGateway, WarehouseOrderGateway warehouseGateway, ParafiksitOrderGateway parafiksitGateway) {
        this.clientRequest = clientRequest;
        this.clientGateway = clientGateway;
        this.warehouseGateway = warehouseGateway;
        this.parafiksitGateway = parafiksitGateway;

        this.warehouseReplyListener = new IReplyListener<WarehouseRequest, WarehouseReply>() {
            public void onReply(WarehouseRequest request, WarehouseReply reply) {
                System.out.println("OrderRequestProcess got reply from warehouse");
                onWarehouseReply(reply);
            }
        };

        this.parafiksitReplyListener = new IReplyListener<ParafiksitOrderRequest, ParafiksitOrderReply>() {
            public void onReply(ParafiksitOrderRequest request, ParafiksitOrderReply reply) {
                System.out.println("OrderRequestProcess got reply from parafiksit");
                onParafiksitReply(reply);
            }
        };

        //CHECK OF ER ONDERDELEN ZITTEN IN DE ORDER!!!!!!
        //sendOrderToParafiksit();
        CheckIfOrderNeedsParts(clientRequest);
    }
    
    private void CheckIfOrderNeedsParts(ClientOrderRequest cRequest){
        //zoja: stuur naar warehouse (-> parafiksit -> client)
        //zonee: stuur naar parafiksit (-> client)
        
        if(cRequest.containsParts()){
            orderPartsAtWarehouse(cRequest);
        }
        else{
            sendOrderToParafiksit(cRequest, null);
        }
    }
    
    /*
     * Warehouse methods
     */
    private void orderPartsAtWarehouse(ClientOrderRequest cRequest) {
        WarehouseRequest whRequest = createWarehouseRequest(cRequest);
        warehouseGateway.orderParts(whRequest, warehouseReplyListener);
    }
    
    private WarehouseRequest createWarehouseRequest(ClientOrderRequest cRequest) {
        //Check welke parts ordered moeten worden!
        return new WarehouseRequest(cRequest.getString()); //geef parameters mee uit cRequest (onderdelen)
    }
    
    private void onWarehouseReply(WarehouseReply whReply) {
        warehouseReply = whReply;
        notifyReceivedWarehouseReply(clientRequest, whReply);
        
        sendOrderToParafiksit(clientRequest, whReply);
    }

    abstract void notifyReceivedWarehouseReply(ClientOrderRequest clientRequest, WarehouseReply warehouseReply);
       
    /*
     * Parafiksit
     */
    private void sendOrderToParafiksit(ClientOrderRequest cRequest, WarehouseReply whReply){
        ParafiksitOrderRequest req = createParafiksitRequest(cRequest, whReply);
        parafiksitGateway.registerOrder(req, parafiksitReplyListener);
    }

    private ParafiksitOrderRequest createParafiksitRequest(ClientOrderRequest cRequest, WarehouseReply whReply){
        System.out.println("cRequest.getstring: " + cRequest.getString());
        
        ParafiksitOrderRequest paraRequest;
        if(whReply != null){
            paraRequest = new ParafiksitOrderRequest(cRequest.getString());
        }
        else{
            paraRequest = new ParafiksitOrderRequest(cRequest.getString());
        }
        
        return paraRequest;
    }

    private void onParafiksitReply(ParafiksitOrderReply pReply) {
        parafiksitReply = pReply;
        notifyReceivedParafiksitReply(clientRequest, pReply);
        

        ClientOrderReply cReply = createClientReply(clientRequest, parafiksitReply);
        sendClientReply(cReply);
    }

    abstract void notifyReceivedParafiksitReply(ClientOrderRequest clientRequest, ParafiksitOrderReply reply);
    
    /*
     * ClientReply
     */   
    private void sendClientReply(ClientOrderReply cReply){
        clientGateway.sendInvoice(clientRequest, cReply);
        notifySentClientReply(this);
    }
    
    private ClientOrderReply createClientReply(ClientOrderRequest cRequest, ParafiksitOrderReply pReply) {
        
        return new ClientOrderReply(pReply.getString());
    }

    abstract void notifySentClientReply(OrderRequestProcess process); 
}
