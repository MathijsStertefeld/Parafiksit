package creditbureau;

import creditbureau.gui.CreditFrame;
import java.util.Random;

/**
 * This class represents one Credit Agency Application.
 * It should:
 *  1. Receive CreditRequest-s for a loan from the LoanBroker Messaging-Orianted Middleware (MOM).
 *  2. Randomly create CreditReply for each request (use method "getReply").
 *  3. Send the CreditReply from the LoanBroker MOM.
 */
public class CreditBureau {

    private Random random = new Random(); // for random generation of replies
    private CreditFrame frame; // GUI
    private LoanBrokerGateway gateway;
    //private CreditSerializer serializer; // serializer CreditRequest CreditReply to/from XML:
    /**
     * attributes for connection to JMS
     */
    //private Connection connection; // connection to the JMS server
    //protected Session session; // JMS session fro creating producers, consumers and messages
    //private MessageProducer producer; // producer for sending messages
    //private MessageConsumer consumer; // consumer for receiving messages

    public CreditBureau(String factoryName, String creditRequestQueue, String creditReplyQueue) throws Exception {
        super();
        // connect to JMS
//        Context jndiContext = new InitialContext();
//        ConnectionFactory connectionFactory = (ConnectionFactory) jndiContext.lookup(factoryName);
//        connection = connectionFactory.createConnection();
//        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//
//        // connect to the sender channel
//        Destination senderDestination = (Destination) jndiContext.lookup(creditReplyQueue);
//        producer = session.createProducer(senderDestination);
//
//        // connect to the receiver channel and register as a listener on it
//        Destination receiverDestination = (Destination) jndiContext.lookup(creditRequestQueue);
//        consumer = session.createConsumer(receiverDestination);
//        consumer.setMessageListener(new MessageListener() {
//
//            public void onMessage(Message msg) {
//                onCreditRequest((TextMessage) msg);
//            }
//        });
//        // create the serializer
//        serializer = new CreditSerializer();
        //System.out.println("creditbureau aangemaakt: " + creditRequestQueue);
        gateway = new LoanBrokerGateway(factoryName, creditRequestQueue, creditReplyQueue){

            @Override
            void receivedCreditRequest(CreditRequest request) {
                System.out.println("creditbureau.LoanBrokerGateway - receivedRequest / creditbureau.CreditBureau - receivedCreditRequest");
                processRequest(request);
            }   
        };
        
        // create GUI
        frame = new CreditFrame();
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {

                frame.setVisible(true);
            }
        });
    }
/**
     * Processes a new request message by randomly generating a reply and sending it back.
     * @param message the credit request message
     */
//    private void onCreditRequest(TextMessage message) {
//        try {
//            CreditRequest request = serializer.requestFromString(message.getText());
//            frame.addRequest(request);
//            CreditReply reply = computeReply(request);
//            Message replyMessage = session.createTextMessage(serializer.replyToString(reply));
//            producer.send(replyMessage);
//            frame.addReply(request, reply);
//        } catch (Exception ex) {
//            Logger.getLogger(CreditBureau.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
    private void processRequest(CreditRequest request)
    {
        System.out.println("creditBureau.CreditBureau - processRequest");
        frame.addRequest(request);
        CreditReply reply = computeReply(request);
        processReply(request, reply);
    }
    
    private void processReply(CreditRequest request, CreditReply reply)
    {  
        System.out.println("creditBureau.CreditBureau - processReply");
        gateway.sendCreditHistory(request, reply);
        frame.addReply(request, reply);
    }
    
   /**
    * Randomly generates a CreditReply given the request.
    * @param request is the Creditrequest for which the reply must be generated
    * @return a credit reply
    */
    private CreditReply computeReply(CreditRequest request) {
        int ssn = request.getSSN();

        int score = (int) (random.nextInt(600) + 300);
        int history = (int) (random.nextInt(19) + 1);

        return new CreditReply(ssn, score, history);
    }
    /**
     * Opens connestion to JMS,so that messages can be send and received.
     */
    public void start() {
        gateway.start();
    }
}
