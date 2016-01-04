using System;
using System.Web.Mvc;
using NUnit.Framework;
using VideoWorld.Controllers;
using VideoWorld.Models;

namespace UnitTests.Controllers
{
    public class HomePageControllerTests
    {
        private Customer customer;
        private HomePageController controller;

        [SetUp]
        public void SetUp()
        {
            customer = new Customer("John Smith");
            controller = new HomePageController(customer);
        }

        [Test]
        public void ShouldShowIndexView()
        {
            ViewResult result = controller.Index();
            Assert.That(result.ViewName, Is.EqualTo("Index"));
        }

        [Test]
        public void ViewShouldShowAListOfMovies()
        {
            ViewResult result = controller.Index();
            var model = (HomePageModel) result.Model;
            var movies = model.Movies;
            Assert.That(movies.Count, Is.EqualTo(3));
        }

        [Test]
        public void ModelShouldIncludeCart()
        {
            ViewResult result = controller.Index();
            var model = (HomePageModel)result.Model;
            Assert.That(model.Cart, Is.SameAs(customer.Cart));
        }
    }
}
