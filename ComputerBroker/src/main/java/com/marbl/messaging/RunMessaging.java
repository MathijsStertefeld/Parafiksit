package com.marbl.messaging;


import com.marbl.client.ClientRequest;

/**
 * This application tests the LoanBroker system.
 * 
 */
public class RunMessaging {

    public static void main(String[] args) {
        try {
            // read the queue names from file "MESSAGING.ini"  
            JMSSettings queueNames = new JMSSettings("MESSAGING_CHANNELS.ini");
            final String factoryName = queueNames.get(JMSSettings.CONNECTION);
            //CLIENTS & BROKER
            final String clientRequestQueue = queueNames.get(JMSSettings.CLIENT_REQUEST);
            final String clientReplyQueue = queueNames.get(JMSSettings.CLIENT_REPLY);
            final String client2ReplyQueue = queueNames.get(JMSSettings.CLIENT_REPLY_2);
            //PARAFIKSIT & BROKER
            final String parafiksitRequestQueue = queueNames.get(JMSSettings.PARAFIKSIT_REQUEST); ///was credit
            final String parafiksitReplyQueue = queueNames.get(JMSSettings.PARAFIKSIT_REPLY); //was credit
            //WAREHOUSE & BROKER
            final String warehouseRequestQueue = queueNames.get(JMSSettings.WAREHOUSE_REQUEST);
            final String warehouseReplyQueue = queueNames.get(JMSSettings.WAREHOUSE_REPLY);
            
            //final String ingRequestQueue = queueNames.get(JMSSettings.BANK_1);
            //final String rabobankRequestQueue = queueNames.get(JMSSettings.BANK_2);
            //final String abnamroRequestQueue = queueNames.get(JMSSettings.BANK_3);
            //final String bankReplyQueue = queueNames.get(JMSSettings.BANK_REPLY);
            

                       
            // create a ComputerBroker middleware
            //LoanBroker broker = new LoanBroker(factoryName, clientRequestQueue, creditRequestQueue, creditReplyQueue, bankReplyQueue); 
            //broker.addBank(factoryName, ingRequestQueue, ING);
            //broker.addBank(factoryName, rabobankRequestQueue, RABO_BANK);
            //broker.addBank(factoryName, abnamroRequestQueue, ABN_AMRO);
            
            
            // create a Client Application
            //LoanTestClient hypotheeker = new LoanTestClient("The Hypotheker", factoryName, clientRequestQueue, clientReplyQueue);
            //LoanTestClient hypotheekvisie = new LoanTestClient("Hypotheekvisie", factoryName, clientRequestQueue, client2ReplyQueue);
            
            // create the CreditBureau Application
            //CreditBureau creditBureau = new CreditBureau(factoryName, creditRequestQueue, creditReplyQueue);

            // create one Bank application
            //Bank ing = new Bank("ING", factoryName, ingRequestQueue, bankReplyQueue, DEBUG_MODE);
            //Bank rabobank = new Bank("RaboBank", factoryName, rabobankRequestQueue, bankReplyQueue, DEBUG_MODE);
            //Bank abnAmro = new Bank("ABN Amro", factoryName, abnamroRequestQueue, bankReplyQueue, DEBUG_MODE);

            // open all connections in the broker, client and credit applications
            //broker.start();
            //creditBureau.start();
            //ing.start();
            //rabobank.start();
            //abnAmro.start();
            //hypotheeker.start();
            //hypotheekvisie.start(); 

            // send three requests
            //hypotheeker.sendRequest(new ClientRequest(1, 100000, 24));
            //hypotheeker.sendRequest(new ClientRequest(2, 88888, 5));
            //hypotheeker.sendRequest(new ClientRequest(3, 100, 5));

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
