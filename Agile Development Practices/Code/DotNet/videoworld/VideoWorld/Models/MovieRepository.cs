using System.Collections.Generic;

namespace VideoWorld.Models
{
    public class MovieRepository
    {
        public List<Movie> FindAllMovies()
        {
            return new List<Movie>()
                       {
                           new Movie("Avatar", new RegularPrice()),
                           new Movie("Up in the Air", new RegularPrice()),
                           new Movie("Finding Nemo", new RegularPrice())
                       };
        }
    }
}