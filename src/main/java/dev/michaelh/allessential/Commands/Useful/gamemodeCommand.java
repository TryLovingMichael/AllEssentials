package dev.michaelh.allessential.Commands.Useful;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class gamemodeCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length > 0) {
            String subCommand = args[0];
            if (sender.hasPermission("ae.gamemode")) {
            switch (subCommand) {
                    case "s":
                    case "survival": {
                        if (args.length > 1) {
                            String target = args[1];
                            Player targetPlayer = Bukkit.getPlayer(target);
                            if (targetPlayer != null) {
                                targetPlayer.setGameMode(GameMode.SURVIVAL);
                                targetPlayer.sendMessage("Your gamemode has been set to survival.");
                                sender.sendMessage("Set " + targetPlayer.getName() + "'s gamemode to survival.");
                            } else {
                                sender.sendMessage("Player not found.");
                            }
                        } else {
                            if (sender instanceof Player) {
                                Player player = (Player) sender;
                                player.setGameMode(GameMode.SURVIVAL);
                                sender.sendMessage("Set your gamemode to survival.");
                            } else {
                                sender.sendMessage("You must specify a player.");
                            }
                        }
                    }
                    break;


                    case "c":
                    case "creative": {
                        if (args.length > 1) {
                            String target = args[1];
                            Player targetPlayer = Bukkit.getPlayer(target);
                            if (targetPlayer != null) {
                                targetPlayer.setGameMode(GameMode.CREATIVE);
                                targetPlayer.sendMessage("Your gamemode has been set to creative.");
                                sender.sendMessage("Set " + targetPlayer.getName() + "'s gamemode to creative.");
                            } else {
                                sender.sendMessage("Player not found.");
                            }
                        } else {
                            if (sender instanceof Player) {
                                Player player = (Player) sender;
                                player.setGameMode(GameMode.CREATIVE);
                                sender.sendMessage("Set your gamemode to creative.");
                            } else {
                                sender.sendMessage("You must specify a player.");
                            }
                        }
                    }
                    break;

                    case "a":
                    case "adventure": {
                        if (args.length > 1) {
                            String target = args[1];
                            Player targetPlayer = Bukkit.getPlayer(target);
                            if (targetPlayer != null) {
                                targetPlayer.setGameMode(GameMode.ADVENTURE);
                                targetPlayer.sendMessage("Your gamemode has been set to adventure.");
                                sender.sendMessage("Set " + targetPlayer.getName() + "'s gamemode to adventure.");
                            } else {
                                sender.sendMessage("Player not found.");
                            }
                        } else {
                            if (sender instanceof Player) {
                                Player player = (Player) sender;
                                player.setGameMode(GameMode.ADVENTURE);
                                sender.sendMessage("Set your gamemode to adventure.");
                            } else {
                                sender.sendMessage("You must specify a player.");
                            }
                        }
                    }
                    break;

                default:
                    sender.sendMessage("Invalid gamemode. Please select survival, creative, or adventure.");
                    break;
                }

                return true;
            }
            return true;
        } return true;
    }}