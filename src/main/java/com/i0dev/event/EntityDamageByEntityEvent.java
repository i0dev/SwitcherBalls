package com.i0dev.event;

import com.i0dev.SwitcherBalls;
import com.i0dev.hook.CheckIntegrations;
import com.i0dev.util.Utility;
import com.massivecraft.factions.Board;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.ps.PS;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EntityDamageByEntityEvent implements Listener {

    @EventHandler
    public void onEntityDamageByEntity(org.bukkit.event.entity.EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        if (!(e.getDamager() instanceof Snowball)) return;
        FileConfiguration config = SwitcherBalls.getConfiguration();
        Snowball snowball = (Snowball) e.getDamager();
        if (!snowball.getName().equals(Utility.getSwitcherItemStack(1).getItemMeta().getDisplayName())) return;

        Player hit = (Player) e.getEntity();
        Player thrower = (Player) snowball.getShooter();
        Location throwerLocation = thrower.getLocation();
        Location hitLocation = hit.getLocation();

        if (CheckIntegrations.mCoreFactionsCheck()) {
            MPlayer mThrower = MPlayer.get(thrower);
            MPlayer mHit = MPlayer.get(hit);
            if (config.getBoolean("integration.mcoreFactions.blockWarZone")) {
                if (BoardColl.get().getFactionAt(PS.valueOf(hitLocation)).getId().equalsIgnoreCase("warZone") || BoardColl.get().getFactionAt(PS.valueOf(throwerLocation)).getId().equalsIgnoreCase("warZone")) {
                    thrower.sendMessage(Utility.colorize(config.getString("messaging.cannotSwitchInWarZone")));
                    thrower.playSound(throwerLocation, Sound.valueOf(config.getString("sounds.switchFail")), 10.0F, 10.0F);
                    e.setCancelled(true);
                    return;
                }
            }
            if (config.getBoolean("integration.mcoreFactions.blockSafeZone")) {
                if (BoardColl.get().getFactionAt(PS.valueOf(hitLocation)).getId().equalsIgnoreCase("safeZone") || BoardColl.get().getFactionAt(PS.valueOf(throwerLocation)).getId().equalsIgnoreCase("safeZone")) {
                    thrower.sendMessage(Utility.colorize(config.getString("messaging.cannotSwitchInSafeZone")));
                    thrower.playSound(throwerLocation, Sound.valueOf(config.getString("sounds.switchFail")), 10.0F, 10.0F);
                    e.setCancelled(true);
                    return;
                }
            }
            if (config.getBoolean("integration.mcoreFactions.blockTeamSwitch")) {
                if (mThrower.getFaction().equals(mHit.getFaction())) {
                    thrower.sendMessage(Utility.colorize(config.getString("messaging.cannotSwitchFriendly")));
                    thrower.playSound(throwerLocation, Sound.valueOf(config.getString("sounds.switchFail")), 10.0F, 10.0F);
                    e.setCancelled(true);
                    return;
                }

            }
        }
        if (CheckIntegrations.SupremeCheck()) {
            FPlayer fThrower = FPlayers.getInstance().getByPlayer(thrower);
            FPlayer fHit = FPlayers.getInstance().getByPlayer(hit);

            if (config.getBoolean("integration.supremeFactions.blockWarZone")) {

                if (Board.getInstance().getFactionAt(fThrower.getLastStoodAt()).isWarZone() || Board.getInstance().getFactionAt(fHit.getLastStoodAt()).isWarZone()) {
                    thrower.sendMessage(Utility.colorize(config.getString("messaging.cannotSwitchInWarZone")));
                    thrower.playSound(throwerLocation, Sound.valueOf(config.getString("sounds.switchFail")), 10.0F, 10.0F);
                    e.setCancelled(true);
                    return;
                }
            }
            if (config.getBoolean("integration.supremeFactions.blockSafeZone")) {
                if (Board.getInstance().getFactionAt(fThrower.getLastStoodAt()).isSafeZone() || Board.getInstance().getFactionAt(fHit.getLastStoodAt()).isSafeZone()) {
                    thrower.sendMessage(Utility.colorize(config.getString("messaging.cannotSwitchInSafeZone")));
                    thrower.playSound(throwerLocation, Sound.valueOf(config.getString("sounds.switchFail")), 10.0F, 10.0F);
                    e.setCancelled(true);
                    return;
                }
            }
            if (config.getBoolean("integration.supremeFactions.blockTeamSwitch")) {
                if (fThrower.getFaction().equals(fHit.getFaction())) {
                    thrower.sendMessage(Utility.colorize(config.getString("messaging.cannotSwitchFriendly")));
                    thrower.playSound(throwerLocation, Sound.valueOf(config.getString("sounds.switchFail")), 10.0F, 10.0F);
                    e.setCancelled(true);
                    return;
                }

            }
        }

        thrower.teleport(hitLocation);
        hit.teleport(throwerLocation);
        hit.playSound(hitLocation, Sound.valueOf(config.getString("sounds.switchSuccess")), 10.0F, 10.0F);
        thrower.playSound(throwerLocation, Sound.valueOf(config.getString("sounds.switchSuccess")), 10.0F, 10.0F);
        thrower.sendMessage(Utility.colorize(config.getString("messaging.switchedLocations").replace("{player}", hit.getName())));
        hit.sendMessage(Utility.colorize(config.getString("messaging.switchedLocations").replace("{player}", thrower.getName())));
        SwitcherBalls.getCooldownMap().put(thrower.getUniqueId(), System.currentTimeMillis() + (SwitcherBalls.getConfiguration().getLong("cooldownSeconds") * 1000));
        e.setCancelled(true);
    }
}
