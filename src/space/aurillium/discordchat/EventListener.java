package space.aurillium.discordchat;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventListener implements Listener {
	
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        String message = event.getMessage();
        Player p = event.getPlayer();
        DiscordChatlinkTask.sendDiscordString(p, message);
    }
    
	@EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Player p = event.getPlayer();
        DiscordChatlinkTask.sendDiscordEmbed(p, event.getJoinMessage().substring(2), 49919);
    }
	
	@EventHandler
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        Player p = event.getPlayer();
        DiscordChatlinkTask.sendDiscordEmbed(p, event.getQuitMessage().substring(2), 49919);
    }
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player p = event.getEntity();
		DiscordChatlinkTask.sendDiscordEmbed(p, event.getDeathMessage(), 49919);
	}
	
	public void onAdvancement(org.bukkit.event.player.PlayerAdvancementDoneEvent event) {
		//event.getAdvancement().
	}
}