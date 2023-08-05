package dev.michaelh.allessential.Commands.QOL;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class feed implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender.hasPermission("ae.qol.feed")) {
            if(sender instanceof Player) {
                Player player = (Player) sender;
                if(player.getFoodLevel() == 20) {
                    player.sendMessage("You are already full.");
                } else {
                    player.setFoodLevel(20);
                    player.sendMessage("You have been fed.");
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
