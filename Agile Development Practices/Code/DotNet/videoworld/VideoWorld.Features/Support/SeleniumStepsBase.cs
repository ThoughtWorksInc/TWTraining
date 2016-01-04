using OpenQA.Selenium;
using TechTalk.SpecFlow;

namespace VideoWorld.Features.Support
{
    public abstract class SeleniumStepsBase
    {
        protected IWebDriver WebDriver
        {
            get { return ScenarioContext.Current.WebDriver(); }
        }
    }
}