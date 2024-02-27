package space.aurillium.discordchat.commands;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import space.aurillium.discordchat.DiscordChat;

public class DiscordCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

    	if (args.length < 1) {
    		sender.sendMessage(ChatColor.RED + "You must specify either 'on' or 'off'.");
    		return true;
    	}
		OfflinePlayer subject = null;
		if (args.length > 1) {
			String name = args[1];
			if (sender.hasPermission("discordchat.manage_players")) {
				for (OfflinePlayer player : DiscordChat.server.getOfflinePlayers()) {
					if (player.getName().equalsIgnoreCase(name)) {
						subject = player;
						break;
					}
				}
				if (subject == null) {
					sender.sendMessage(ChatColor.RED + "Player '" + name + "' was not found.");
					return true;
				}
			} else {
				if (sender.getName().equalsIgnoreCase(name)) {
					// The console has all permissions and so cannot end up here
					subject = (Player)sender;
				} else {
					sender.sendMessage(ChatColor.RED + "You do not have permission to modify chat settings for other players.");
					return true;
				}
			}
		} else {
			if (sender instanceof Player) {
				// Rust wouldn't need that cast...
				subject = (Player)sender;
			} else {
				sender.sendMessage(ChatColor.RED + "You must specify a player.");
			}
		}

    	if (args[0].equalsIgnoreCase("on")) {
    		if (DiscordChat.optOutChat.contains(subject)) {
    			DiscordChat.optOutChat.remove(subject);
    			sender.sendMessage("Turned on the chat for " + subject.getName() + "!");
    		} else {
    			sender.sendMessage(ChatColor.RED + "The chat was already on.");
    		}
    	} else if (args[0].equalsIgnoreCase("off")) {
    		if (DiscordChat.optOutChat.contains(subject)) {
    			sender.sendMessage(ChatColor.RED + "The chat was already off.");
    		} else {
    			DiscordChat.optOutChat.add(subject);
    			sender.sendMessage("Turned off the chat for " + subject.getName() + "!");
    		}
    	} else {
			sender.sendMessage(ChatColor.RED + "You must specify either 'on' or 'off'.");
		}
    	return true;
    }
}
