
package utils;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiUtils {

    public static int makeAlphaUser(String userId) {
        return makeAlphaUser(userId, "DESKTOP");
    }

    public static int makeAlphaUser(String userId, String platform) {
        try {
            String apiPlatform = "msite".equalsIgnoreCase(platform) ? "MOBILESITE" : "DESKTOP";
            URL url = new URL("http://10.20.77.66/aggregator-common-service/override-xAB");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setConnectTimeout(5000);
            conn.setReadTimeout(10000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String json = "[{\"userId\":\"" + userId + "\",\"state\":\"Y\",\"experimentName\":\"APTMAPXID\",\"platform\":\"" + apiPlatform + "\"}]";
            System.out.println("Alpha API request [" + apiPlatform + "] userId=" + userId + " payload=" + json);

            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes());
            os.flush();

            int code = conn.getResponseCode();
            System.out.println("Alpha API response: " + code);
            return code;

        } catch (Exception e) {
            System.out.println("Alpha API unavailable: " + e.getMessage());
            return -1;
        }
    }
}
