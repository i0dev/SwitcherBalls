package com.i0dev.SwitcherBalls.managers;

import com.i0dev.SwitcherBalls.Heart;
import com.i0dev.SwitcherBalls.config.GeneralConfig;
import com.i0dev.SwitcherBalls.config.MessageConfig;
import com.i0dev.SwitcherBalls.templates.AbstractManager;
import com.i0dev.SwitcherBalls.utility.Utility;
import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

@Getter
public class SwitcherBallManager extends AbstractManager {
    public SwitcherBallManager(Heart heart) {
        super(heart);
    }

    Map<UUID, Long> coolDownMap;
    GeneralConfig cnf;
    MessageConfig msg;
    MessageManager msgManager;
    BukkitTask task;

    @Override
    public void initialize() {
        msgManager = getHeart().getManager(MessageManager.class);
        msg = getHeart().getConfig(MessageConfig.class);
        cnf = getHeart().getConfig(GeneralConfig.class);
        coolDownMap = new HashMap<>();
        task = Bukkit.getScheduler().runTaskTimerAsynchronously(getHeart(), taskUpdateCoolDown, 20L, 20L);
    }

    @Override
    public void deinitialize() {
        task.cancel();
        coolDownMap.clear();
        msgManager = null;
        msg = null;
        cnf = null;
    }

    public ItemStack getItem(int amt) {
        GeneralConfig cnf = getHeart().getConfig(GeneralConfig.class);
        MessageManager msgManager = getHeart().getManager(MessageManager.class);
        List<String> newLore = new ArrayList<>();
        cnf.getLore().forEach(s -> {
            newLore.add(msgManager.pair(s,
                    new MessageManager.Pair<>("{cooldown}", cnf.getCoolDownSeconds() + "")
            ));
        });
        ItemStack switcher = Utility.makeItem(Material.SNOW_BALL, amt, (short) 0, cnf.getDisplayName(), newLore, cnf.isGlow());
        NBTItem nbtItem = new NBTItem(switcher);
        nbtItem.setBoolean("switcherBall", true);
        switcher = nbtItem.getItem();
        return switcher;
    }


    public Runnable taskUpdateCoolDown = () -> {
        List<UUID> toRemove = new ArrayList<>();
        coolDownMap.forEach((uuid, aLong) -> {
            if (System.currentTimeMillis() >= aLong) {
                toRemove.add(uuid);
            }
        });
        toRemove.forEach(uuid -> {
            coolDownMap.remove(uuid);
            Player player = Bukkit.getPlayer(uuid);
            if (player != null && cnf.isMsgOnCoolDownEnd()) msgManager.msg(player, msg.getNoLongerCoolDown());
        });
        toRemove.clear();
    };

}
