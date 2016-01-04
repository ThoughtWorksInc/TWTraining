using NUnit.Framework;
using VideoWorld.Models;

namespace UnitTests.Models
{
    public class MovieTests
    {
        [TestCase(null)]
        [TestCase("")]
        public void ShouldRequireTitle(string invalidTitle)
        {
            Assert.That(() => new Movie(invalidTitle, new RegularPrice()), Throws.Exception);
        }

        [Test]
        public void ShouldContainTitle()
        {
            var movie = new Movie("Avatar", new RegularPrice());
            Assert.That(movie.Title, Is.EqualTo("Avatar"));
        }

        private static readonly Movie RegularMovieInstance = new Movie("Regular", new RegularPrice());
        private static readonly Movie NewReleaseMovieInstance = new Movie("NewRelease", new NewReleasePrice());
        private static readonly Movie ChildrensMovieInstance = new Movie("Childrens", new ChildrensPrice());

        [Test]
        public void ShouldCalculateCorrentFrequentRenterPointsForNonNewReleaseMovie()
        {
            Assert.AreEqual(1, RegularMovieInstance.Price.GetFrequentRenterPoints(1));
            Assert.AreEqual(1, RegularMovieInstance.Price.GetFrequentRenterPoints(4));
            Assert.AreEqual(1, ChildrensMovieInstance.Price.GetFrequentRenterPoints(1));
            Assert.AreEqual(1, ChildrensMovieInstance.Price.GetFrequentRenterPoints(4));
        }

        [Test]
        public void ShouldCalculateCorrentFrequentRenterPointsForNewReleaseMovie()
        {
            Assert.AreEqual(1, NewReleaseMovieInstance.Price.GetFrequentRenterPoints(1));
            Assert.AreEqual(2, NewReleaseMovieInstance.Price.GetFrequentRenterPoints(2));
            Assert.AreEqual(2, NewReleaseMovieInstance.Price.GetFrequentRenterPoints(3));
        }

    }
}