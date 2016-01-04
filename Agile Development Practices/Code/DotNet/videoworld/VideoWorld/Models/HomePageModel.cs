using System;
using System.Collections.Generic;

namespace VideoWorld.Models
{
    public class HomePageModel
    {
        public HomePageModel(IEnumerable<Movie> movies, Customer customer)
        {
            this.Movies = new List<Movie>(movies);
            this.Cart = customer.Cart;
        }

        public List<Movie> Movies { get; private set; }

        public Cart Cart { get; private set; }
    }
}