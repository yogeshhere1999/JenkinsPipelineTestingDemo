
package flows;

import org.openqa.selenium.WebDriver;
import utils.ApiUtils;
import utils.CookieUtils;

public class AlphaUserFlow {

    WebDriver driver;

    public AlphaUserFlow(WebDriver driver) {
        this.driver = driver;
    }

    public void makeUserAlpha() {
        makeUserAlpha("DESKTOP");
    }

    public void makeUserAlpha(String platform) {
        String userId = CookieUtils.getGoogleId(driver);
        System.out.println("Alpha user setup — userId=" + userId + " platform=" + platform);

        int response = ApiUtils.makeAlphaUser(userId, platform);

        if (response == 200 || response == 204) {
            System.out.println("Alpha user activated successfully (HTTP " + response + ")");
            // Wait briefly for server-side experiment activation to propagate
            try { Thread.sleep(3000); } catch (Exception ignored) {}
        } else {
            System.out.println("Alpha user API returned " + response + " — continuing anyway");
        }
    }
}