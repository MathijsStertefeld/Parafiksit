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

        public ParafiksitOrderRequest(List<WorkPerformedInfo> work, Contact contact, ShippingAddress shipping)
        {
            this.workPerformed = work;
            this.contact = contact;
            this.shippingAddress = shipping;
        }

        public List<WorkPerformedInfo> getWork()
        {
            return workPerformed;
        }

        public void setWork(List<WorkPerformedInfo> work)
        {
            this.workPerformed = work;
        }

        public ShippingAddress getShippingAddress()
        {
            return shippingAddress;
        }

        public void setShippingAddress(ShippingAddress shippingAddress)
        {
            this.shippingAddress = shippingAddress;
        }

        public Contact getContact()
        {
            return contact;
        }

        public void setContact(Contact contact)
        {
            this.contact = contact;
        }
    }
}
