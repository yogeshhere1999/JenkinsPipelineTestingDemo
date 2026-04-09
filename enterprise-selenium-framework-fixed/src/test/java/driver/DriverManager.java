package driver;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;




public class DriverManager {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static void initDriver(String platform) {

        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        if ("msite".equalsIgnoreCase(platform)) {

            Map<String, Object> deviceMetrics = new HashMap<>();
            deviceMetrics.put("width", 320);
            deviceMetrics.put("height", 580);
            deviceMetrics.put("pixelRatio", 2.0);

            Map<String, Object> mobileEmulation = new HashMap<>();
            mobileEmulation.put("deviceMetrics", deviceMetrics);
            mobileEmulation.put("userAgent",
                "Mozilla/5.0 (Linux; Android 11; Pixel 5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.91 Mobile Safari/537.36");

            options.setExperimentalOption("mobileEmulation", mobileEmulation);

            System.out.println("Running in mSite mode");
        }

        driver.set(new ChromeDriver(options)); // ✅ correct
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        driver.get().quit();
        driver.remove();
    }
}

//public static void initDriver() {
//
//    WebDriverManager.chromedriver().setup();
//
//    ChromeOptions options = new ChromeOptions();
//    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
//    String platform = ConfigReader.get("platform");
//
//    if ("msite".equalsIgnoreCase(platform)) {
//
//        Map<String, Object> mobileEmulation = new HashMap<>();
//        mobileEmulation.put("deviceName", "Pixel 5");
//
//        options.setExperimentalOption("mobileEmulation", mobileEmulation);
//
//        System.out.println("Running in mSite mode");
//    }
//
//    driver.set(new ChromeDriver(options));
//    
//    public static WebDriver getDriver() {
//        return driver.get();
//    }
//    
//    public static void quitDriver() {
//        driver.get().quit();
//        driver.remove();
//    }
//}

//public static void initDriver() {
//	
//	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
//
//    WebDriverManager.chromedriver().setup();
//     
//
//    String platform = ConfigReader.get("platform");
//
//    ChromeOptions options = new ChromeOptions();
//    options.addArguments("--disable-notifications");
//    
//    
//    
//    Map<String, Object> deviceMetrics = new HashMap<>();
//    deviceMetrics.put("width", 390);
//    deviceMetrics.put("height", 844);
//    deviceMetrics.put("pixelRatio", 3.0);
//
//    Map<String, Object> mobileEmulation = new HashMap<>();
//    mobileEmulation.put("deviceMetrics", deviceMetrics);
//    mobileEmulation.put("userAgent",
//            "Mozilla/5.0 (iPhone; CPU iPhone OS 14_0 like Mac OS X)");
//
//    options.setExperimentalOption("mobileEmulation", mobileEmulation);
//
//    // 🔥 FORCE MOBILE MODE
//    
//
//    driver.set(new ChromeDriver(options));
//}