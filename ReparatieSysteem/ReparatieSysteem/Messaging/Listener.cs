using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Spring.Messaging.Nms;
using Apache.NMS;
using Spring.Messaging.Nms.Core;
//using System.Xml.Serialization;

namespace ReparatieSysteem.Messaging
{
    class Listener : IMessageListener
    {
        public Listener() { Console.WriteLine("Listener created.rn"); }

        public void OnMessage(IMessage message)
        {
            try
            {

                ITextMessage textMessage = message as ITextMessage;

                //XmlSerializer serializer = new XmlSerializer(typeof(ParafiksitOrderRequest));

                //ParafiksitOrderRequest request = (ParafiksitOrderRequest) serializer.Deserialize(new System.Xml.XmlTextReader(new System.IO.StringReader(textMessage.Text)));

                //Console.WriteLine(request.teststring);

                Console.WriteLine("Received: " + textMessage.Text);
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
        }
    }
}
