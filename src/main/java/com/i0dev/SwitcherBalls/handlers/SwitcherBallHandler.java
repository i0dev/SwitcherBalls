package com.i0dev.SwitcherBalls.handlers;

import com.i0dev.SwitcherBalls.Heart;
import com.i0dev.SwitcherBalls.config.GeneralConfig;
import com.i0dev.SwitcherBalls.config.MessageConfig;
import com.i0dev.SwitcherBalls.hooks.CombatTagHook;
import com.i0dev.SwitcherBalls.hooks.MCoreFactionsHook;
import com.i0dev.SwitcherBalls.managers.MessageManager;
import com.i0dev.SwitcherBalls.managers.SwitcherBallManager;
import com.i0dev.SwitcherBalls.templates.AbstractListener;
import de.tr7zw.changeme.nbtapi.NBTEntity;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class SwitcherBallHandler extends AbstractListener {

    public SwitcherBallHandler(Heart heart) {
        super(heart);
    }

    MessageConfig msg;
    MessageManager msgManager;
    SwitcherBallManager sManager;
    GeneralConfig cnf;

    @Override
    public void initialize() {
        sManager = getHeart().getManager(SwitcherBallManager.class);
        msgManager = getHeart().getManager(MessageManager.class);
        msg = getHeart().getConfig(MessageConfig.class);
        cnf = getHeart().getConfig(GeneralConfig.class);
    }

    @Override
    public void deinitialize() {
        sManager = null;
        msgManager = null;
        msg = null;
        cnf = null;
    }


    @EventHandler
    public void onSwitcherBallHit(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        if (!(e.getDamager() instanceof Snowball)) return;
        Snowball snowball = (Snowball) e.getDamager();
        if (!msgManager.color(snowball.getName()).equals(sManager.getItem(1).getItemMeta().getDisplayName())) return;
        Player hit = (Player) e.getEntity();
        Player thrower = (Player) snowball.getShooter();
        Location throwerLocation = thrower.getLocation();
        Location hitLocation = hit.getLocation();

        if (throwerLocation.distance(hitLocation) > cnf.getBlocksRangeToActivate()) {
            e.setCancelled(true);
            thrower.playSound(throwerLocation, Sound.valueOf(cnf.getSwitchFailSound()), 10.0F, 10.0F);
            msgManager.msg(thrower, msg.getOutOfRange());
            return;
        }


        if (getHeart().isUsingMCoreFactions()) {
            if (cnf.isBlockSystemFactionUse() && MCoreFactionsHook.isSystemFaction(hitLocation)) {
                msgManager.msg(thrower, msg.getCantUseSystemFaction());
                thrower.playSound(throwerLocation, Sound.valueOf(cnf.getSwitchFailSound()), 10.0F, 10.0F);
                e.setCancelled(true);
                return;
            }

            if (cnf.isBlockTeamSwitch() && MCoreFactionsHook.isSameFaction(hit, thrower)) {
                msgManager.msg(thrower, msg.getCantSwitchOwnTeam());
                thrower.playSound(throwerLocation, Sound.valueOf(cnf.getSwitchFailSound()), 10.0F, 10.0F);
                e.setCancelled(true);
                return;
            }
        }

        if (cnf.isCombatTagPlus_tagOnSwitcherBall() && Heart.usingCombatTag) {
            CombatTagHook.combatTagPlayer(hit, thrower);
        }

        thrower.teleport(hitLocation);
        hit.teleport(throwerLocation);
        hit.playSound(hitLocation, Sound.valueOf(cnf.getSwitchSuccessSound()), 10.0F, 10.0F);
        thrower.playSound(throwerLocation, Sound.valueOf(cnf.getSwitchSuccessSound()), 10.0F, 10.0F);
        msgManager.msg(thrower, msg.getSwitched(), new MessageManager.Pair<>("{player}", hit.getName()));
        msgManager.msg(hit, msg.getSwitched(), new MessageManager.Pair<>("{player}", thrower.getName()));
        sManager.getCoolDownMap().put(thrower.getUniqueId(), System.currentTimeMillis() + cnf.getCoolDownSeconds() * 1000);
        e.setCancelled(true);
    }


    @EventHandler
    public void onSwitcherBallThrow(ProjectileLaunchEvent e) {
        if (!(e.getEntity() instanceof Snowball)) return;
        if (!(e.getEntity().getShooter() instanceof Player)) return;
        Player thrower = ((Player) e.getEntity().getShooter());
        ItemStack item = thrower.getItemInHand();
        NBTItem nbtItem = new NBTItem(item);
        if (!nbtItem.getBoolean("switcherBall")) return;
        UUID uuid = thrower.getUniqueId();

        if (getHeart().isUsingMCoreFactions()) {
            if (cnf.isBlockSystemFactionUse() && MCoreFactionsHook.isSystemFaction(thrower.getLocation())) {
                msgManager.msg(thrower, msg.getCantUseSystemFaction());
                thrower.playSound(thrower.getLocation(), Sound.valueOf(cnf.getSwitchFailSound()), 10.0F, 10.0F);
                e.setCancelled(true);
                return;
            }
        }

        if (sManager.getCoolDownMap().containsKey(uuid)) {
            msgManager.msg(thrower, msg.getOnCoolDown(), new MessageManager.Pair<>("{sec}", ((sManager.getCoolDownMap().get(uuid) - System.currentTimeMillis()) / 1000) + ""));
            if (thrower.getGameMode().equals(GameMode.SURVIVAL) || thrower.getGameMode().equals(GameMode.ADVENTURE)) {
                thrower.getInventory().addItem(sManager.getItem(1));
            }
            e.setCancelled(true);
            return;
        }
        if (cnf.isShowSwitcherballName())
            e.getEntity().setCustomNameVisible(true);
        e.getEntity().setCustomName(cnf.getDisplayName());
    }
}
