package com.i0dev.SwitcherBalls.managers;

import com.i0dev.SwitcherBalls.Heart;
import com.i0dev.SwitcherBalls.templates.AbstractManager;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.UUID;

public class MessageManager extends AbstractManager {
    public MessageManager(Heart heart) {
        super(heart);
    }


    public String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public String papi(CommandSender sender, String s) {
        if (!getHeart().isUsingPapi() || !(sender instanceof Player)) return s;
        return me.clip.placeholderapi.PlaceholderAPI.setPlaceholders((Player) sender, s);
    }

    public String pair(String msg, Pair<String, String>... pairs) {
        for (Pair<String, String> pair : pairs) {
            msg = msg.replace(pair.getKey(), pair.getValue());
        }
        return msg;
    }

    @SafeVarargs
    public final void msg(CommandSender sender, String msg, Pair<String, String>... pairs) {
        sender.sendMessage(color(papi(sender, pair(msg, pairs))));
    }

    @SafeVarargs
    public final void msg(CommandSender sender, Collection<String> msg, Pair<String, String>... pairs) {
        msg.forEach(s -> sender.sendMessage(color(papi(sender, pair(s, pairs)))));
    }

    public Player getPlayer(String s) {
        if (s.length() > 20)
            return Bukkit.getPlayer(UUID.fromString(s));
        return Bukkit.getPlayer(s);
    }


    /*
    Credit given to EmberCM
    Discord: Ember#1404
    GitHub: https://github.com/EmberCM
    */

    @Getter
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    public static class Pair<K, V> {
        K key;
        V value;

        @Override
        public String toString() {
            return key + "~" + value;
        }
    }

}
