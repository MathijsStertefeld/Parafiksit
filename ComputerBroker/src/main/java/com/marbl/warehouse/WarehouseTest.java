/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marbl.warehouse;

/**
 *
 * @author Bas
 */
public class WarehouseTest {
    private ComputerBrokerGateway gateway;
    
    public WarehouseTest(String factoryName, String requestQueue, String replyQueue){
        gateway = new ComputerBrokerGateway(factoryName, requestQueue, replyQueue) {

            @Override
            void receivedWarehouseRequest(WarehouseRequest request) {
                gateway.sendReply(request, new WarehouseReply(request.getString()));
            }
        };
    }
    
    public void start(){
        gateway.start();
    }
}
