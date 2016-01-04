using NUnit.Framework;
using VideoWorld.Models;

namespace UnitTests.Models
{
    class ChildrensPriceTest
    {
        private readonly IPrice CHILDRENS_PRICE = new ChildrensPrice();

        [Test]
        public void ShouldCalculateCorrectChargeForChildrensMovie()
        {
            Assert.AreEqual(1.5, CHILDRENS_PRICE.GetCharge(1));
            Assert.AreEqual(1.5, CHILDRENS_PRICE.GetCharge(2));
            Assert.AreEqual(1.5, CHILDRENS_PRICE.GetCharge(3));
            Assert.AreEqual(3.0, CHILDRENS_PRICE.GetCharge(4));
            Assert.AreEqual(4.5, CHILDRENS_PRICE.GetCharge(5));
        }
    }
}
