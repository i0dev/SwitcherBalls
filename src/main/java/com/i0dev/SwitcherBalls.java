package com.i0dev;

import com.i0dev.command.cmdSwitcher;
import com.i0dev.event.EntityDamageByEntityEvent;
import com.i0dev.event.ProjectileLaunchEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class SwitcherBalls extends JavaPlugin {


    @Override
    public void onEnable() {
        inst = this;
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new EntityDamageByEntityEvent(), this);
        getServer().getPluginManager().registerEvents(new ProjectileLaunchEvent(), this);
        getCommand("switcher").setExecutor(new cmdSwitcher());
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "SwitcherBalls Enabled!");
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "SwitcherBalls Disabled!");
    }

    public static FileConfiguration getConfiguration() {
        return get().getConfig();
    }

    private static SwitcherBalls inst;

    public static SwitcherBalls get() {
        return inst;
    }
}
