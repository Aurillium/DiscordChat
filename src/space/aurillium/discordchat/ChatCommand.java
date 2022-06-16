package space.aurillium.discordchat;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    	Player self = ((Player)sender);
    	String stringID = self.getUniqueId().toString();
    	if (args.length < 1) {
    		sender.sendMessage(ChatColor.RED + "You must specify either 'on' or 'off'");
    		return true;
    	}
    	if (args[0].equalsIgnoreCase("on")) {
    		if (Main.optOutChat.contains(stringID)) {
    			Main.optOutChat.remove(stringID);
    			sender.sendMessage("Turned on the chat!");
    		} else {
    			sender.sendMessage(ChatColor.RED + "You already had the chat on.");
    		}
    	} else if (args[0].equalsIgnoreCase("off")) {
    		if (Main.optOutChat.contains(stringID)) {
    			sender.sendMessage(ChatColor.RED + "You already had the chat off.");
    		} else {
    			Main.optOutChat.add(stringID);
    			sender.sendMessage("Turned off the chat!");
    		}
    	}
    	return true;
    }
}
