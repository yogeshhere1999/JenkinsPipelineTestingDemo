
package utils;

import com.aventstack.extentreports.*;
import org.testng.*;

import driver.DriverManager;

public class ExtentTestListener implements ITestListener {

    private static ExtentReports extent = ExtentManager.getInstance();
   // private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    public static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    public static ExtentTest getTest() {
        return test.get();
    }
    public static ExtentTest getSafeTest() {

        if (test.get() == null) {
            return ExtentManager.getInstance().createTest("DefaultTest");
        }

        return test.get();
    }
    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String className = result.getTestClass().getRealClass().getSimpleName();
        String platform = result.getTestContext().getCurrentXmlTest().getParameter("platform");
        String label = className + " :: " + testName + (platform != null ? " [" + platform + "]" : "");
        test.set(extent.createTest(label));
        test.get().log(Status.INFO, "Test started");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().pass("Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String path = ScreenshotUtils.capture(DriverManager.getDriver(), result.getMethod().getMethodName());
        test.get().fail(result.getThrowable());
        if (path != null) {
            try {
                test.get().addScreenCaptureFromPath(path);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}
