package testcases;

import base.TestBase;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.TestUtil;

import java.util.Hashtable;


public class OpenAccountTest extends TestBase {
    @Test(dataProviderClass = TestUtil.class, dataProvider = "dp")
    public void openAccountTest(Hashtable<String,String> data) throws InterruptedException {

        click("open_account_XPATH");
        select("customer_CSS",data.get("customer"));
        select("currency_CSS",data.get("currency"));
        click("Process_XPATH");

        log.debug("Open Account executed");

        Reporter.log("Open Account executed");


        Thread.sleep(2000);
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();
    }
}