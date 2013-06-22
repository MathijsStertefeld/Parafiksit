using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Apache.NMS;
using Apache.NMS.ActiveMQ;
using Spring.Messaging.Nms;
using Spring.Messaging.Nms.Listener;
using Spring.Messaging.Nms.Core;
using Spring.Messaging.Nms.Support.Destinations;

namespace ReparatieSysteem.Messaging
{
    class JMSHandler
    {
        private const string URI = "tcp://localhost:61616"; //3700 of 7676
        private const string DESTINATION_REQUEST = "parafiksitOrderRequestQueue";
        private const string DESTINATION_REPLY = "parafiksitOrderReplyQueue";
        private ConnectionFactory connectionFactory;

        public JMSHandler()
        {
            connectionFactory = new ConnectionFactory(URI);
            //template = new NmsTemplate(connectionFactory);

            SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer();
            
            listenerContainer.ConnectionFactory = connectionFactory;
            listenerContainer.DestinationName = DESTINATION_REQUEST;
            listenerContainer.MessageListener = new Listener();
            listenerContainer.AfterPropertiesSet();
        }

        public void Send(String message)
        {
            try
            {
                IConnection conn = connectionFactory.CreateConnection();
                conn.Start();
                ISession sess = conn.CreateSession();
                NmsDestinationAccessor destinationResolver = new NmsDestinationAccessor();
                IDestination dest = destinationResolver.ResolveDestinationName(sess, DESTINATION_REPLY);
                IMessageProducer prod = sess.CreateProducer(dest);
                ITextMessage textMessage = prod.CreateTextMessage(message);
                prod.Send(textMessage);
                Console.WriteLine("Sent: " + message);

            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
        }

    }
}
