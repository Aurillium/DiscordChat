package space.aurillium.discordchat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import javax.net.ssl.HttpsURLConnection;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.JSONArray;
import org.json.JSONObject;

public class DiscordChatlinkTask extends BukkitRunnable {
	
	public static void sendDiscordString(Player p, String message) {
		JSONObject obj = new JSONObject();
		obj.put("content", message);
		obj.put("username", p.getName());
		obj.put("avatar_url", "https://mc-heads.net/avatar/" + p.getUniqueId() + "/256");
		
		sendDiscord(p, obj.toString());
	}
	public static void sendDiscordEmbed(Player p, String message, Integer colour) {
		JSONObject obj = new JSONObject();
		JSONArray embedArray = new JSONArray();
		JSONObject embed = new JSONObject();

		embed.put("title", message);
		embed.put("color", colour);
		embedArray.put(embed);
		obj.put("embeds", embedArray);
		obj.put("username", p.getName());
		obj.put("avatar_url", "https://mc-heads.net/avatar/" + p.getUniqueId() + "/256");

		sendDiscord(p, obj.toString());
	}
	
	public static void sendDiscord(Player p, String jsonInputString) {
		if (!DiscordChat.config.getBoolean("master", false)) {
			return;
		}
		if (DiscordChat.optOutChat.contains(p)) {
			return;
		}
		new DiscordChatlinkTask(jsonInputString).runTaskAsynchronously(DiscordChat.plugin);
	}
	
	String jsonInputString;
	
    public DiscordChatlinkTask(String jsonData) {
        this.jsonInputString = jsonData;
    }
    
    @Override
    public void run() {
    	for (String webhook : DiscordChat.webhookURLs) {
    		try {
            	URL url = new URL(webhook);
                HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json; utf-8");
                con.setRequestProperty("Accept", "application/json");
                con.addRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.132 Safari/537.36");
                con.setDoOutput(true);

                try (OutputStream os = con.getOutputStream()) {
                    byte[] input = this.jsonInputString.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }
                try (BufferedReader br = new BufferedReader(
                		  new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
                		    StringBuilder response = new StringBuilder();
                		    String responseLine;
                		    while ((responseLine = br.readLine()) != null) {
                		        response.append(responseLine.trim());
                		    }
                		}
            } catch (Exception e) {
            	e.printStackTrace();
            }
    	}
    }
}
