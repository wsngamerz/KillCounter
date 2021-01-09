package io.github.wsngamerz.killcounter;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Arrays;
import java.util.List;

public class KillCounterTabComplete implements TabCompleter {

    private static List<String> ROOT_OPTIONS = Arrays.asList("hostile", "neutral", "passive", "actionbar");
    private static List<String> ACTIONBAR_OPTIONS = Arrays.asList("on", "off");

    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 1) {
            return ROOT_OPTIONS;
        } else if (args[0].equals("actionbar")) {
            return ACTIONBAR_OPTIONS;
        } else {
            return null;
        }
    }

}
