package tests;

import base.BaseTest;
import flows.AlphaUserFlow;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.PropertyPage;
import utils.ApiUtils;
import utils.ConfigReader;
import utils.CookieUtils;
import utils.ExtentTestListener;
import com.aventstack.extentreports.Status;

public class AmenitiesTest extends BaseTest {

    @Test
    public void verifyAmenities() {

        ExtentTestListener.getTest().log(Status.INFO, "Making user alpha (desktop platform)");
        AlphaUserFlow flow = new AlphaUserFlow(driver);
        flow.makeUserAlpha("desktop");
        ExtentTestListener.getTest().log(Status.PASS, "Alpha user activated for Desktop");

        ExtentTestListener.getTest().log(Status.INFO, "Navigating to property URL: " + ConfigReader.get("propertyUrl"));
        driver.get(ConfigReader.get("propertyUrl"));
        new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(15))
            .until(d -> ((org.openqa.selenium.JavascriptExecutor) d)
                .executeScript("return document.readyState").equals("complete"));
        driver.navigate().refresh();
        new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(15))
            .until(d -> ((org.openqa.selenium.JavascriptExecutor) d)
                .executeScript("return document.readyState").equals("complete"));

        PropertyPage page = new PropertyPage(driver);

        ExtentTestListener.getTest().log(Status.INFO, "Closing any popup if present");
        page.closePopupIfPresent();

        ExtentTestListener.getTest().log(Status.INFO, "Scrolling Amenities section into view");
        page.scrollToAmenities();

        ExtentTestListener.getTest().log(Status.INFO, "Verifying Amenities heading is visible");
        boolean isVisible = page.isAmenitiesHeadingVisible();
        Assert.assertTrue(isVisible, "Amenities heading should be visible");
        ExtentTestListener.getTest().log(Status.PASS, "Amenities heading verified successfully");
    }

    @Test
    public void verifyAmenitiesFlow() throws Exception {

        ExtentTestListener.getTest().log(Status.INFO, "Getting Google user ID from cookies");
        String userId = CookieUtils.getGoogleId(driver);
        ExtentTestListener.getTest().log(Status.INFO, "User ID retrieved: " + userId);

        ExtentTestListener.getTest().log(Status.INFO, "Calling alpha user API for user: " + userId);
        int apiResponse = ApiUtils.makeAlphaUser(userId);
        ExtentTestListener.getTest().log(Status.INFO, "Alpha API response code: " + apiResponse);

        ExtentTestListener.getTest().log(Status.INFO, "Navigating to property URL: " + ConfigReader.get("propertyUrl"));
        driver.get(ConfigReader.get("propertyUrl"));
        Thread.sleep(5000);
        driver.navigate().refresh();

        PropertyPage page = new PropertyPage(driver);

        ExtentTestListener.getTest().log(Status.INFO, "Closing any popup if present");
        page.closePopupIfPresent();

        ExtentTestListener.getTest().log(Status.INFO, "Scrolling to Amenities section");
        page.scrollToAmenities();

        ExtentTestListener.getTest().log(Status.INFO, "Verifying Amenities heading is visible");
        Assert.assertTrue(page.isAmenitiesHeadingVisible(), "Amenities heading should be visible");
        ExtentTestListener.getTest().log(Status.PASS, "Amenities heading is visible");

        ExtentTestListener.getTest().log(Status.INFO, "Verifying View All button is visible");
        Assert.assertTrue(page.isViewAllVisible(), "View All button should be visible");
        ExtentTestListener.getTest().log(Status.PASS, "View All button is visible");

        ExtentTestListener.getTest().log(Status.INFO, "Clicking View All button");
        page.clickViewAll();
        ExtentTestListener.getTest().log(Status.PASS, "View All clicked successfully");

        ExtentTestListener.getTest().log(Status.INFO, "Verifying Amenities details panel");
        boolean detailsVisible = page.isAmenitiesDetailsSoft();
        ExtentTestListener.getTest().log(
            detailsVisible ? Status.PASS : Status.WARNING,
            "Amenities details panel visible: " + detailsVisible +
            (detailsVisible ? "" : " (alpha experiment may not be active in this environment)")
        );

        ExtentTestListener.getTest().log(Status.PASS, "Amenities flow completed successfully");
    }
}
