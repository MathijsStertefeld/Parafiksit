package bank;

import bank.gui.BankFrame;
import java.util.Random;

/**
 * This class represents one Bank Application.
 * It should eventually:
 *  1. Receive BankQuoteRequest-s for a loan from the LoanBroker Messaging-Oriented Middleware (MOM).
 *  2. Randomly create BankQuoteReply for each request (use method "computeBankReply").
 *  3. Send the BankQuoteReply from the LoanBroker MOM.
 */
public class Bank {

    private static final int ERROR_CODE = 1;
    private static final int NO_ERROR_CODE = 0;
    private final double primeRate = 3.5;
    private String name;
    private double ratePremium = 0.5;
    private int maxLoanTerm = 10000;
    private int quoteCounter = 0;
    protected Random random = new Random();
    private BankFrame frame; // GUI
    private boolean debug_mode;
    
    private LoanBrokerGateway gateway;
    
    /**
     * attributes for connection to JMS
     */

    public Bank(String bankName,String factoryName, String bankRequestQueue, String bankReplyQueue, boolean debug_mode) throws Exception {
        super();
        this.name = bankName;
        this.debug_mode = debug_mode;
               
        gateway = new LoanBrokerGateway(factoryName, bankRequestQueue, bankReplyQueue){

            @Override
            void receivedQuoteRequest(BankQuoteRequest request) {
                processRequest(request);
            }   
        };

        // create GUI
        frame = new BankFrame(name, debug_mode) {

            @Override
            public boolean sendBankReply(BankQuoteRequest request, double interest, int error) {
                System.out.println("BANK REPLIED!");
                BankQuoteReply reply = createReply(interest,error);
                //return sendReply(request, reply);
                processReply(request, reply);
                return true;
            }
        };
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {

                frame.setVisible(true);
            }
        });

    }

    private BankQuoteReply createReply(double interest, int error){
       String quoteID = name + "-" + String.valueOf(++quoteCounter);
       return new BankQuoteReply(interest, quoteID, error);
    }

    /**
     * Processes a new request message. Only if the debug_mode is true, this method
     * randomly generates a reply and sends it back.
     * @param message
     */
//    private void onBankQuoteRequest(TextMessage message) {
//        try {
//            BankQuoteRequest request = serializer.requestFromString(message.getText());
//            frame.addRequest(request);
//            if (debug_mode) { // only in debug mode send immediately random reply
//
//                BankQuoteReply reply = computeReply(request);
//                Bank.this.sendReply(request, reply);
//
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
    private void processRequest(BankQuoteRequest request)
    {
        System.out.println("Bank.bank - processRequest");
        frame.addRequest(request);
        if (debug_mode) { // only in debug mode send immediately random reply
            BankQuoteReply reply = computeReply(request);
            processReply(request, reply);
        }
    }
    
    private void processReply(BankQuoteRequest request, BankQuoteReply reply)
    {
        System.out.println("Bank.bank - processReply");
        frame.addReply(request, reply);
        //gateway.sendBankReply(reply);
        gateway.sendQuoteOffer(request, reply);
    }

    /**
     * Randomly generates a reply for the given request.
     * @param request for which the reply should be generated.
     * @return randomly generated reply
     */
    private BankQuoteReply computeReply(BankQuoteRequest request) {

        double interest = 0.0;
        int error = ERROR_CODE;
        if (request.getTime() <= maxLoanTerm) {

            double temp = primeRate + ratePremium + (double) (request.getTime() / 12) / 10 + (double) random.nextInt(10) / 10;
            interest = round(temp, 1); // round to one decimal

            error = NO_ERROR_CODE;
        }
        return createReply(interest,error);

    }

    /**
     * Rounds a decimal number.
     * @param value is the decimal value to be rounded.
     * @param decimals the number of decimal places after rounding.
     * @return
     */
    private double round(double value, int decimals) {
        final int temp = (int) Math.pow(10, decimals);// temp will be 1 or 10 or 100 or 1000 or , ...
        double result = Math.round(value * temp);
        result = result / temp;
        return result;
    }

    /**
     * Opens connection to JMS,so that messages can be send and received.
     */
    public void start() {
        gateway.start();
    }
}
