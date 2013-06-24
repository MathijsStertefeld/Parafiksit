using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ReparatieSysteem.Messaging
{
    class ParafiksitOrderReply
    {
        public string NameClient { get; private set; }
        public ShippingAddress address { get; private set; }
        public List<WorkPerformedInfo> WorkPerformed { get; private set; }
        public int TotalPriceForWorkPerformed { get; private set; }
        public string BankAccount { get; private set; }

        public ParafiksitOrderReply(string nameClient, ShippingAddress address, List<Ticket> tickets)
        {
            this.NameClient = nameClient;
            this.address = address;

            foreach (Ticket t in tickets)
            {
                WorkPerformedInfo w = new WorkPerformedInfo(t.Probleem);
                w.Price = (int)t.VerwachteKosten;
                TotalPriceForWorkPerformed += (int)t.VerwachteKosten;
            }

            BankAccount = "8797701";
        }
    }
}
