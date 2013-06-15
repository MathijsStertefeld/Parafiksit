/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marbl.computerbroker;

import com.marbl.client.ClientStatusReply;
import com.marbl.client.ClientStatusRequest;
import com.marbl.messaging.requestreply.IReplyListener;
import com.marbl.parafiksit.ParafiksitStatusReply;
import com.marbl.parafiksit.ParafiksitStatusRequest;

/**
 *
 * @author Bas
 */
abstract class StatusRequestProcess {

    private ClientStatusRequest clientRequest = null;
    private ParafiksitStatusRequest parafiksitRequest = null;
    private ParafiksitStatusReply parafiksitReply = null;
    private ClientStatusReply clientReply = null;
    private ClientStatusGateway clientGateway;
    private ParafiksitStatusGateway parafiksitGateway;
    private IReplyListener<ParafiksitStatusRequest, ParafiksitStatusReply> parafiksitReplyListener;

    public StatusRequestProcess(ClientStatusRequest clientRequest, ClientStatusGateway clientGateway, ParafiksitStatusGateway parafiksitGateway) {
        this.clientRequest = clientRequest;
        this.clientGateway = clientGateway;
        this.parafiksitGateway = parafiksitGateway;

        this.parafiksitReplyListener = new IReplyListener<ParafiksitStatusRequest, ParafiksitStatusReply>() {
            public void onReply(ParafiksitStatusRequest request, ParafiksitStatusReply reply) {
                onParafiksitReply(reply);
            }
        };
        
        sendStatusRequest();
    }
    
    private void sendStatusRequest(){
        ParafiksitStatusRequest pRequest = new ParafiksitStatusRequest();
        parafiksitGateway.getStatus(pRequest, parafiksitReplyListener);
    }

    private void onParafiksitReply(ParafiksitStatusReply reply) {
        notifyReceivedParafiksitReply(clientRequest, reply);
        
        ClientStatusReply cReply = new ClientStatusReply();
        clientGateway.sendReply(clientRequest, cReply);
        
        notifySentClientReply(this);
    }
    
    abstract void notifyReceivedParafiksitReply(ClientStatusRequest clientRequest, ParafiksitStatusReply reply);

    abstract void notifySentClientReply(StatusRequestProcess process);
}
