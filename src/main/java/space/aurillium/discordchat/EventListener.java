package space.aurillium.discordchat;

import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementDisplay;
import org.bukkit.advancement.AdvancementDisplayType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
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
	
	@EventHandler
    public void onAdvancement(PlayerAdvancementDoneEvent event) {
        AdvancementDisplay display = event.getAdvancement().getDisplay();
        if (display != null) {
            AdvancementDisplayType type = display.getType();
            Player player = event.getPlayer();

            String message = player.getName();
            message += switch (type) {
                case TASK -> " has made the advancement [";
                case GOAL -> " has reached the goal [";
                case CHALLENGE -> " has completed the challenge [";
            };
            message += display.getTitle() + "]";

            DiscordChatlinkTask.sendDiscordEmbed(player, message, 49919);
        }
	}
}