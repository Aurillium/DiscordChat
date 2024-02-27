package space.aurillium.discordchat.autocompleters;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import space.aurillium.discordchat.DiscordChat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MasterAutocomplete implements TabCompleter {
    private static final List<String> VALID = Arrays.asList("on", "off");

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> list = new ArrayList<>();
        if (args.length == 1) {
            StringUtil.copyPartialMatches(args[0], VALID, list);
            Collections.sort(list);
        }
        return list;
    }
}
