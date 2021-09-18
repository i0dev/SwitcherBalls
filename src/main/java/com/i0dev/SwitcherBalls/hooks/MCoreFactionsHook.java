package com.i0dev.SwitcherBalls.hooks;

import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.ps.PS;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class MCoreFactionsHook {

    public static boolean isWilderness(Location location) {
        return BoardColl.get().getFactionAt(PS.valueOf(location)).isNone();
    }

    public static boolean isSystemFaction(Location location) {
        if (isWilderness(location)) return false;
        return BoardColl.get().getFactionAt(PS.valueOf(location)).isSystemFaction();
    }

    public static boolean isOwn(Location location, Player player) {
        return BoardColl.get().getFactionAt(PS.valueOf(location)).getId().equals(MPlayer.get(player).getFaction().getId());
    }

    public static boolean isSameFaction(Player player1, Player player2) {
        MPlayer mPlayer1 = MPlayer.get(player1);
        MPlayer mPlayer2 = MPlayer.get(player2);
        return mPlayer1.getFaction().getId().equalsIgnoreCase(mPlayer2.getFaction().getId());
    }

}
