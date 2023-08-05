package dev.michaelh.allessential.Commands.Moderation;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class kick implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("kick") && args.length >= 1) {
            if (sender instanceof Player && !sender.hasPermission("ae.mod.kick")) {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
                return true;
            }

            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(ChatColor.RED + "Player not found.");
                return true;
            }
            String reason = "Kicked by an administrator.";
            if (args.length > 1) {
                reason = String.join(" ", args).substring(args[0].length() + 1);
            }
            target.kickPlayer(ChatColor.RED + "You have been kicked from the server. Reason: " + reason);
            Bukkit.broadcast(ChatColor.RED + target.getName() + " has been kicked from the server. Reason: " + reason, "ae.mod.kick");
            return true;
        }
        return false;
    }

}
