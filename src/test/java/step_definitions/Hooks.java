package step_definitions;

import java.net.MalformedURLException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import helpers.Log;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.opera.OperaDriver;

public class Hooks {
    public static WebDriver driver;


    @Before
    /**
     * Delete all cookies at the start of each scenario to avoid
     * shared state between tests
     */
    public void openBrowser()  {
        Log.info("Called openBrowser");
        System.setProperty("webdriver.chrome.driver", "C:\\drivers\\chromedriver.exe");

        ChromeOptions cOptions = new ChromeOptions();
        cOptions.addArguments("disable-infobars");
        cOptions.addArguments("--js-flags=--expose-gc");
        cOptions.addArguments("--disable-popup-blocking");
        driver = new ChromeDriver(cOptions);
        // driver = new ChromeDriver();
        //System.setProperty("webdriver.edge.driver", "C:\\drivers\\MicrosoftWebDriver.exe");
        //System.setProperty("webdriver.opera.driver", "C:\\drivers\\operadriver.exe");
        //driver = new EdgeDriver(); //OperaDriver(); //ChromeDriver(); //
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
    }


    @After
    /**
     * Embed a screenshot in test report if test is marked as failed
     */
    public void embedScreenshot(Scenario scenario) {

        if (scenario.isFailed()) {
            try {
                scenario.write("Current Page URL is " + driver.getCurrentUrl());
//              byte[] screenshot = getScreenshotAs(OutputType.BYTES);
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.embed(screenshot, "image/png");
            } catch (WebDriverException somePlatformsDontSupportScreenshots) {
                System.err.println(somePlatformsDontSupportScreenshots.getMessage());
            }
        }
        driver.quit();
    }
}