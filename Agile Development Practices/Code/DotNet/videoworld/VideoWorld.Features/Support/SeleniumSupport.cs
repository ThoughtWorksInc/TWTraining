using System;
using System.Diagnostics;
using System.IO;
using OpenQA.Selenium;
using OpenQA.Selenium.Remote;
using TechTalk.SpecFlow;

namespace VideoWorld.Features.Support
{
    [Binding]
    public static class SeleniumSupport
    {
        private static IWebDriver _driver;
        private static Process _iisProcess;

        [BeforeTestRun]
        public static void BeforeTestRun()
        {
            StartServer();
        }

        [AfterTestRun]
        public static void AfterTestRun()
        {
            StopServer();
        }

        [BeforeScenario("web")]
        public static void BeforeWebScenario()
        {
            _driver = new RemoteWebDriver(new Uri("http://localhost:4444/wd/hub"), DesiredCapabilities.HtmlUnit());
            ScenarioContext.Current.SetWebDriver(_driver);
            _driver.Navigate().GoToUrl("http://localhost:49786");
        }

        [AfterScenario("web")]
        public static void AfterWebScenario()
        {
            _driver.Quit();
            ScenarioContext.Current.SetWebDriver(null);
        }

        private static void StartServer()
        {
            var applicationRoot = Path.GetDirectoryName(Path.GetDirectoryName(Path.GetDirectoryName(AppDomain.CurrentDomain.BaseDirectory)));
            var applicationPath = Path.Combine(applicationRoot, "VideoWorld");
            var programFiles = Environment.GetFolderPath(Environment.SpecialFolder.ProgramFilesX86);
            _iisProcess = new Process
                              {
                                  StartInfo =
                                      {
                                          FileName = programFiles + "\\IIS Express\\iisexpress.exe",
                                          Arguments = string.Format("/path:\"{0}\" /port:{1}", applicationPath, 49786)
                                      }
                              };
            _iisProcess.Start();
        }

        private static void StopServer()
        {
            if (_iisProcess != null)
            {
                _iisProcess.Kill();
            }
        }
    }
}