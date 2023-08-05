package dev.michaelh.allessential.Commands.Moderation;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class setspawn implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if(player.hasPermission("ae.mod.setspawn")){
                player.sendMessage(ChatColor.GREEN + "Spawn set!");
                player.getWorld().setSpawnLocation(player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ());
        } else {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
        }

        return false;
    }
}
