package com.i0dev.hook;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class CheckIntegrations {


    public static boolean mCoreFactionsCheck() {
        Plugin plugin = Bukkit.getPluginManager().getPlugin("Factions");
        if (plugin == null) {
            return false;
        }
        if (plugin.getDescription().getMain().equals("com.massivecraft.factions.Factions") || plugin.getDescription().getWebsite().equals("https://www.massivecraft.com/factions")) {
            System.out.println("Using MCore Factions");

            return true;
        }
        return false;

    }

    public static boolean SuperiorSkyBlockCheck() {
        Plugin plugin = Bukkit.getPluginManager().getPlugin("SuperiorSkyblock2");
        if (plugin != null) {
            System.out.println("Using Superior Skyblock");

            return true;
        }
        return false;
    }

    public static boolean SupremeCheck() {
        Plugin plugin = Bukkit.getPluginManager().getPlugin("Factions");
        if (plugin == null) {
            return false;
        }
        if (plugin.getDescription().getMain().equals("com.massivecraft.factions.P") || plugin.getDescription().getLoadBefore().contains("SupremeCommons")) {
            System.out.println("Using Supreme Factions");

            return true;
        }
        return false;
    }
}
