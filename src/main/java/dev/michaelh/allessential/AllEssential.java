package dev.michaelh.allessential;

import dev.michaelh.allessential.Commands.Admin.reload;
import dev.michaelh.allessential.Commands.Admin.worldtp;
import dev.michaelh.allessential.Commands.Economy.economyMain;
import dev.michaelh.allessential.Commands.Fun.nuke;
import dev.michaelh.allessential.Commands.Moderation.ban;
import dev.michaelh.allessential.Commands.Moderation.kick;
import dev.michaelh.allessential.Commands.Moderation.setspawn;
import dev.michaelh.allessential.Commands.Moderation.tempban;
import dev.michaelh.allessential.Commands.QOL.*;
import dev.michaelh.allessential.Commands.Useful.gamemodeCommand;
import dev.michaelh.allessential.Commands.Useful.gmc;
import dev.michaelh.allessential.Commands.Useful.gms;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.*;
import java.util.HashMap;
import java.util.UUID;

public final class AllEssential extends JavaPlugin implements Listener {

    public static String webhookUrl;
    public static AllEssential instance;

    public static String denyMessage = ChatColor.RED + "You do not have permission.";
    public static String failMessage = ChatColor.RED + "Command Failed.";


    @Override
    public void onEnable() {

        instance = this;

        FileConfiguration config = getConfig();
        webhookUrl = config.getString("webhook_url");
        saveDefaultConfig();

        if (webhookUrl == null) {
            getLogger().info("No webhook URL found in config.yml, please add one for reports.");
        }

        // Plugin startup logic
        System.out.println("AllEssential loaded.");

        reportCommand.reports = new HashMap<>();

        //Commands
        getCommand("gamemode").setExecutor(new gamemodeCommand());
        getCommand("kick").setExecutor(new kick());
        getCommand("ban").setExecutor(new ban());
        getCommand("tempban").setExecutor(new tempban());
        getCommand("fly").setExecutor(new fly());
        getCommand("feed").setExecutor(new feed());
        getCommand("report").setExecutor(new reportCommand());
        getCommand("reports").setExecutor(new reportCommand());
        getCommand("day").setExecutor(new day());
        getCommand("sun").setExecutor(new sun());
        getCommand("setspawn").setExecutor(new setspawn());
        getCommand("reloadconfig").setExecutor(new reload());
        getCommand("gmc").setExecutor(new gmc());
        getCommand("gms").setExecutor(new gms());
        getCommand("nuke").setExecutor(new nuke());
        getCommand("worldtp").setExecutor(new worldtp());


        //Economy
        economyMain balanceCMD = new economyMain();
        getCommand("balance").setExecutor(balanceCMD);
        getCommand("pay").setExecutor(balanceCMD);
        getCommand("setbalance").setExecutor(balanceCMD);
        getCommand("takebalance").setExecutor(balanceCMD);
        loadBalancesFromFiles();


        //Listeners
        getServer().getPluginManager().registerEvents(this, this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("AllEssential disabled.");

        saveBalancesToFiles();
    }

    @EventHandler
    public void leaveEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        savePersonalFile(player.getUniqueId(), player);
    }

    public String getWebhookUrl() {
        return webhookUrl;
    }

    public void savePersonalFile(UUID uuid, Player player){
        File dataFolder = getDataFolder();
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
        double balance = economyMain.balances.get(uuid);

        File playerFile = new File(dataFolder, uuid.toString() + ".yml");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(playerFile))) {
            writer.write("# Player Balance Data #");
            writer.newLine();
            writer.write("# Do not edit this file manually! #");
            writer.newLine();
            writer.write("# This file is automatically generated by the AllEssential plugin. #");
            writer.newLine();
            writer.write(player.getName());
            writer.newLine();
            writer.write(Double.toString(balance));
            writer.flush();
        } catch (IOException e) {
            getLogger().warning("Failed to write balance data for player " + ": " + e.getMessage());
        }
    }

    public void loadBalancesFromFiles() {
        File dataFolder = getDataFolder();
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            File playerFile = new File(dataFolder, player.getUniqueId().toString() + ".yml");
            if (playerFile.exists()) {
                try (BufferedReader reader = new BufferedReader(new FileReader(playerFile))) {
                    String name = reader.readLine();
                    double balance = Double.parseDouble(reader.readLine());
                    economyMain.balances.put(player.getUniqueId(), balance);
                } catch (IOException e) {
                    getLogger().warning("Failed to read balance data for player " + player.getName() + ": " + e.getMessage());
                }
            }
        }
    }

    public void saveBalancesToFiles() {
        File dataFolder = getDataFolder();
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
        for (Player player : Bukkit.getOnlinePlayers()) {
            UUID uuid = player.getUniqueId();
            double balance = economyMain.balances.get(uuid);

            File playerFile = new File(dataFolder, uuid.toString() + ".yml");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(playerFile))) {
                writer.write("# Player Balance Data #");
                writer.newLine();
                writer.write("# Do not edit this file manually! #");
                writer.newLine();
                writer.write("# This file is automatically generated by the AllEssential plugin. #");
                writer.newLine();
                writer.write(player.getName());
                writer.newLine();
                writer.write(Double.toString(balance));
                writer.flush();
            } catch (IOException e) {
                getLogger().warning("Failed to write balance data for player " + player.getName() + ": " + e.getMessage());
            }
        }
    }
}