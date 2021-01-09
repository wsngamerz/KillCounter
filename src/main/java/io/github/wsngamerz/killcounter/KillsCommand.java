package io.github.wsngamerz.killcounter;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

// TODO: 02/08/2018 Add "/kills top" to list the top players

public class KillsCommand implements CommandExecutor {

    private DataManager dm = DataManager.getManager();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {

            Player player = (Player) sender;
            KillGUI killGUI = new KillGUI();

            if (args.length == 0) {

                // Command "/kills"
                killGUI.mainMenu(player);

            } else if (args[0].equals("passive")) {

                // Command "/kills passive"
                killGUI.passiveMenu(player);

            } else if (args[0].equals("neutral")) {

                // Command "/kills neutral"
                killGUI.neutralMenu(player);

            } else if (args[0].equals("hostile")) {

                // Command "/kills hostile"
                killGUI.hostileMenu(player);

            } else if (args[0].equals("actionbar")) {

                String playerName = player.getDisplayName();
                String actionbarValue = dm.getUserString(playerName, "options", "actionbar");

                if (args[1].equals("on")) {
                    // Command "/kills actionbar on"

                    if (actionbarValue.equals("off")) {
                        sender.sendMessage("Actionbar enabled!");
                        dm.setUserString(playerName, "options", "actionbar", "on");
                    } else {
                        sender.sendMessage("Actionbar is already enabled!");
                    }

                } else if (args[1].equals("off")) {
                    // Command "/kills actionbar off"

                    if (actionbarValue.equals("on")) {
                        sender.sendMessage("Actionbar disabled!");
                        dm.setUserString(playerName, "options", "actionbar", "off");
                    } else {
                        sender.sendMessage("Actionbar is already disabled!");
                    }

                } else {
                    // Unknown kills actionbar command
                    sender.sendMessage("Unknown kills actionbar option!");
                }
            }

        } else {
            sender.sendMessage("Only players can use /" + label);
        }
        return true;
    }
}
