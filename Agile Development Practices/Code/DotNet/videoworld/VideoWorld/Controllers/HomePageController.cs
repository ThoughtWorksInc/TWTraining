using System;
using System.Collections.Generic;
using System.Web.Mvc;
using VideoWorld.Models;

namespace VideoWorld.Controllers
{
    public class HomePageController : Controller
    {
        private readonly Customer customer;

        public HomePageController(Customer customer)
        {
            this.customer = customer;
        }

        public ViewResult Index()
        {
            var movieRepo = new MovieRepository();
            List<Movie> movies = movieRepo.FindAllMovies();

            return View("Index", new HomePageModel(movies, customer));
        }
    }
}