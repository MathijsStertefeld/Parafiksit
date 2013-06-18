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
                onWarehouseReply(reply);
            }
        };

        this.parafiksitReplyListener = new IReplyListener<ParafiksitOrderRequest, ParafiksitOrderReply>() {
            public void onReply(ParafiksitOrderRequest request, ParafiksitOrderReply reply) {
                onParafiksitReply(reply);
            }
        };

        //check in database of parafiksit de ndonderdelen al heeft, zoja -> skip requestWarehouse

        //na clientprocess aanmaken, het clientrequest doorsturen naar parafiksit waar je kijkt of de onderdelen daar al zijn

        sendOrderToParafiksit();
    }
       
    private void sendOrderToParafiksit(){
        ParafiksitOrderRequest req = createParafiksitRequest(clientRequest);
        parafiksitGateway.registerOrder(req, parafiksitReplyListener);
    }

    private ParafiksitOrderRequest createParafiksitRequest(ClientOrderRequest cRequest){
        return new ParafiksitOrderRequest(cRequest.getString() + "B"); //Parameters halen uit ClientOrderRequest / WarehouseReply
    }

    private void onParafiksitReply(ParafiksitOrderReply pReply) {
        parafiksitReply = pReply;
        notifyReceivedParafiksitReply(clientRequest, pReply);
        
        //als parafiksit alle onderdelen had
        if(false){
            ClientOrderReply cReply = createClientReply(clientRequest, parafiksitReply);
            sendClientReply(cReply);
        }
        else{
            //Als parafiksit niet alle onderdelen heeft, order de missende
            orderPartsAtWarehouse(clientRequest, pReply);
        }
    }

    abstract void notifyReceivedParafiksitReply(ClientOrderRequest clientRequest, ParafiksitOrderReply reply);
    
    private void orderPartsAtWarehouse(ClientOrderRequest cRequest, ParafiksitOrderReply pReply) {
        WarehouseRequest whRequest = createWarehouseRequest(cRequest, pReply);
        warehouseGateway.orderParts(whRequest, warehouseReplyListener);
    }

    private WarehouseRequest createWarehouseRequest(ClientOrderRequest cRequest, ParafiksitOrderReply pReply) {
        //Check welke parts ordered moeten worden!
        return new WarehouseRequest(pReply + "C"); //geef parameters mee uit cRequest en pReply
    }

    private void onWarehouseReply(WarehouseReply whReply) {
        warehouseReply = whReply;
        notifyReceivedWarehouseReply(clientRequest, whReply);
        
        //create bill and send back?
        // of moeten we terug naar parafiksit om whatever te doen
        ClientOrderReply cReply = createClientReply(clientRequest, parafiksitReply, whReply);
        sendClientReply(cReply);
    }

    abstract void notifyReceivedWarehouseReply(ClientOrderRequest clientRequest, WarehouseReply warehouseReply);
        
    private void sendClientReply(ClientOrderReply cReply){
        clientGateway.sendInvoice(clientRequest, cReply);
        notifySentClientReply(this);
    }

    private ClientOrderReply createClientReply(ClientOrderRequest cRequest, ParafiksitOrderReply pReply, WarehouseReply whReply){
        //Hier parafiksit en warehouse (als niet null) replys gebruiken
        // om reply te maken
        
        return new ClientOrderReply(whReply + "D");
    }
    
    private ClientOrderReply createClientReply(ClientOrderRequest cRequest, ParafiksitOrderReply pReply) {
        
        return createClientReply(cRequest, pReply, null);
    }

    abstract void notifySentClientReply(OrderRequestProcess process);

 
}
