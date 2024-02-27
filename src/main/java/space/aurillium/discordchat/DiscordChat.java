package space.aurillium.discordchat;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import space.aurillium.discordchat.autocompleters.DiscordAutocomplete;
import space.aurillium.discordchat.autocompleters.MasterAutocomplete;
import space.aurillium.discordchat.commands.DiscordCommand;
import space.aurillium.discordchat.commands.MasterCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class DiscordChat extends JavaPlugin {
	
	public static JavaPlugin plugin;
	public static Logger logger;
	public static Server server;
	public static FileConfiguration config;
	
	public static List<OfflinePlayer> optOutChat;
	public static List<String> webhookURLs;
	//public static Translator translator;
	//public static String language;

	public void reload() {

	}

	@Override
	public void onEnable() {
		plugin = JavaPlugin.getPlugin(DiscordChat.class);
		logger = plugin.getLogger();
		server = plugin.getServer();
		config = plugin.getConfig();
		//LanguageTools languageTools = (LanguageTools) plugin.getServer().getPluginManager().getPlugin("LanguageTools");
		//translator = languageTools.getTranslator();
		//language = languageTools.getServerLanguage();

		optOutChat = new ArrayList<>();
		List<String> optOutUUIDs = this.getConfig().getStringList("opt_out_chat");
		for (String rawID : optOutUUIDs) {
			optOutChat.add(server.getOfflinePlayer(UUID.fromString(rawID)));
		}

		webhookURLs = this.getConfig().getStringList("webhook_urls");
		
		// Register commands
		this.getCommand("discord").setExecutor(new DiscordCommand());
		this.getCommand("master_discord").setExecutor(new MasterCommand());
		// Register tab complete
		this.getCommand("discord").setTabCompleter(new DiscordAutocomplete());
		this.getCommand("master_discord").setTabCompleter(new MasterAutocomplete());
		
		// Register event handler
		Bukkit.getPluginManager().registerEvents(new EventListener(), this);
	}
	
	@Override
	public void onDisable() {
		this.reloadConfig();
		List<String> rawOptOut = new ArrayList<>();
		for (OfflinePlayer player : optOutChat) {
			rawOptOut.add(player.getUniqueId().toString());
		}
		this.getConfig().set("opt_out_chat", rawOptOut);
		this.saveConfig();
	}
}
