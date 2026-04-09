
package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtils {

    private WebDriver driver;

    public WaitUtils(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement visible(By locator) {
        int timeout = Integer.parseInt(ConfigReader.get("timeout"));
        return new WebDriverWait(driver, Duration.ofSeconds(timeout))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void click(By locator) {
        visible(locator).click();
    }

    public void type(By locator, String value) {
        WebElement el = visible(locator);
        el.clear();
        el.sendKeys(value);
    }
}
