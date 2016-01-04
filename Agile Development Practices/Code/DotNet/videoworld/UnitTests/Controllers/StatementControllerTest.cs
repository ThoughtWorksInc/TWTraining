using System;
using System.Linq;
using System.Text;
using System.Web.Mvc;
using NUnit.Framework;
using VideoWorld.Controllers;
using VideoWorld.Models;

namespace UnitTests.Controllers
{
    class StatementControllerTest
    {
        private Customer customer;
        private StatementRepository repository;
        private StatementsController controller;

        [SetUp]
        public void SetUp()
        {
            customer = new Customer();
            repository = new StatementRepository();
            controller = new StatementsController(repository, customer);
        }

        [Test]
        public void ShouldRedirectToStatementView()
        {
            RedirectResult redirect = controller.Create();
            Assert.That(redirect.Url, Is.StringMatching(@"/statements/\d+"));
        }

        [Test]
        public void ShouldCreateStatementWithSameCustomer()
        {
            controller.Create();
            Assert.That(repository.FindById(0).Customer, Is.SameAs(customer));
        }

        [Test]
        public void ShouldRecordStatement()
        {
            controller.Create();
            Assert.That(repository.FindById(0).Text, Contains.Substring("Amount charged is"));
        }

        [Test]
        public void ShouldPopulatestatementModel()
        {
            var statement = new Statement(customer);
            repository.Add(statement);
            ViewResult result = controller.Show(0);
            Assert.That(result.Model, Is.SameAs(statement));
        }

        [Test]
        public void ShouldShowStatementHistory()
        {
            var s1 = new Statement(customer);
            var s2 = new Statement(customer);

            repository.Add(s1);
            repository.Add(s2);

            ViewResult result = controller.Index();
            Assert.That(result.Model, Contains.Item(s1));
            Assert.That(result.Model, Contains.Item(s2));
        }
    }
}
