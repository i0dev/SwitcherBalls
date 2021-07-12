package com.i0dev;

import com.i0dev.command.cmdSwitcher;
import com.i0dev.event.EntityDamageByEntityEvent;
import com.i0dev.event.ProjectileLaunchEvent;
import com.i0dev.util.Utility;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class SwitcherBalls extends JavaPlugin {


    @Override
    public void onEnable() {
        inst = this;
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new EntityDamageByEntityEvent(), this);
        getServer().getPluginManager().registerEvents(new ProjectileLaunchEvent(), this);
        getCommand("switcher").setExecutor(new cmdSwitcher());

        Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> {
            List<UUID> toRemove = new ArrayList<>();
            getCooldownMap().forEach((uuid, aLong) -> {
                if (System.currentTimeMillis() >= aLong) {
                    toRemove.add(uuid);
                }
            });
            toRemove.forEach(uuid -> {
                getCooldownMap().remove(uuid);
                Player player = Bukkit.getPlayer(uuid);
                if (player != null && getConfiguration().getBoolean("msgOnCooldownEnd")) {
                    player.sendMessage(Utility.colorize(getConfiguration().getString("messaging.noLongerOnCooldown")));
                }
            });
            toRemove.clear();
        }, 20L, 20L);

        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "SwitcherBalls Enabled!");
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "SwitcherBalls Disabled!");
    }

    public static FileConfiguration getConfiguration() {
        return get().getConfig();
    }

    private static SwitcherBalls inst;

    public static SwitcherBalls get() {
        return inst;
    }

    public static Map<UUID, Long> cooldownMap = new HashMap<>();

    public static Map<UUID, Long> getCooldownMap() {
        return cooldownMap;
    }
}
