package dev.michaelh.allessential.Commands.QOL;

import dev.michaelh.allessential.AllEssential;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.bukkit.Bukkit.getServer;

public class reportCommand implements CommandExecutor {

    public static Map<UUID, List<String>> reports;
    private String webhookUrl = AllEssential.instance.getConfig().getString("webhook_url");

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("report")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "Only players can report!");
                return true;
            }
            if (args.length < 2) {
                sender.sendMessage(ChatColor.RED + "Usage: /report <player> <reason>");
                return true;
            }
            Player reportedPlayer = Bukkit.getPlayer(args[0]);
            if (reportedPlayer == null) {
                sender.sendMessage(ChatColor.RED + "Player not found!");
                return true;
            }
            if (reportedPlayer.equals(sender)) {
                sender.sendMessage(ChatColor.RED + "You cannot report yourself!");
                return true;
            }
            String reason = String.join(" ", args).substring(args[0].length() + 1);
            addReport(reportedPlayer.getUniqueId(), sender.getName(), reason);
            sender.sendMessage(ChatColor.GREEN + "Report submitted!");
            notifyModerators();
            return true;

        } else if (cmd.getName().equalsIgnoreCase("reports")) {
            if (!sender.hasPermission("ae.mod.reports")) {
                sender.sendMessage(ChatColor.RED + "You do not have permission to view reports!");
                return true;
            }
            if (reports.isEmpty()) {
                sender.sendMessage(ChatColor.GREEN + "No reports to show!");
                return true;
            }
            sender.sendMessage(ChatColor.GREEN + "Reports:");
            for (Map.Entry<UUID, List<String>> entry : reports.entrySet()) {
                String playerName = Bukkit.getOfflinePlayer(entry.getKey()).getName();
                sender.sendMessage(ChatColor.YELLOW + playerName + ":");
                for (String report : entry.getValue()) {
                    sender.sendMessage(ChatColor.GRAY + "- " + report);
                }
            }
            return true;
        }
        return false;
    }

    private void addReport(UUID reportedPlayer, String reporter, String reason) {
        if (!reports.containsKey(reportedPlayer)) {
            reports.put(reportedPlayer, new ArrayList<>());
        }
        List<String> playerReports = reports.get(reportedPlayer);
        playerReports.add(reporter + ": " + reason);
        reports.put(reportedPlayer, playerReports);
    }

    private void notifyModerators() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("ae.mod.reportnotify")) {
                player.sendMessage(ChatColor.YELLOW + "[REPORT] There's a new report waiting for you to review!");
                sendWebhookMessage("```[REPORT] There's a new report waiting for Staff to review! Reported by: " + player.getName() + "!```");
            }
        }
        ConsoleCommandSender console = Bukkit.getConsoleSender();
        console.sendMessage(ChatColor.YELLOW + "[REPORT] There's a new report waiting for moderators to review!");
    }


    private void sendWebhookMessage(String message) {
        try {
            URL url = new URL(webhookUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            String payload = "{\"content\":\"" + message + "\"}";
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(payload.getBytes());
            outputStream.flush();
            outputStream.close();
            int responseCode = connection.getResponseCode();
            if (responseCode != 204) {
                getServer().getLogger().warning("Failed to send webhook message: " + responseCode);
            }
        } catch (Exception e) {
            getServer().getLogger().warning("Failed to send webhook message: " + e.getMessage());
        }
    }
}
