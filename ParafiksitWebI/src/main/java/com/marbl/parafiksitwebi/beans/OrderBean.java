package com.marbl.parafiksitwebi.beans;

import com.marbl.client.ClientOrderRequest;
import com.marbl.messaging.JMSSettings;
import com.marbl.parafiksitwebi.messaging.ClientTest;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * @author Leslie Aerts
 */

public class OrderBean
{

    private ClientOrderRequest request;
    private ClientTest cTest;
    
    public void OrderBean()
    {
        JMSSettings queueNames = new JMSSettings("src/main/resources/MESSAGING_CHANNELS.ini");
        final String factoryName = queueNames.get(JMSSettings.CONNECTION);
        final String clientOrderRequestQueue = queueNames.get(JMSSettings.CLIENT_ORDER_REQUEST);
        final String clientOrder2ReplyQueue = queueNames.get(JMSSettings.CLIENT_ORDER_REPLY_2);
        
        cTest = new ClientTest("FontysApp", factoryName, clientOrderRequestQueue, clientOrder2ReplyQueue);
        cTest.start();
        System.out.println("Init done.");
    }

    public void order()
    {
        cTest.sendOrderRequest(request);
    }

    public ClientOrderRequest getRequest()
    {
        return request;
    }

    public void setRequest(ClientOrderRequest request)
    {
        this.request = request;
    }
}
