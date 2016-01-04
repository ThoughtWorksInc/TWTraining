namespace VideoWorld.Models
{
    public class RegularPrice : IPrice
    {
        public decimal GetCharge(int periodInDays)
        {
            decimal result = 2.00m;
            if (periodInDays > 2)
                result += (periodInDays - 2) * 1.50m;
            return result;
        }

        public int GetFrequentRenterPoints(int daysRented)
        {
            return 1;
        }

    }
}