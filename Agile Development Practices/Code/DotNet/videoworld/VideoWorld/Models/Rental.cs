using System;

namespace VideoWorld.Models
{
    public class Rental 
    {
        private readonly Movie movie;
        private readonly int periodInDays;

        public Rental(Movie movie, int periodInDays)
        {
            this.movie = movie;
            this.periodInDays = periodInDays;
        }

        public Movie Movie
        {
            get {
                return movie;
            }
        }

        public int Period
        {
            get { return periodInDays; }
        }
    }
}