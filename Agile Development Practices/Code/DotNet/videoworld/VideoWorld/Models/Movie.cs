using System;

namespace VideoWorld.Models
{
    public class Movie
    {
        private readonly string title;

        public IPrice Price { get; set; }

        public Movie(string title, IPrice price)
        {
            Price = price;
            if (string.IsNullOrEmpty(title))
            {
                throw new ArgumentNullException("title");
            }

            this.title = title;
        }

        public bool Equals(Movie other)
        {
            if (ReferenceEquals(null, other)) return false;
            if (ReferenceEquals(this, other)) return true;
            return Equals(other.title, title);
        }

        public override bool Equals(object obj)
        {
            if (ReferenceEquals(null, obj)) return false;
            if (ReferenceEquals(this, obj)) return true;
            if (obj.GetType() != typeof (Movie)) return false;
            return Equals((Movie) obj);
        }

        public override int GetHashCode()
        {
            return (title != null ? title.GetHashCode() : 0);
        }

        public string Title
        {
            get { return title; }
        }

    }
}