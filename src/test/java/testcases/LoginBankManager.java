package testcases;

import base.TestBase;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginBankManager extends TestBase {


    @Test
    public void loginBankManager() throws IOException, InterruptedException {

        verifyEquals(getTitle(),"Protractor practice website - Banking App");
        Thread.sleep(3000);


        log.debug("Inside Login Test");

        click("adminBtn_XPATH");
       // driver.findElement(By.xpath("//button[contains(text(),'Bank Manager Login')]")).click();
        Assert.assertTrue(isElementPresent(By.xpath(OR.getProperty("addCustomerBtn_XPATH"))),"Login is not successful");
        log.debug("Login executed");

        Reporter.log("Login executed");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
