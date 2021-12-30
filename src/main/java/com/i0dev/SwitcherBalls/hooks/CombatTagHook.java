package com.i0dev.SwitcherBalls.hooks;

import net.minelink.ctplus.CombatTagPlus;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CombatTagHook {

    public static void combatTagPlayer(Player player, Player attacker) {
        ((CombatTagPlus) Bukkit.getPluginManager().getPlugin("CombatTagPlus")).getTagManager().tag(player, attacker);
    }

}
