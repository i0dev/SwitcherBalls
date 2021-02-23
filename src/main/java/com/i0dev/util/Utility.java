package com.i0dev.util;

import com.i0dev.SwitcherBalls;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Utility {

    public static String colorize(String content) {
        return ChatColor.translateAlternateColorCodes('&', content);
    }

    public static List<String> colorize(List<String> content) {
        List<String> coloredList = new ArrayList<>();
        content.forEach(string -> coloredList.add(colorize(string)));
        return coloredList;
    }

    public static ItemStack getSwitcherItemStack(int count) {
        FileConfiguration config = SwitcherBalls.getConfiguration();
        ItemStack switcherBall = new ItemStack(Material.SNOW_BALL, count);
        ItemMeta switcherMeta = switcherBall.getItemMeta();
        switcherMeta.setLore(colorize(config.getStringList("item.lore")));
        switcherMeta.setDisplayName(colorize(config.getString("item.name")));
        if (config.getBoolean("item.glow")) {
            switcherMeta.addEnchant(Enchantment.DURABILITY, 100, true);
            switcherMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_PLACED_ON);
        }
        switcherBall.setItemMeta(switcherMeta);
        return switcherBall;
    }
}
