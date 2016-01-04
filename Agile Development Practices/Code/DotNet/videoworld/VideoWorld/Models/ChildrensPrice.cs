using System;

namespace VideoWorld.Models
{
    public class ChildrensPrice : IPrice
    {
        public decimal GetCharge(int daysRented)
        {
            decimal result = 1.50m;
            if (daysRented > 3)
                result += (daysRented - 3) * 1.50m;
            return result;
        }

        public int GetFrequentRenterPoints(int daysRented)
        {
            return 1;
        }
    }
}