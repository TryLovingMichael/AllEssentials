package dev.michaelh.allessential.Commands.Admin;

import dev.michaelh.allessential.AllEssential;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class worldtp implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("worldtp")){
            Player player = (Player) sender;
            Player target = Bukkit.getPlayer(args[1]);

            if(args[0] == null){
                player.sendMessage(ChatColor.RED + "Please specify a world.");
            }

            if (target == null){
                player.sendMessage(ChatColor.RED + "Please specify a player.");
            }

            if(player.hasPermission("ae.admin.worldtp")){
                player.sendMessage(ChatColor.GREEN + "Successfully teleported to " + args[0]);
                player.teleport(Bukkit.getWorld(args[0]).getSpawnLocation());
            } else {
                player.sendMessage(AllEssential.denyMessage);
            }
        }

        return false;
    }


}
