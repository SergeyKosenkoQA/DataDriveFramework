package listeners;

import base.TestBase;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.*;
import utils.TestUtil;

import java.io.IOException;
import java.util.Locale;

public class CustomListeners extends TestBase implements ITestListener {

    @Override
    public void onTestStart(ITestResult arg0) {
        test = rep.startTest(arg0.getTestName());
        //Runmodes - Y
        System.out.println(TestUtil.isTestRunnable(arg0.getTestName(),excel));
        if(!TestUtil.isTestRunnable(arg0.getTestName(),excel)) {
        throw new SkipException("Skipping the "+arg0.getTestName().toUpperCase()+" as the Run mode is No");
        }
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        test.log(LogStatus.PASS, iTestResult.getName().toUpperCase(Locale.ROOT) +" PASS");
        rep.endTest(test);
        rep.flush();
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        try {
            TestUtil.captureScreenshot();
        } catch (IOException e) {
            e.printStackTrace();
        }

        test.log(LogStatus.FAIL, iTestResult.getName().toUpperCase(Locale.ROOT) +" FAIL with exception: +" + iTestResult.getThrowable());
        test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotName));
        rep.endTest(test);
        rep.flush();
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        test.log(LogStatus.SKIP, iTestResult.getTestName().toUpperCase() + "Skipped test");
        rep.endTest(test);
        rep.flush();

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    @Override
    public void onStart(ITestContext iTestContext) {

    }

    @Override
    public void onFinish(ITestContext iTestContext) {

    }
}
