
package pages;

import base.BasePage;

import org.openqa.selenium.JavascriptExecutor;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PropertyPage extends BasePage {

    public PropertyPage(WebDriver driver) {
        super(driver);
    }
   
    
    

        // First wait for element
     //   WebElement element = wait.visible(amenitiesSection);

        // Then scroll
    
    
    public static void scrollUntilVisible(WebDriver driver, By locator) {

        JavascriptExecutor js = (JavascriptExecutor) driver;

        for (int i = 0; i < 15; i++) {

            try {
                WebElement element = driver.findElement(locator);

                if (element.isDisplayed()) {
                    return;
                }

            } catch (Exception ignored) {}

            // scroll more aggressively
            js.executeScript("window.scrollBy(0,800)");

            try {
                Thread.sleep(1000); // allow lazy load
            } catch (Exception ignored) {}
        }

        throw new RuntimeException("Element not found after scrolling: " + locator);
    }
    
     // 🔥 MUST
    
    public void scrollToAmenities() {
        WebElement el = wait.visible(amenitiesHeading);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", el);
    }
    public void scrollToFloorPlan() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, 0)");
        By floorPlanCard = By.xpath("//*[@data-testid='FLOOR_PLAN_AREA_TYPE_OPTION']");
        By floorPlanSection = By.xpath(
            "//*[contains(text(),'Floor Plan') or contains(text(),'floor plan')]" +
            " | //*[@data-testid='FLOOR_PLAN_AREA_TYPE_OPTION']" +
            " | //*[contains(@class,'floorPlan') or contains(@class,'floor-plan') or contains(@class,'FloorPlan')]"
        );
        for (int i = 0; i < 30; i++) {
            // First priority: card element
            try {
                WebElement el = driver.findElement(floorPlanCard);
                js.executeScript("arguments[0].scrollIntoView({block:'center'});", el);
                try { Thread.sleep(500); } catch (Exception ignored) {}
                return;
            } catch (Exception ignored) {}
            // Second priority: any floor plan section
            try {
                WebElement el = driver.findElement(floorPlanSection);
                js.executeScript("arguments[0].scrollIntoView({block:'center'});", el);
                try { Thread.sleep(500); } catch (Exception ignored) {}
                return;
            } catch (Exception ignored) {}
            js.executeScript("window.scrollBy(0, 400)");
            try { Thread.sleep(500); } catch (Exception ignored) {}
        }
        // Last resort: scroll to floorPlanHeading via wait
        try {
            WebElement heading = wait.visible(floorPlanHeading);
            js.executeScript("arguments[0].scrollIntoView({block:'center'});", heading);
        } catch (Exception e) {
            throw new RuntimeException("Floor Plan section not found after scrolling: " + e.getMessage());
        }
    }
    
//    page.scrollToAmenities();
//    wait.visible(amenitiesHeading); 
    
    
    
    
//    	public void scrollToAmenities1() {
//    	    WebElement element = wait.visible(amenitiesHeading);
//    	    JavascriptExecutor js = (JavascriptExecutor) driver;
//    	    js.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
//    	}
    //private By amenitiesSection = By.xpath("//div[contains(text(),'Amenities')]");
//    private By viewAllBtn = By.xpath("//button[contains(text(),'View All')]");
//    
    
 // Amenities heading
    By amenitiesHeading = By.xpath("//*[contains(text(),'Amenities')]");

    // Subheading
    By amenitiesSubHeading = By.xpath("//*[contains(text(),'ABA Cleo County')]");

    // View All button
    By viewAllBtn = By.xpath("//*[@data-testid='AMENITIES.VIEW_ALL'] | //a[contains(text(),'View All')]");

    // Amenities detail section - alpha panel OR standard amenities list/popup
    By amenitiesDetails = By.xpath(
        "//*[contains(@class,'UniquesFacilities')]" +
        " | //*[contains(@class,'ameniti') or contains(@class,'Ameniti')]" +
        " | //*[@role='dialog' or @role='modal' or contains(@class,'modal') or contains(@class,'popup') or contains(@class,'overlay')]" +
        " | //*[contains(@class,'pageComponent') and contains(@class,'facilities')]"
    );

    // View number button
    By viewNumberBtn = By.xpath("//span[normalize-space()='View number']");

    // Cross icon
    By crossIcon = By.xpath("//i[contains(@class,'iconS_Common_24 icon_close pageComponent')]");
    
    
    public boolean isAmenitiesHeadingVisible() {
        return wait.visible(amenitiesHeading).isDisplayed();
    }

    public boolean isSubHeadingVisible() {
        return wait.visible(amenitiesSubHeading).isDisplayed();
    }

    public boolean isViewAllVisible() {
        return wait.visible(viewAllBtn).isDisplayed();
    }

    public void clickViewAll() {
        WebElement el = wait.visible(viewAllBtn);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", el);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
    }

    public boolean isAmenitiesDetailsVisible() {
        return wait.visible(amenitiesDetails).isDisplayed();
    }

    public boolean isAmenitiesDetailsSoft() {
        try {
            return wait.visible(amenitiesDetails).isDisplayed();
        } catch (Exception e) {
            System.out.println("Amenities details not found (alpha panel may not be active): " + e.getMessage());
            return false;
        }
    }

    public boolean isViewNumberVisible() {
        return wait.visible(viewNumberBtn).isDisplayed();
    }

    public void clickCrossIcon() {
        wait.click(crossIcon);
    }
    
     public void clickSubmit() {
    	 wait.click(crossIcon);
     }
     
     
  // Amenities
     By amenitiesHeading1 = By.xpath("//*[contains(text(),'Amenities')]");

     // Floor Plan heading — match "Floor Plan" exactly (two words) to avoid "Floor Area", "Ground Floor" etc.
     By floorPlanHeading = By.xpath("//*[contains(text(),'Floor Plan') or contains(text(),'floor plan')]");

     // Floor Plan Detail Layer locators
     By floorPlanDetailTitle  = By.xpath("(//div[@data-testid='FLOOR_PLAN_DETAIL_LAYER_TITLE'])[2]");
     By floorPlanDetailShare  = By.xpath("(//div[@data-testid='FLOOR_PLAN_DETAIL_LAYER_SHARE'])[2]");
     By whatsAppOption        = By.xpath("(//div[contains(text(),'WhatsApp')])[2]");

     // Expand Floor Plan — arrows-out-simple icon is the expand/zoom button for floor plan
     By expandFloorPlan = By.xpath("//*[@data-testid='phosphor-react-native-arrows-out-simple']");

     // Charges inside overlay
     By chargesText = By.xpath("(//div[contains(text(),'charges')])[2]");

     // Floor Plan Detail Layer — additional locators
     By viewNumberBtn2    = By.xpath("(//div[contains(text(),'View Number')])[2]");
     By callBtn           = By.xpath("(//div[@id='CALL'])[2]");
     By floorPlanCharges  = By.xpath("//div[@data-testid='FLOOR_PLAN_DETAIL.TUPLE.CHARGES']");
     By bedroomsText      = By.xpath("//div[contains(text(),'Bedrooms')]");
     By areaText          = By.xpath("(//div[contains(text(),'Area')])[4]");
     public void scrollToBottom() {

    	    JavascriptExecutor js = (JavascriptExecutor) driver;

    	    for (int i = 0; i < 10; i++) {
    	        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

    	        try {
    	            Thread.sleep(1500);
    	        } catch (Exception ignored) {}
    	    }
    	}
     
     public boolean isFloorPlanVisible() {
    	    // Try card element (no wait — DOM presence check only)
    	    try {
    	        return driver.findElement(By.xpath("//*[@data-testid='FLOOR_PLAN_AREA_TYPE_OPTION']")).isDisplayed();
    	    } catch (Exception ignored) {}
    	    // Try Floor Plan heading text variants
    	    try {
    	        return driver.findElement(By.xpath(
    	            "//*[contains(text(),'Floor Plan') or contains(text(),'floor plan') or contains(text(),'Floor Plans')]"
    	        )).isDisplayed();
    	    } catch (Exception ignored) {}
    	    // Try class-based fallback
    	    try {
    	        return driver.findElement(By.xpath(
    	            "//*[contains(@class,'floorPlan') or contains(@class,'floor-plan') or contains(@class,'FloorPlan')]"
    	        )).isDisplayed();
    	    } catch (Exception e) {
    	        return false;
    	    }
    	}

//    	public boolean isFloorPlanSubHeadingVisible() {
//    	    return wait.visible(floorPlanSubHeading).isDisplayed();
//    	}

//    	public void clickExpandFloorPlan() {
//    	    WebElement element = wait.visible(expandFloorPlan);
//
//    	    JavascriptExecutor js = (JavascriptExecutor) driver;
//    	    js.executeScript("arguments[0].scrollIntoView({block:'center'});", element);
//
//    	    element.click();
//    	}
    	
    	public boolean isChargesVisible() {
    	    return wait.visible(chargesText).isDisplayed();
    	}

    	public void clickCharges() {
    	    wait.click(chargesText);
    	}
    	
    	public void closePopupIfPresent() {
    	    String[] closeXpaths = {
    	        "//i[contains(@class,'icon_close')]",
    	        "//i[contains(@class,'close')]",
    	        "//*[@data-label='POPUP.CLOSE' or @data-label='CLOSE' or @data-label='DISMISS']",
    	        "//*[@data-testid='CLOSE_BUTTON' or @data-testid='close-button']",
    	        "//button[contains(@aria-label,'close') or contains(@aria-label,'Close')]",
    	        "//*[contains(@class,'modal') or contains(@class,'overlay') or contains(@class,'popup')]" +
    	            "//*[contains(@class,'close') or contains(@class,'cross') or contains(@class,'dismiss')]",
    	        "//*[contains(@class,'appDownload') or contains(@class,'app-download')]" +
    	            "//*[contains(@class,'close') or contains(@class,'cross')]",
    	        "//*[contains(@class,'nudge') or contains(@class,'Nudge')]" +
    	            "//*[contains(@class,'close')]"
    	    };
    	    for (String xpath : closeXpaths) {
    	        try {
    	            java.util.List<org.openqa.selenium.WebElement> els = driver.findElements(By.xpath(xpath));
    	            for (org.openqa.selenium.WebElement el : els) {
    	                if (el.isDisplayed()) {
    	                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
    	                    try { Thread.sleep(400); } catch (Exception ignored) {}
    	                    break;
    	                }
    	            }
    	        } catch (Exception ignored) {}
    	    }
    	    // ESC to dismiss any remaining modal
    	    try {
    	        driver.findElement(By.tagName("body"))
    	              .sendKeys(org.openqa.selenium.Keys.ESCAPE);
    	    } catch (Exception ignored) {}
    	}
    	
    	public void clickExpandFloorPlan() {
    	    JavascriptExecutor js = (JavascriptExecutor) driver;
    	    // Try card element first (alpha experiment feature)
    	    try {
    	        WebElement floorCard = driver.findElement(By.xpath("//*[@data-testid='FLOOR_PLAN_AREA_TYPE_OPTION']"));
    	        js.executeScript("arguments[0].scrollIntoView({block:'center'});", floorCard);
    	        try { Thread.sleep(500); } catch (Exception ignored) {}
    	        new org.openqa.selenium.interactions.Actions(driver).moveToElement(floorCard).perform();
    	        try { Thread.sleep(500); } catch (Exception ignored) {}
    	        js.executeScript("arguments[0].click();", floorCard);
    	        try { Thread.sleep(2000); } catch (Exception ignored) {}
    	        return;
    	    } catch (Exception ignored) {}
    	    // Fallback: click the floor plan heading/section
    	    try {
    	        WebElement heading = driver.findElement(By.xpath(
    	            "//*[contains(text(),'Floor Plan') or contains(text(),'floor plan')]"));
    	        js.executeScript("arguments[0].scrollIntoView({block:'center'});", heading);
    	        try { Thread.sleep(500); } catch (Exception ignored) {}
    	        js.executeScript("arguments[0].click();", heading);
    	        try { Thread.sleep(2000); } catch (Exception ignored) {}
    	    } catch (Exception e) {
    	        System.out.println("Floor Plan card/heading not clickable (alpha may not be active): " + e.getMessage());
    	    }
    	}

    	public boolean isFloorPlanDetailLayerTitleVisible() {
    	    try {
    	        return wait.visible(floorPlanDetailTitle).isDisplayed();
    	    } catch (Exception e) {
    	        return false;
    	    }
    	}

    	public boolean isFloorPlanDetailLayerShareVisible() {
    	    try {
    	        return wait.visible(floorPlanDetailShare).isDisplayed();
    	    } catch (Exception e) {
    	        return false;
    	    }
    	}

    	public boolean isWhatsAppVisible() {
    	    try {
    	        return wait.visible(whatsAppOption).isDisplayed();
    	    } catch (Exception e) {
    	        return false;
    	    }
    	}

    	public boolean isWhatsAppClickable() {
    	    try {
    	        new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10))
    	            .until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(whatsAppOption));
    	        return true;
    	    } catch (Exception e) {
    	        return false;
    	    }
    	}

    	public void clickWhatsApp() {
    	    WebElement el = wait.visible(whatsAppOption);
    	    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", el);
    	    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
    	}

    	public boolean isViewNumberBtn2Visible() {
    	    try { return wait.visible(viewNumberBtn2).isDisplayed(); } catch (Exception e) { return false; }
    	}

    	public boolean isCallBtnVisible() {
    	    try { return wait.visible(callBtn).isDisplayed(); } catch (Exception e) { return false; }
    	}

    	public boolean isFloorPlanChargesVisible() {
    	    try { return wait.visible(floorPlanCharges).isDisplayed(); } catch (Exception e) { return false; }
    	}

    	public boolean isBedroomsTextVisible() {
    	    try { return wait.visible(bedroomsText).isDisplayed(); } catch (Exception e) { return false; }
    	}

    	public boolean isAreaTextVisible() {
    	    try { return wait.visible(areaText).isDisplayed(); } catch (Exception e) { return false; }
    	}
    	
//    	public void scrollToFloorPlan() {
//
//    	    JavascriptExecutor js = (JavascriptExecutor) driver;
//
//    	    for (int i = 0; i < 8; i++) {
//
//    	        js.executeScript("window.scrollBy(0,600)");
//
//    	        try {
//    	            if (driver.findElement(floorPlanHeading).isDisplayed()) {
//    	                break;
//    	            }
//    	        } catch (Exception ignored) {}
//    	    }
    	}

