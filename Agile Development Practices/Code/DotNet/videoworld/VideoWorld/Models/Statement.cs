using System;

namespace VideoWorld.Models
{
    public class Statement
    {
        public Statement(Customer customer)
        {
            Customer = customer;
            Text = customer.Statement(customer.Cart.Rentals);
        }

        public Customer Customer { get; private set; }

        public string Text { get; private set; }
    }
}