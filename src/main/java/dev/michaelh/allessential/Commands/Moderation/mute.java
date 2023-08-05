package dev.michaelh.allessential.Commands.Moderation;

import dev.michaelh.allessential.AllEssential;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;

public class mute implements CommandExecutor, Listener {

    public static List<Player> muted;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;
        Player target = Bukkit.getPlayer(args[0]);


        if(target == null){
            player.sendMessage(AllEssential.failMessage);
        }

        if(player.hasPermission("ae.mod.mute")){
            player.sendMessage(ChatColor.GREEN + "You have muted: " + ChatColor.AQUA + target);

            muted.add(target);

        } else {
            player.sendMessage(AllEssential.denyMessage);
        }

        return false;
    }


    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){

        Player player = event.getPlayer();

        if(muted.contains(player)){
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + "You are muted,");
        }

    }

}
