/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marbl.client;

/**
 *
 * @author Bas
 */
public class ClientTest {
    
    private String clientName;
    private ComputerBrokerOrderGateway orderGateway;
    
    public ClientTest(String clientName, String factoryName, String requestQueue, String replyQueue){
        orderGateway = new ComputerBrokerOrderGateway(factoryName, requestQueue, replyQueue) {

            @Override
            void onOrderReplyArrived(ClientOrderRequest request, ClientOrderReply reply) {
                System.out.println("Client got reply from broker");
                System.out.println("Resultaat: " + reply.getString());
            }
        };
    }
    
    public void start(){
        orderGateway.start();
    }
    
    public void sendOrderRequest(ClientOrderRequest request){
        System.out.println("Sending request from client");
        orderGateway.order(request);
    }
    
    public String getClientName(){
        return clientName;
    }
}
