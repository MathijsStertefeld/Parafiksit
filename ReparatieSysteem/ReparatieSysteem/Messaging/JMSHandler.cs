using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Apache.NMS.ActiveMQ;
using Spring.Messaging.Nms;
using Spring.Messaging.Nms.Listener;
using Spring.Messaging.Nms.Core;
using JMSTest;

namespace ReparatieSysteem.Messaging
{
    class JMSHandler
    {
        private const string URI = "tcp://localhost:61616";
        private const string DESTINATION = "test.queue";
        private NmsTemplate template;

        public JMSHandler()
        {
            ConnectionFactory connectionFactory = new ConnectionFactory(URI);
            template = new NmsTemplate(connectionFactory);

            using (SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer())
            {
                listenerContainer.ConnectionFactory = connectionFactory;
                listenerContainer.DestinationName = DESTINATION;
                listenerContainer.MessageListener = new Listener();
                listenerContainer.AfterPropertiesSet();
            }
        }

        public void Send(String message)
        {
            try
            {
                template.ConvertAndSend(DESTINATION, message);

            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
        }

    }
}
