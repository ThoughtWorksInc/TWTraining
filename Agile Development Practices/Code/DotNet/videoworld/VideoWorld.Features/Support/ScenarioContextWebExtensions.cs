using System;
using System.Text;
using NUnit.Framework;
using OpenQA.Selenium;
using TechTalk.SpecFlow;

namespace VideoWorld.Features.Support
{
    public static class ScenarioContextWebExtensions
    {
        public static IWebDriver WebDriver(this ScenarioContext scenarioContext)
        {
            try
            {
                var result = (IWebDriver)ScenarioContext.Current["webDriver"];
                return result;
            }
            catch (Exception)
            {
                throw new Exception("WebDriver is not started");
            }
        }

        public static bool IsWebDriverRunning(this ScenarioContext scenarioContext)
        {
            return ScenarioContext.Current["webDriver"] != null;
        }

        public static void SetWebDriver(this ScenarioContext scenarioContext, IWebDriver webDriver)
        {
            ScenarioContext.Current["webDriver"] = webDriver;
            ScenarioContext.Current["webDriver-errors"] = new StringBuilder();
            ScenarioContext.Current["StepCounter"] = 0;
        }

        public static StringBuilder WebDriverErrors(this ScenarioContext scenarioContext)
        {
            var result = (StringBuilder)ScenarioContext.Current["webDriver-errors"];
            Assert.IsNotNull(result, "WebDriver is not started");
            return result;
        }
    }
}