package base;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import utils.ExcelReader;
import utils.ExtentReport;
import utils.TestUtil;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class TestBase {
    public static WebDriver driver;
    public static Properties config = new Properties();
    public static Properties OR = new Properties();
    public static FileInputStream fis;
    public static Logger log = Logger.getLogger("devpinoyLogger");
    public static ExcelReader excel = new ExcelReader("/Users/srg_kosenko/Documents/School projects/DataDrivenFramework/src/test/resources/excel/TestData.xlsx");
    public static WebDriverWait wait;
    public ExtentReports rep = ExtentReport.getInstance();
    public static ExtentTest test;
    public static String browser;

    @BeforeSuite
    public void  setUp() throws IOException {
        if (driver == null) {
            try {
                fis = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/properties/Config.properties");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            config.load(fis);
            log.debug("Chrome launched");

            try {
                fis = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/properties/OR.properties");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            OR.load(fis);


        }
        if (config.getProperty("browser").equals("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (config.getProperty("browser").equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();

        }

        driver.get(config.getProperty("testsiteurl"));
        log.debug("Navigated to : " + config.getProperty("testsiteurl"));
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")), TimeUnit.SECONDS);
        wait = new WebDriverWait(driver,5);
    }
    public void click(String locator) {

        driver.findElement(By.xpath(OR.getProperty(locator))).click();
        test.log(LogStatus.INFO,"Clicking on :" + locator);
    }

    public String getTitle() {
        return driver.getTitle();
    }

    static WebElement dropdown;
    public void select(String locator, String value) {

        if (locator.endsWith("_CSS")) {
            dropdown = driver.findElement(By.cssSelector(OR.getProperty(locator)));
        } else if (locator.endsWith("_XPATH")) {
            dropdown = driver.findElement(By.xpath(OR.getProperty(locator)));
        } else if (locator.endsWith("_ID")) {
            dropdown = driver.findElement(By.id(OR.getProperty(locator)));
        }

        Select select = new Select(dropdown);
        select.selectByVisibleText(value);

        test.log(LogStatus.INFO,"Selecting from dropdown: " + locator + " and picked -  " + value);
    }

    public void type(String locator,String value) {

        if (locator.endsWith("_CSS")) {
            driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
        } else if (locator.endsWith("_XPATH")) {
            driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
        } else if (locator.endsWith("_ID")) {
            driver.findElement(By.id(OR.getProperty(locator))).sendKeys(value);
        }

        test.log(LogStatus.INFO,"Typing in locator: " + locator + " and entered" + value);
    }

    public boolean isElementPresent(By by) {
        try {
        driver.findElement(by); return true;
        }catch (NoSuchElementException e) {
            return false;
        }
    }

    public static void verifyEquals(String expect,String actual) throws IOException {
        try {
            Assert.assertEquals(actual,expect);
        } catch (Throwable e) {
            TestUtil.captureScreenshot();
            //ReportNG
            Reporter.log("<br>"+"Verification failure : "+ e.getMessage() + "<br>");
            //Extent Reports
            test.log(LogStatus.FAIL, "Verification failed with exception :  " + e.getMessage());
            test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotName));
        }
    }



    @AfterSuite
    public void tearDown() {
        if(driver!=null) driver.quit();
        log.debug("test execution completed");
    }
}
