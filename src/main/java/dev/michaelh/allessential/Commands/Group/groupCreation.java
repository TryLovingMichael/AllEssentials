package dev.michaelh.allessential.Commands.Group;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class groupCreation implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if(player.hasPermission("ae.group.create")){
            if(args.length == 0){
                player.sendMessage("Please specify a group name.");
            } else {
                player.sendMessage("Group created!");
            }
        } else {
            player.sendMessage("You do not have permission to use this command!");
        }


        return false;
    }


}
