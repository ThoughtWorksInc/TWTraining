using System;
using System.Web.Mvc;
using VideoWorld.Models;

namespace VideoWorld.Controllers
{
    public class StatementsController : Controller
    {
        private readonly StatementRepository repository;
        private readonly Customer customer;

        public StatementsController(StatementRepository repository, Customer customer)
        {
            this.repository = repository;
            this.customer = customer;
        }

        [AcceptVerbs(HttpVerbs.Post), ActionName("Index")]
        public RedirectResult Create()
        {
            var statement = new Statement(customer);
            int id = repository.Add(statement);
            return Redirect("/statements/" + id);
        }

        public ViewResult Show(int id)
        {
            var statement = repository.FindById(id);
            return View("Show", statement);
        }

        public ViewResult Index()
        {
            return View("Index", repository.FindByCustomer(customer));
        }
    }
}