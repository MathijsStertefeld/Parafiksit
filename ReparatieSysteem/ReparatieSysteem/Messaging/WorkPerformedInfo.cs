using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ReparatieSysteem.Messaging
{
    class WorkPerformedInfo
    {
        private string description;

        private int price;

        public int Price
        {
            get { return price; }
            set { price = value; }
        }

        public String getDescription
        {
            get { return description; }
        }

        public WorkPerformedInfo(string description)
        {
            this.description = description;
        }
    }
}
