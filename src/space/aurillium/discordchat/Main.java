package space.aurillium.discordchat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	public static JavaPlugin plugin;
	public static Random random = new Random();
	
	public static List<String> optOutChat;
	public static List<String> webhookURLs;
	
	@Override
	public void onEnable() {
		plugin = JavaPlugin.getPlugin(Main.class);

		optOutChat = this.getConfig().getStringList("opt_out_chat");
		if (optOutChat == null) {
			optOutChat = new ArrayList<String>();
		}
		webhookURLs = this.getConfig().getStringList("webhook_urls");
		if (webhookURLs == null) {
			webhookURLs = new ArrayList<String>();
		}

		DiscordChatlinkTask.discordMessages = this.getConfig().getBoolean("chat_master");
		if (DiscordChatlinkTask.discordMessages == null) {
			DiscordChatlinkTask.discordMessages = true;
		}
		
		// Register Commands
		this.getCommand("chat").setExecutor(new ChatCommand());
		
		// Register Event Handler
		Bukkit.getPluginManager().registerEvents(new EventListener(), this);
	}
	
	@Override
	public void onDisable() {
		this.getConfig().set("opt_out_chat", optOutChat);
		this.getConfig().set("webhook_urls", webhookURLs);
		this.getConfig().set("chat_master", DiscordChatlinkTask.discordMessages);
		this.saveConfig();
	}
	
	@Override
    public boolean onCommand(CommandSender sender,
                             Command command,
                             String label,
                             String[] args) {
        if (command.getName().equalsIgnoreCase("masterchatoff")) {
        	if (sender instanceof Player) {
        		sender.sendMessage("Players cannot use this command.");
        		return false;
        	}
        	DiscordChatlinkTask.discordMessages = false;
        	return true;
        } else if (command.getName().equalsIgnoreCase("masterchaton")) {
        	if (sender instanceof Player) {
        		sender.sendMessage("Players cannot use this command.");
        		return false;
        	}
        	DiscordChatlinkTask.discordMessages = true;
        	return true;
        }
        return false;
    }
}
