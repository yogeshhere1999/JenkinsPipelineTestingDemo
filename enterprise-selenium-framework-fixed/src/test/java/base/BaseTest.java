
package base;

import driver.DriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import utils.ConfigReader;
import utils.WaitUtils;

public class BaseTest {

	@BeforeMethod(alwaysRun = true)
	@Parameters({"platform"})
	public void setUp(@Optional("desktop") String platform) {

	    try {
	        System.out.println("=== Starting Setup ===");

	        DriverManager.initDriver(platform);
	        System.out.println("Driver initialized");

	        driver = DriverManager.getDriver();
	        System.out.println("Driver assigned");

	        String baseUrl = ConfigReader.get("baseUrl");
	        System.out.println("Base URL: " + baseUrl);

	        driver.get(baseUrl);

	        System.out.println("=== Setup Completed ===");

	    } catch (Exception e) {
	        e.printStackTrace();   // 🔥 THIS WILL SHOW REAL ERROR
	        throw e;
	    }
	}
	
	
    protected WebDriver driver;
    protected WaitUtils wait;

    public void BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver);   // 🔥 THIS LINE IS MUST
    }
   
    
    @AfterMethod
    public void tearDown() {
        DriverManager.quitDriver();
    }
    

    
}
