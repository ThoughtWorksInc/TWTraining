using NUnit.Framework;
using VideoWorld.Models;

namespace UnitTests.Models
{
    class RegularPriceTest
    {
        private static readonly IPrice RegularPriceInstance = new RegularPrice();

        [Test]
        public void ShouldCalculateCorrectChargeForRegularMovie()
        {
            Assert.AreEqual(2.00m, RegularPriceInstance.GetCharge(1));
            Assert.AreEqual(2.00m, RegularPriceInstance.GetCharge(2));
            Assert.AreEqual(3.50m, RegularPriceInstance.GetCharge(3));
            Assert.AreEqual(5.00m, RegularPriceInstance.GetCharge(4));
        }
    }
}
