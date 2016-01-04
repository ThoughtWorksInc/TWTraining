using System.Collections.Generic;
using NUnit.Framework;
using VideoWorld.Models;

namespace UnitTests.Models
{
    internal class CustomerTests
    {
        [Test]
        public void Empty()
        {
            var customer = new Customer("John Smith");
            const string noRentalsStatement = "Rental Record for John Smith\n"
                                              + "Amount charged is $0.00\n"
                                              + "You have a new total of 0 frequent renter points";
            Assert.AreEqual(noRentalsStatement, customer.Statement(new List<Rental>()));
        }

        [Test]
        public void NonEmpty()
        {
            const string expected = "Rental Record for John Smith\n"
                                    + "  Monty Python and the Holy Grail  -  $3.50\n"
                                    + "  Ran  -  $2.00\n"
                                    + "  LA Confidential  -  $6.00\n"
                                    + "  Star Trek 13.2  -  $3.00\n"
                                    + "  Wallace and Gromit  -  $6.00\n"
                                    + "Amount charged is $20.50\n"
                                    + "You have a new total of 6 frequent renter points";

            var customer = new Customer("John Smith");

            var montyPython = new Movie("Monty Python and the Holy Grail", new RegularPrice());
            var ran = new Movie("Ran", new RegularPrice());
            var laConfidential = new Movie("LA Confidential", new NewReleasePrice());
            var starTrek = new Movie("Star Trek 13.2", new NewReleasePrice());
            var wallaceAndGromit = new Movie("Wallace and Gromit", new ChildrensPrice());

            var mixedRentals = new List<Rental>();
            mixedRentals.Add(new Rental(montyPython, 3));
            mixedRentals.Add(new Rental(ran, 1));
            mixedRentals.Add(new Rental(laConfidential, 2));
            mixedRentals.Add(new Rental(starTrek, 1));
            mixedRentals.Add(new Rental(wallaceAndGromit, 6));

            Assert.AreEqual(expected, customer.Statement(mixedRentals));
        }
    }
}