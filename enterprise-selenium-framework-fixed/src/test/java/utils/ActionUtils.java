package utils;




import org.openqa.selenium.*;
import org.openqa.selenium.JavascriptExecutor;

public class ActionUtils {

    public static void scrollUntilVisible(WebDriver driver, By locator) {

        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Reset to top so we don't miss elements above the current scroll position
        js.executeScript("window.scrollTo(0, 0)");

        for (int i = 0; i < 20; i++) {

            try {
                WebElement element = driver.findElement(locator);

                if (element.isDisplayed()) {
                    js.executeScript("arguments[0].scrollIntoView({block:'center'});", element);
                    return;
                }

            } catch (Exception ignored) {}

            js.executeScript("window.scrollBy(0,600)");

            try { Thread.sleep(800); } catch (Exception ignored) {}
        }

        throw new RuntimeException("Element not found after scrolling: " + locator);
    }
    
    public static void safeClick(WebDriver driver, By locator) {

        for (int i = 0; i < 3; i++) {

            try {
                WebElement element = driver.findElement(locator);

                ((JavascriptExecutor) driver)
                        .executeScript("arguments[0].scrollIntoView({block:'center'});", element);

                element.click();
                return;

            } catch (ElementClickInterceptedException e) {

                // 🔥 Try closing overlay
                try {
                    driver.findElement(By.xpath("//i[contains(@class,'icon_close')]")).click();
                } catch (Exception ignored) {}

            } catch (Exception e) {

                // Fallback JS click
                try {
                    WebElement element = driver.findElement(locator);
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
                    return;
                } catch (Exception ignored) {}
            }
        }

        throw new RuntimeException("Failed to click element: " + locator);
    }
}