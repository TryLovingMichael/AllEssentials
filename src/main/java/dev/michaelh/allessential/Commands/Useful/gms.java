package dev.michaelh.allessential.Commands.Useful;

import dev.michaelh.allessential.AllEssential;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class gms implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        if(player.hasPermission("ae.mod.gamemode")){

            player.sendMessage(ChatColor.GREEN + "Gamemode set to " + ChatColor.AQUA + "SURVIVAL");
            player.setGameMode(GameMode.SURVIVAL);
        } else {
            player.sendMessage(AllEssential.denyMessage);
        }

        return false;
    }


}
