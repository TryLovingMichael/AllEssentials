package dev.michaelh.allessential.Commands.Economy;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

import static org.bukkit.Bukkit.getServer;

public class economyMain implements CommandExecutor {

    public static HashMap<UUID, Double> balances = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("balance")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                double balance = getBalance(player.getUniqueId());
                player.sendMessage(String.format("Your balance is %.2f", balance));
            } else {
                sender.sendMessage("Only players can use this command.");
            }
            return true;
        } else if (command.getName().equalsIgnoreCase("pay")) {
            if (sender instanceof Player) {
                Player senderPlayer = (Player) sender;
                if (args.length < 2) {
                    senderPlayer.sendMessage("Usage: /pay <player> <amount>");
                    return true;
                }
                Player receiverPlayer = getServer().getPlayer(args[0]);
                if (receiverPlayer == null) {
                    senderPlayer.sendMessage("Player not found.");
                    return true;
                }
                double amount;
                try {
                    amount = Double.parseDouble(args[1]);
                } catch (NumberFormatException e) {
                    senderPlayer.sendMessage("Invalid amount.");
                    return true;
                }
                if (amount <= 0) {
                    senderPlayer.sendMessage("Amount must be positive.");
                    return true;
                }
                if (getBalance(senderPlayer.getUniqueId()) < amount) {
                    senderPlayer.sendMessage("Insufficient funds.");
                    return true;
                }
                transfer(senderPlayer.getUniqueId(), receiverPlayer.getUniqueId(), amount);
                senderPlayer.sendMessage(String.format("Sent %.2f to %s.", amount, receiverPlayer.getName()));
                receiverPlayer.sendMessage(String.format("%s sent you %.2f.", senderPlayer.getName(), amount));
            } else {
                sender.sendMessage("Only players can use this command.");
            }
            return true;
        } else if (command.getName().equalsIgnoreCase("setbalance") && sender.hasPermission("ae.mod.setbalance")) {
            if (args.length < 2) {
                sender.sendMessage("Usage: /setbalance <player> <amount>");
                return true;
            }
            Player player = getServer().getPlayer(args[0]);
            if (player == null) {
                sender.sendMessage("Player not found.");
                return true;
            }
            double amount;
            try {
                amount = Double.parseDouble(args[1]);
            } catch (NumberFormatException e) {
                sender.sendMessage("Invalid amount.");
                return true;
            }
            setBalance(player.getUniqueId(), amount);
            sender.sendMessage(String.format("Set %s's balance to %.2f", player.getName(), amount));

        } else if (command.getName().equalsIgnoreCase("takebalance") && sender.hasPermission("ae.mod.takebalance")) {
            if (args.length < 2) {
                sender.sendMessage("Usage: /takebalance <player> <amount>");
                return true;
            }

            Player player = getServer().getPlayer(args[0]);

            if (player == null) {
                sender.sendMessage("Player not found.");
                return true;
            }
            double amount;
            try {
                amount = Double.parseDouble(args[1]);
            } catch (NumberFormatException e) {
                sender.sendMessage("Invalid amount.");
                return true;
            }
            takeBalance(player.getUniqueId(), amount);
            sender.sendMessage(String.format("Took %.2f from %s's balance.", amount, player.getName()));
            return true;
        }
        return true;
    }

    public double getBalance(UUID playerId) {
        return balances.getOrDefault(playerId, 0.0);
    }

    public void setBalance(UUID playerId, double balance) {
        balances.put(playerId, balance);
    }

    public void takeBalance(UUID playerId, double amount) {
        balances.put(playerId, getBalance(playerId) - amount);
    }

    public void transfer(UUID senderId, UUID receiverId, double amount) {
        setBalance(senderId, getBalance(senderId) - amount);
        setBalance(receiverId, getBalance(receiverId) + amount);
    }
}
