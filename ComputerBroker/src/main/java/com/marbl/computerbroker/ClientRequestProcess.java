/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marbl.computerbroker;

import com.marbl.client.ClientReply;
import com.marbl.client.ClientRequest;
import com.marbl.messaging.requestreply.IReplyListener;
import com.marbl.parafiksit.ParafiksitReply;
import com.marbl.parafiksit.ParafiksitRequest;
import com.marbl.warehouse.WarehouseReply;
import com.marbl.warehouse.WarehouseRequest;

/**
 *
 * @author Bas
 */
abstract class ClientRequestProcess {

    private ClientRequest clientRequest = null;
    private WarehouseRequest warehouseRequest = null;
    private WarehouseReply warehouseReply = null;
    private ParafiksitRequest parafiksitRequest = null;
    private ParafiksitReply parafiksitReply = null;
    private ClientReply clientReply = null;
    
    private ClientGateway clientGateway;
    private WarehouseGateway warehouseGateway;
    private ParafiksitGateway parafiksitGateway;
    
    private IReplyListener<WarehouseRequest, WarehouseReply> warehouseReplyListener;
    private IReplyListener<ParafiksitRequest, ParafiksitReply> parafiksitReplyListener;

    public ClientRequestProcess(ClientRequest clientRequest, ClientGateway clientGateway, WarehouseGateway warehouseGateway, ParafiksitGateway parafiksitGateway) {
        this.clientRequest = clientRequest;
        this.clientGateway = clientGateway;
        this.warehouseGateway = warehouseGateway;
        this.parafiksitGateway = parafiksitGateway;

        this.warehouseReplyListener = new IReplyListener<WarehouseRequest, WarehouseReply>() {
            public void onReply(WarehouseRequest request, WarehouseReply reply) {
                onWarehouseReply(reply);
            }
        };

        this.parafiksitReplyListener = new IReplyListener<ParafiksitRequest, ParafiksitReply>() {
            public void onReply(ParafiksitRequest request, ParafiksitReply reply) {
                onParafiksitReply(reply);
            }
        };

        //na clientprocess aanmaken, het clientrequest doorsturen naar warehouse
        requestWarehouse();
    }

    private void requestWarehouse() {
        WarehouseRequest whRequest = createWarehouseRequest(clientRequest);
        warehouseGateway.orderParts(whRequest, warehouseReplyListener);
    }

    private WarehouseRequest createWarehouseRequest(ClientRequest cRequest) {
        return new WarehouseRequest(); //geef parameters mee uit cRequest
    }

    private void onWarehouseReply(WarehouseReply reply) {
        warehouseReply = reply;
        notifyReceivedWarehouseReply(clientRequest, reply);
        
        //Doorgaan naar parafiksit
        ParafiksitRequest pfRequest = createParafiksitRequest(clientRequest, warehouseReply);
        //registerOrder = sendRequest(...)
        parafiksitGateway.registerOrder(pfRequest, parafiksitReplyListener);
    }
    
    abstract void notifyReceivedWarehouseReply(ClientRequest clientRequest, WarehouseReply warehouseReply);

    private ParafiksitRequest createParafiksitRequest(ClientRequest cRequest, WarehouseReply wReply){
        return new ParafiksitRequest(); //Parameters halen uit ClientRequest / WarehouseReply
    }
    
    private void onParafiksitReply(ParafiksitReply reply) {
        notifyReceivedParafiksitReply(clientRequest, reply);
        ClientReply cReply = createClientReply(clientRequest, reply);
        //sendInvoice = sendReply
        clientGateway.sendInvoice(clientRequest, cReply);
        notifySentClientReply(this);
    }
    
    abstract void notifyReceivedParafiksitReply(ClientRequest clientRequest, ParafiksitReply reply);

    private ClientReply createClientReply(ClientRequest cRequest, ParafiksitReply pReply){
        return new ClientReply(); //Parameters halen uit request en reply
    }
    
    abstract void notifySentClientReply(ClientRequestProcess process);
}
