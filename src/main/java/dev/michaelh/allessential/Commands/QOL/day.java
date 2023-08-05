package dev.michaelh.allessential.Commands.QOL;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class day implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        if(player.hasPermission("ae.qol.day")) {
            sender.getServer().getWorld(player.getWorld().getName()).setTime(0);
            sender.sendMessage("§aTime set to day!");
        } else {
            sender.sendMessage("§cYou do not have permission to use this command!");
        }

        return true;
    }


}
