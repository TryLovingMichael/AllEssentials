package dev.michaelh.allessential.Commands.Moderation;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class ban implements CommandExecutor {

        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (player.hasPermission("ae.mod.ban")) {
                    if (args.length < 1) {
                        player.sendMessage(ChatColor.RED + "Usage: /ban <player> [reason]");
                        return true;
                    }
                    String playerName = args[0];
                    Player target = Bukkit.getPlayer(playerName);
                    if (target == null) {
                        player.sendMessage(ChatColor.RED + "Player not found.");
                        return true;
                    }
                    String reason = "Banned by " + player.getName();
                    if (args.length > 1) {
                        reason = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
                    }
                    target.kickPlayer(ChatColor.RED + "You have been banned from the server: " + reason);
                    Bukkit.getServer().broadcastMessage(ChatColor.RED + player.getName() + " has banned " + target.getName() + " for " + reason);
                    return true;
                } else {
                    player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
                    return true;
                }
            } else {
                sender.sendMessage(ChatColor.RED + "You must be a player to use this command.");
                return true;
            }
        }
    }

