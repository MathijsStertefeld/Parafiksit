using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ReparatieSysteem.Messaging
{
    class ShippingAddress
    {
        private String street;
        private String number;
        private String postalCode;
        private String place;

        public String getStreet()
        {
            return street;
        }

        public String getNumber()
        {
            return number;
        }

        public String getPostalCode()
        {
            return postalCode;
        }

        public String getPlace()
        {
            return place;
        }

        public ShippingAddress(String street, String number, String postalCode, String place)
        {
            this.street = street;
            this.number = number;
            this.postalCode = postalCode;
            this.place = place;
        }

    }
}
