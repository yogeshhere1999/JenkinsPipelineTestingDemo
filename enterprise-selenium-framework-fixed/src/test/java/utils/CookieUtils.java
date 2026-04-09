
package utils;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

public class CookieUtils {

    public static String getGoogleId(WebDriver driver) {

        for (int i = 0; i < 5; i++) {

            for (Cookie cookie : driver.manage().getCookies()) {

                // Match exact "_ga" cookie, not "_ga_XXXX" variants
                if (cookie.getName().equals("_ga")) {

                    String[] parts = cookie.getValue().split("\\.");
                    if (parts.length >= 2) {
                        return parts[parts.length - 2] + "." + parts[parts.length - 1];
                    } else {
                        return cookie.getValue();
                    }
                }
            }

            try {
                Thread.sleep(2000);
            } catch (Exception ignored) {}
        }

        throw new RuntimeException("GA cookie not found");
    }
}
