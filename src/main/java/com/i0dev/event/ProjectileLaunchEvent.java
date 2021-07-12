package com.i0dev.event;

import com.i0dev.SwitcherBalls;
import com.i0dev.util.Utility;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public class ProjectileLaunchEvent implements Listener {

    @EventHandler
    public void onProjectileLaunch(org.bukkit.event.entity.ProjectileLaunchEvent e) {
        if (!(e.getEntity() instanceof Snowball)) return;
        if (!(e.getEntity().getShooter() instanceof Player)) return;
        Player thrower = (Player) e.getEntity().getShooter();
        ItemStack item = thrower.getItemInHand();
        if (!item.hasItemMeta()) return;
        ItemMeta itemMeta = thrower.getItemInHand().getItemMeta();
        String switcherDisplayName = Utility.getSwitcherItemStack(1).getItemMeta().getDisplayName();
        if (!itemMeta.hasDisplayName()) return;
        if (!itemMeta.getDisplayName().equals(switcherDisplayName)) return;
        UUID uuid = thrower.getUniqueId();
        if (SwitcherBalls.getCooldownMap().containsKey(uuid)) {
            thrower.sendMessage(Utility.colorize(SwitcherBalls.getConfiguration().getString("messaging.switcherOnCooldown").replace("{sec}",
                    ((SwitcherBalls.getCooldownMap().get(uuid) - System.currentTimeMillis()) / 1000) + ""
            )));
            e.setCancelled(true);
            return;
        }
        e.getEntity().setCustomNameVisible(true);
        e.getEntity().setCustomName(switcherDisplayName);
    }
}
