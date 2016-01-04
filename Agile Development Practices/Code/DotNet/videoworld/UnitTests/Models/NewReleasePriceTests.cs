using NUnit.Framework;
using VideoWorld.Models;

namespace UnitTests.Models
{
    class NewReleasePriceTests
    {
        private static readonly IPrice NewReleasePriceInstance = new NewReleasePrice();

        [Test]
        public void ShouldCalculateCorrectChargeForNewRelease()
        {
            Assert.AreEqual(3.0, NewReleasePriceInstance.GetCharge(1));
            Assert.AreEqual(6.0, NewReleasePriceInstance.GetCharge(2));
            Assert.AreEqual(9.0, NewReleasePriceInstance.GetCharge(3));
        }
    }
}
