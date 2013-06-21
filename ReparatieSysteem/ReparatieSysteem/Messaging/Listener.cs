using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Spring.Messaging.Nms;
using Apache.NMS;
using Spring.Messaging.Nms.Core;

namespace JMSTest
{
    class Listener : IMessageListener
    {
        public void OnMessage(IMessage message)
        {
            ITextMessage textMessage = message as ITextMessage;
            Console.WriteLine(textMessage.Text);
        }
    }
}
