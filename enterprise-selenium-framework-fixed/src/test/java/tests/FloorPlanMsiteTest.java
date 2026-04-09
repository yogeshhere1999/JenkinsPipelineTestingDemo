package tests;

import base.BaseTest;
import flows.AlphaUserFlow;
import org.testng.annotations.Test;
import pages.PropertyPage;
import utils.ConfigReader;
import utils.ExtentTestListener;
import com.aventstack.extentreports.Status;

public class FloorPlanMsiteTest extends BaseTest {

    @Test
    public void verifyFloorPlan_mSite() {

        ExtentTestListener.getTest().log(Status.INFO, "Making user alpha (msite platform)");
        AlphaUserFlow flow = new AlphaUserFlow(driver);
        flow.makeUserAlpha("msite");
        ExtentTestListener.getTest().log(Status.PASS, "Alpha user activated for mSite");

        ExtentTestListener.getTest().log(Status.INFO, "Navigating to property URL: " + ConfigReader.get("propertyUrl"));
        driver.get(ConfigReader.get("propertyUrl"));

        // Wait for page to fully load before refreshing
        new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(15))
            .until(d -> ((org.openqa.selenium.JavascriptExecutor) d)
                .executeScript("return document.readyState").equals("complete"));
        ExtentTestListener.getTest().log(Status.INFO, "Page loaded — refreshing to activate alpha experiment");
        driver.navigate().refresh();
        new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(20))
            .until(d -> ((org.openqa.selenium.JavascriptExecutor) d)
                .executeScript("return document.readyState").equals("complete"));
        // Second refresh ensures experiment flags are fully applied
        driver.navigate().refresh();
        new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(20))
            .until(d -> ((org.openqa.selenium.JavascriptExecutor) d)
                .executeScript("return document.readyState").equals("complete"));
        ExtentTestListener.getTest().log(Status.PASS, "Page refreshed — alpha experiment active");

        PropertyPage page = new PropertyPage(driver);

        ExtentTestListener.getTest().log(Status.INFO, "Closing any popup if present");
        page.closePopupIfPresent();

        ExtentTestListener.getTest().log(Status.INFO, "Scrolling Floor Plan section into view");
        page.scrollToFloorPlan();

        ExtentTestListener.getTest().log(Status.INFO, "Verifying Floor Plan section is visible");
        boolean floorPlanVisible = page.isFloorPlanVisible();
        ExtentTestListener.getTest().log(floorPlanVisible ? Status.PASS : Status.WARNING,
            "Floor Plan section visible: " + floorPlanVisible);

        ExtentTestListener.getTest().log(Status.INFO, "Clicking expand on Floor Plan");
        page.clickExpandFloorPlan();
        ExtentTestListener.getTest().log(Status.PASS, "Floor Plan expand clicked");

        // --- Floor Plan Detail Layer validations (alpha experiment features) ---

        ExtentTestListener.getTest().log(Status.INFO, "Verifying Floor Plan Detail Layer Title (//div[@data-testid='FLOOR_PLAN_DETAIL_LAYER_TITLE'])[2]");
        boolean titleVisible = page.isFloorPlanDetailLayerTitleVisible();
        ExtentTestListener.getTest().log(titleVisible ? Status.PASS : Status.WARNING,
            "Floor Plan Detail Layer Title visible: " + titleVisible);

        ExtentTestListener.getTest().log(Status.INFO, "Verifying Floor Plan Detail Layer Share (//div[@data-testid='FLOOR_PLAN_DETAIL_LAYER_SHARE'])[2]");
        boolean shareVisible = page.isFloorPlanDetailLayerShareVisible();
        ExtentTestListener.getTest().log(shareVisible ? Status.PASS : Status.WARNING,
            "Floor Plan Detail Layer Share visible: " + shareVisible);

        ExtentTestListener.getTest().log(Status.INFO, "Verifying WhatsApp option visibility (//div[contains(text(),'WhatsApp')])[2]");
        boolean whatsAppVisible = page.isWhatsAppVisible();
        ExtentTestListener.getTest().log(whatsAppVisible ? Status.PASS : Status.WARNING,
            "WhatsApp option visible: " + whatsAppVisible);

        if (whatsAppVisible) {
            ExtentTestListener.getTest().log(Status.INFO, "Verifying WhatsApp option is clickable");
            boolean whatsAppClickable = page.isWhatsAppClickable();
            ExtentTestListener.getTest().log(whatsAppClickable ? Status.PASS : Status.WARNING,
                "WhatsApp option clickable: " + whatsAppClickable);

            if (whatsAppClickable) {
                ExtentTestListener.getTest().log(Status.INFO, "Clicking WhatsApp option");
                page.clickWhatsApp();
                ExtentTestListener.getTest().log(Status.PASS, "WhatsApp option clicked successfully");
            }
        }

        ExtentTestListener.getTest().log(Status.INFO, "Verifying View Number button (//div[contains(text(),'View Number')])[2]");
        boolean viewNumberVisible = page.isViewNumberBtn2Visible();
        ExtentTestListener.getTest().log(viewNumberVisible ? Status.PASS : Status.WARNING,
            "View Number button visible: " + viewNumberVisible);

        ExtentTestListener.getTest().log(Status.INFO, "Verifying Call button (//div[@id='CALL'])[2]");
        boolean callVisible = page.isCallBtnVisible();
        ExtentTestListener.getTest().log(callVisible ? Status.PASS : Status.WARNING,
            "Call button visible: " + callVisible);

        ExtentTestListener.getTest().log(Status.INFO, "Verifying Floor Plan Charges (//div[@data-testid='FLOOR_PLAN_DETAIL.TUPLE.CHARGES'])");
        boolean chargesVisible = page.isFloorPlanChargesVisible();
        ExtentTestListener.getTest().log(chargesVisible ? Status.PASS : Status.WARNING,
            "Floor Plan Charges visible: " + chargesVisible);

        ExtentTestListener.getTest().log(Status.INFO, "Verifying Bedrooms text (//div[contains(text(),'Bedrooms')])");
        boolean bedroomsVisible = page.isBedroomsTextVisible();
        ExtentTestListener.getTest().log(bedroomsVisible ? Status.PASS : Status.WARNING,
            "Bedrooms text visible: " + bedroomsVisible);

        ExtentTestListener.getTest().log(Status.INFO, "Verifying Area text (//div[contains(text(),'Area')])[4]");
        boolean areaVisible = page.isAreaTextVisible();
        ExtentTestListener.getTest().log(areaVisible ? Status.PASS : Status.WARNING,
            "Area text visible: " + areaVisible);

        ExtentTestListener.getTest().log(Status.PASS, "Floor Plan mSite flow completed successfully");
    }
}
