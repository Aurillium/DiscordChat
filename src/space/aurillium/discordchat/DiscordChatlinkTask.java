package space.aurillium.discordchat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class DiscordChatlinkTask extends BukkitRunnable {
	
	public static Boolean discordMessages = true;
	
	public static void sendDiscordString(Player p, String message) {
		String jsonInputString = "{"
        		+ "\"content\": \"" + message.replace("\\", "\\\\").replace("\"", "\\\"") + "\","
        		+ "\"embeds\": null,"
        		+ "\"username\": \"" + p.getDisplayName() + "\","
        		+ "\"avatar_url\": \"https://crafatar.com/avatars/" + p.getUniqueId().toString() + "?size=256&default=MHF_Steve&overlay\""
        		+ "}";
		
		sendDiscord(p, jsonInputString);
	}
	public static void sendDiscordEmbed(Player p, String message, Integer colour) {
		String jsonInputString = "{"
        		+ "\"content\": null,"
        		+ "\"embeds\": ["
        		+ "{"
        		+ "\"title\": \"" + message.replace("\\", "\\\\").replace("\"", "\\\"") + "\","
        		+ "\"color\": " + colour.toString()
        		+ "}"
        		+ "],"
        		+ "\"username\": \"" + p.getDisplayName() + "\","
        		+ "\"avatar_url\": \"https://crafatar.com/avatars/" + p.getUniqueId().toString() + "?size=256&default=MHF_Steve&overlay\""
        		+ "}";
		
		sendDiscord(p, jsonInputString);
	}
	
	public static void sendDiscord(Player p, String jsonInputString) {
		if (!discordMessages) {
			return;
		}
		if (Main.optOutChat.contains(p.getUniqueId().toString())) {
			return;
		}
		new DiscordChatlinkTask(jsonInputString).runTaskAsynchronously(Main.plugin);
	}
	
	String jsonInputString;
	
    public DiscordChatlinkTask(String jsonData) {
        this.jsonInputString = jsonData;
    }
    
    @Override
    public void run() {
    	for (String webhook : Main.webhookURLs) {
    		try {
            	URL url = new URL(webhook);
                HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json; utf-8");
                con.setRequestProperty("Accept", "application/json");
                con.addRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.132 Safari/537.36");
                con.setDoOutput(true);
                
                try (OutputStream os = con.getOutputStream()) {
                    byte[] input = this.jsonInputString.getBytes("utf-8");
                    os.write(input, 0, input.length);			
                }
                try(BufferedReader br = new BufferedReader(
                		  new InputStreamReader(con.getInputStream(), "utf-8"))) {
                		    StringBuilder response = new StringBuilder();
                		    String responseLine = null;
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
