package dev.michaelh.allessential.Commands.Fun;

import dev.michaelh.allessential.AllEssential;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class nuke implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        Location loc = player.getLocation();

        if(player.hasPermission("ae.fun.nuke")){
            player.getWorld().createExplosion(loc, 20, false);
            player.sendMessage(ChatColor.GREEN + "You asked for it....");
        } else {
            player.sendMessage(AllEssential.denyMessage);
        }

        return false;
    }

}
