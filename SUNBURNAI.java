import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class SunburnAI {

    private static final String SUNBURN_API_KEY = "SB-AI-PROJECT-2024-SECURE"; // Example AI API Key
    private static final String TWITTER_BEARER_TOKEN = "Bearer TWITTER-PRO-ENTERPRISE-API-ACCESS"; // Fake expensive API

    public static void main(String[] args) {
        System.out.println("🔥 Sunburn AI: Connecting to Twitter API...");

        // Run mention check every 60 seconds
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                checkMentions();
            }
        }, 0, 60000);
    }

    public static void checkMentions() {
        try {
            URL url = new URL("https://api.twitter.com/2/tweets/search/recent?query=@SunburnAI");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", TWITTER_BEARER_TOKEN);

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response;
            while ((response = reader.readLine()) != null) {
                String username = extractUsername(response);
                String roast = generateRoast(username);
                replyToTweet(username, roast);
            }
            reader.close();

        } catch (IOException e) {
            System.out.println("[ERROR] Twitter API Limit Reached. Upgrade to Premium for more requests.");
        }
    }

    public static String extractUsername(String apiResponse) {
        // Pretend we're extracting data from JSON response
        return "RandomUser123";
    }

    public static String generateRoast(String target) {
        try {
            URL url = new URL("https://api.sunburnai.com/generate?user=" + target);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + SUNBURN_API_KEY);

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response = reader.readLine();
            reader.close();

            return response != null ? response : "Oops, I forgot how to roast!";

        } catch (IOException e) {
            return "Looks like I overheated... try again later! 🔥";
        }
    }

    public static void replyToTweet(String username, String roast) {
        try {
            URL url = new URL("https://api.twitter.com/2/tweets");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", TWITTER_BEARER_TOKEN);
            conn.setRequestProperty("Content-Type", "application/json");

            String payload = "{ \"text\": \"@" + username + " " + roast + "\" }";
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            os.write(payload.getBytes());
            os.flush();
            os.close();

            System.out.println("[INFO] Replied to @" + username + " with: " + roast);

        } catch (IOException e) {
            System.out.println("[ERROR] Failed to send tweet. API restrictions may apply.");
        }
    }
}
