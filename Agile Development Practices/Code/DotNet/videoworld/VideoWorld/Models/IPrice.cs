namespace VideoWorld.Models
{
    public interface IPrice
    {
        decimal GetCharge(int periodInDays);
        int GetFrequentRenterPoints(int daysRented);
    }
}