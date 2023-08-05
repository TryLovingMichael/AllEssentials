package dev.michaelh.allessential.Commands.QOL;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class sun implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if(player.hasPermission("ae.qol.sun")){
            player.getWorld().setStorm(false);
            player.getWorld().setThundering(false);
            player.sendMessage(ChatColor.YELLOW + "It is now sunny!");
        } else {
            player.sendMessage("You do not have permission to use this command!");
        }

        return true;
    }


}
