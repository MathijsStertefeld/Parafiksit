/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marbl.parafiksit;

/**
 *
 * @author Bas
 */
public class ParafiksitTest {
    
    private ComputerBrokerOrderGateway orderGateway;
    
    public ParafiksitTest(String factoryName, String requestQueue, String replyQueue){
        orderGateway = new ComputerBrokerOrderGateway(factoryName, requestQueue, replyQueue) {

            @Override
            void receivedParafiksitRequest(ParafiksitOrderRequest request) {
                orderGateway.sendReply(request, new ParafiksitOrderReply(request.getString()));
            }
        };
    }
    
    public void start(){
        orderGateway.start();
    }
}
