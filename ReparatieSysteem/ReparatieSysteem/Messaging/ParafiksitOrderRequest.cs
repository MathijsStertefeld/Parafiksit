using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ReparatieSysteem.Messaging
{
    class ParafiksitOrderRequest
    {
        public List<WorkPerformedInfo> workPerformed;
        public Contact contact;
        public ShippingAddress shippingAddress;

        public ParafiksitOrderRequest(List<WorkPerformedInfo> work)
        {
            this.workPerformed = work;
        }

        public List<WorkPerformedInfo> getWork()
        {
            return workPerformed;
        }

        public void setWork(List<WorkPerformedInfo> work)
        {
            this.workPerformed = work;
        }
    }
}
