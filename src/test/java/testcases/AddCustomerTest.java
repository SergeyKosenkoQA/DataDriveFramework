package testcases;

import base.TestBase;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;
import utils.TestUtil;

import java.util.Hashtable;


public class AddCustomerTest extends TestBase {
    @Test(dataProviderClass = TestUtil.class, dataProvider = "dp")
    public void addCustomerTest(Hashtable<String,String> data) throws InterruptedException {

        if(!data.get("runmode").equals("Y")) {
            throw new SkipException("Skipping the test as the RunMode at test data set up for N");
        }

        click("addCustomerBtn_XPATH");
        type("firstName_XPATH",data.get("firstName"));
        type("lastName_XPATH",data.get("lastName"));
        type("postCode_XPATH",data.get("postCode"));
        click("addBtn_XPATH");

        Thread.sleep(2000);


       Alert alert = wait.until(ExpectedConditions.alertIsPresent());

       Assert.assertTrue(alert.getText().contains(data.get("alertText")));
       alert.accept();

    }
}