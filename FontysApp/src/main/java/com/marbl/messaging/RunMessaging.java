package com.marbl.messaging;

import com.marbl.client.ClientOrderRequest;
import com.marbl.client.ClientTest;
import com.marbl.fontysapp.FontysAppFrame;

/**
 * This application tests the LoanBroker system.
 *
 */
public class RunMessaging
{

    public static void main(String[] args)
    {
        try
        {
            // read the queue names from file "MESSAGING.ini"  
            JMSSettings queueNames = new JMSSettings("src/main/resources/MESSAGING_CHANNELS.ini");
            final String factoryName = queueNames.get(JMSSettings.CONNECTION);
            //CLIENTS & BROKER
            final String clientOrderRequestQueue = queueNames.get(JMSSettings.CLIENT_ORDER_REQUEST);
            final String clientOrderReplyQueue = queueNames.get(JMSSettings.CLIENT_ORDER_REPLY);
            final String clientOrder2ReplyQueue = queueNames.get(JMSSettings.CLIENT_ORDER_REPLY_2);
//            //PARAFIKSIT & BROKER
//            final String parafiksitOrderRequestQueue = queueNames.get(JMSSettings.PARAFIKSIT_ORDER_REQUEST); ///was credit
//            final String parafiksitOrderReplyQueue = queueNames.get(JMSSettings.PARAFIKSIT_ORDER_REPLY); //was credit
//            //WAREHOUSE & BROKER
//            final String warehouseOrderRequestQueue = queueNames.get(JMSSettings.WAREHOUSE_REQUEST);
//            final String warehouseOrderReplyQueue = queueNames.get(JMSSettings.WAREHOUSE_REPLY);
//
//            final String clientStatusRequestQueue = queueNames.get(JMSSettings.CLIENT_STATUS_REQUEST);
//            final String clientStatusReplyQueue = queueNames.get(JMSSettings.CLIENT_STATUS_REPLY);
//            final String clientStatus2ReplyQueue = queueNames.get(JMSSettings.CLIENT_STATUS_REPLY_2);
//
//            final String parafiksitStatusRequestQueue = queueNames.get(JMSSettings.PARAFIKSIT_STATUS_REQUEST);
//            final String parafiksitStatusReplyQueue = queueNames.get(JMSSettings.PARAFIKSIT_STATUS_REPLY);

            FontysAppFrame frame = new FontysAppFrame(factoryName, clientOrderRequestQueue, clientOrderReplyQueue);
            frame.setVisible(true);
           
            //ClientTest client = new ClientTest("basClient", factoryName, clientOrderRequestQueue, clientOrderReplyQueue);
            //client.start();




        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public static void sendRequest()
    {
    }
}
