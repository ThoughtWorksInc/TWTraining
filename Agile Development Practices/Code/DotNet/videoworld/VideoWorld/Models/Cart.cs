using System;
using System.Collections.Generic;

namespace VideoWorld.Models
{
    public class Cart
    {
        private readonly List<Rental> rentals = new List<Rental>();

        public bool Contains(Rental movie)
        {
            return rentals.Contains(movie);
        }

        public int Count
        {
            get { return rentals.Count; }
        }

        public List<Rental> Rentals
        {
            get {
                return rentals;
            }
        }

        public void AddMovie(Movie movie)
        {
            rentals.Add(new Rental(movie, 1));
        }
    }
}