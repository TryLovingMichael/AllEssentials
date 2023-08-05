package dev.michaelh.allessential.Commands.Moderation;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class tempban implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Check if the command sender is a player and has the permission node "ae.mod.tempban"
        if (!(sender instanceof Player) || !sender.hasPermission("ae.mod.tempban")) {
            sender.sendMessage("You do not have permission to use this command.");
            return true;
        }

        // Check if the correct number of arguments were provided
        if (args.length < 2) {
            sender.sendMessage("Usage: /tempban <player> <duration> [reason]");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        // Check if the target player is online
        if (target == null) {
            sender.sendMessage("Player not found.");
            return true;
        }

        long duration = parseDuration(args[1]);

        // Check if the duration is valid
        if (duration <= 0) {
            sender.sendMessage("Invalid duration.");
            return true;
        }

        String reason = (args.length > 2) ? String.join(" ", args).substring(args[0].length() + args[1].length() + 2) : "No reason specified";

        Bukkit.getBanList(BanList.Type.NAME).addBan(target.getName(), reason, new java.util.Date(System.currentTimeMillis() + duration), sender.getName());

        target.kickPlayer("You have been temporarily banned.\nReason: " + reason + "\nDuration: " + formatDuration(duration));

        return true;
    }

    private long parseDuration(String durationString) {
        try {
            return Long.parseLong(durationString) * 1000;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private String formatDuration(long duration) {
        long seconds = duration / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        if (days > 0) {
            return days + " day(s)";
        } else if (hours > 0) {
            return hours + " hour(s)";
        } else if (minutes > 0) {
            return minutes + " minute(s)";
        } else {
            return seconds + " second(s)";
        }
    }

}
