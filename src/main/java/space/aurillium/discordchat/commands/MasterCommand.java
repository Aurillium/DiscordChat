package space.aurillium.discordchat.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import space.aurillium.discordchat.DiscordChat;

public class MasterCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

    	if (args.length < 1) {
    		sender.sendMessage(ChatColor.RED + "You must specify either 'on' or 'off'.");
    		return true;
    	}

    	if (args[0].equalsIgnoreCase("on")) {
    		if (!DiscordChat.config.getBoolean("master", false)) {
				DiscordChat.config.set("master", true);
    			sender.sendMessage("Turned on the chat link!");
    		} else {
    			sender.sendMessage(ChatColor.RED + "The chat link was already on.");
    		}
    	} else if (args[0].equalsIgnoreCase("off")) {
    		if (!DiscordChat.config.getBoolean("master", false)) {
    			sender.sendMessage(ChatColor.RED + "The chat link was already off.");
    		} else {
				DiscordChat.config.set("master", false);
    			sender.sendMessage("Turned off the chat link!");
    		}
    	} else {
			sender.sendMessage(ChatColor.RED + "You must specify either 'on' or 'off'.");
		}
    	return true;
    }
}
