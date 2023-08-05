package dev.michaelh.allessential.Commands.Admin;

import dev.michaelh.allessential.AllEssential;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class reload implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender.hasPermission("ae.admin.reloadconfig")) {
            AllEssential.instance.reloadConfig();
            sender.sendMessage(ChatColor.GREEN + "AllEssential config reloaded!");
        } else {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
        }
        return true;
    }
}
