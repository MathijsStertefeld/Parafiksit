/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package loanbroker;

import bank.BankQuoteReply;
import client.*;
import creditbureau.CreditReply;
import java.util.ArrayList;
import javax.jms.JMSException;
import loanbroker.gui.LoanBrokerFrame;

/**
 *
 * @author Maja Pesic
 */
public class LoanBroker {

    private ClientGateway clientGateway;
    private CreditGateway creditGateway;
    private BankGateway bankGateway;
    /**
     *  the collection of active clientRequests
     */
    private ArrayList<ClientRequestProcess> activeClientProcesses;
    private LoanBrokerFrame frame;

    /**
     * Intializes attributes, and registers itself (method onClinetRequest) as
     * the listener for new client requests
     * @param connectionName
     * @param clientRequestQueue
     * @param creditRequestQueue
     * @param creditReplyQueue
     * @param bankRequestQueue
     * @param bankReplyQueue
     */
    //public LoanBroker(String factoryName, String clientRequestQueue, String creditRequestQueue, String creditReplyQueue, String bankRequestQueue, String bankReplyQueue) throws Exception{
    public LoanBroker(String factoryName, String clientRequestQueue, String creditRequestQueue, String creditReplyQueue, String bankReplyQueue) throws Exception{    
        super();
        frame = new LoanBrokerFrame();
        activeClientProcesses = new ArrayList<ClientRequestProcess>();
        clientGateway = new ClientGateway(factoryName, clientRequestQueue) {
            @Override
            void onClientRequest(ClientRequest request) {
                LoanBroker.this.onClientRequest(request);
            }
        };

        //BAS: Misschien moeten reply/request hier omgedraait worden!!!!!!!!
        creditGateway = new CreditGateway(factoryName, creditRequestQueue, creditReplyQueue) {};
        //creditGateway = new CreditGateway(factoryName, creditReplyQueue, creditRequestQueue) {}; //stackoverflowerror 

        bankGateway = new BankGateway(factoryName, bankReplyQueue) {};

        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {

                frame.setVisible(true);
            }
        });
    }

    /**
     * When a new client reques arrives:
     * 1. a new instance of ClientRequestProcess is created for this request,
     * 2. method  notifyFinished is implemented to remove the process from activeClientProcesses
     * 3. the new process is added to activeClientProcesses
     * @param message the incomming message containng the ClientRequest
     */
    private void onClientRequest(ClientRequest request) {
        System.out.println("loanbroker.LoanBroker - onClientRequest");
        final ClientRequestProcess p = new ClientRequestProcess(request, creditGateway, clientGateway, bankGateway) {

            @Override
            void notifySentClientReply(ClientRequestProcess process) {
                activeClientProcesses.remove(process);
            }

            @Override
            void notifyReceivedCreditReply(ClientRequest clientRequest, CreditReply reply) {
               frame.addObject(clientRequest, reply);
            }

            @Override
            void notifyReceivedBankReply(ClientRequest clientRequest, BankQuoteReply reply) {
                frame.addObject(clientRequest, reply);
            }
        };        
        activeClientProcesses.add(p);
        frame.addObject(null,request);
    }

    /**
     * starts all gateways
     */
    public void start() throws JMSException {
        clientGateway.start();
        creditGateway.start();
        bankGateway.start();
    }
    
    public void addBank(String factoryName, String destination, String expression)
    {
        bankGateway.addBank(factoryName, destination, expression);
    }
}