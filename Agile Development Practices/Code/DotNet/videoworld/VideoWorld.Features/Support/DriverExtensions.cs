using System;
using System.Collections.Generic;
using System.Linq;
using OpenQA.Selenium;

namespace VideoWorld.Features.Support
{
    public static class DriverExtensions
    {
        public const int DefaultTimeout = 10;

        public static void Wait(this IWebDriver driver, int seconds = DefaultTimeout)
        {
            if (seconds <= 60)
                seconds *= 1000;

            System.Threading.Thread.Sleep(seconds);
        }

        public static IWebElement FindElement(this IWebDriver driver, By by, Func<IWebElement, bool> predicate)
        {
            return driver.FindElements(by, predicate).FirstOrDefault();
        }

        public static IEnumerable<IWebElement> FindElements(this IWebDriver driver, By by, Func<IWebElement, bool> predicate)
        {
            return driver.FindElements(by).Where(predicate);
        }

        public static IWebElement WaitForElement(this IWebDriver driver, By by, Func<IWebElement, bool> predicate = null, int seconds = DefaultTimeout)
        {
            return driver.WaitForElements(by, predicate, seconds).First();
        }

        public static IEnumerable<IWebElement> WaitForElements(this IWebDriver driver, By by, Func<IWebElement, bool> predicate = null, int seconds = DefaultTimeout)
        {
            IEnumerable<IWebElement> els;
            var retry = 0;

            do
            {
                retry++;
                driver.Wait(1);

                els = driver.FindElements(by);
                if (predicate != null)
                    els = els.Where(predicate);

            } while (els.Count() == 0 && retry < seconds);

            return els;
        }

    }
}