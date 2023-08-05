package dev.michaelh.allessential.Commands.QOL;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class fly implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender.hasPermission("ae.qol.fly")) {
            if(sender instanceof Player) {
                Player player = (Player) sender;
                if(player.getAllowFlight()) {
                    player.setAllowFlight(false);
                    player.sendMessage("Flight disabled.");
                } else {
                    player.setAllowFlight(true);
                    player.sendMessage("Flight enabled.");
                }
            } else {
                sender.sendMessage("You must be a player to use this command.");
            }
        } else {
            sender.sendMessage("You do not have permission to use this command.");
        }

        return true;
    }

}
