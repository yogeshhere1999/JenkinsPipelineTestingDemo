package tests;

import base.BaseTest;
import flows.AlphaUserFlow;
import org.testng.annotations.Test;
import utils.ExtentTestListener;
import com.aventstack.extentreports.Status;

public class AlphaUserTest extends BaseTest {

    @Test
    public void createAlphaUser() {

        ExtentTestListener.getTest().log(Status.INFO, "Navigating to base URL: " + driver.getCurrentUrl());

        ExtentTestListener.getTest().log(Status.INFO, "Initializing AlphaUserFlow");
        AlphaUserFlow flow = new AlphaUserFlow(driver);

        ExtentTestListener.getTest().log(Status.INFO, "Calling makeUserAlpha() via API");
        flow.makeUserAlpha();

        ExtentTestListener.getTest().log(Status.PASS, "Alpha user created successfully");
    }
}
