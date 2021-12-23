package com.i0dev.SwitcherBalls.config;

import com.i0dev.SwitcherBalls.Heart;
import com.i0dev.SwitcherBalls.templates.AbstractConfiguration;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class GeneralConfig extends AbstractConfiguration {

    List<String> lore = Arrays.asList(
            "",
            "&7Use this projectile to switch locations",
            "&7with the player you throw it at!",
            "",
            "&7Cooldown: &c{cooldown} seconds"
    );
    String displayName = "&4&lSwitcher Ball";
    boolean glow = true;

    long coolDownSeconds = 10;
    boolean msgOnCoolDownEnd = true;

    long blocksRangeToActivate = 5;
    boolean showSwitcherballName = true;

    String switchSuccessSound = "VILLAGER_YES";
    String switchFailSound = "VILLAGER_NO";

    boolean blockSystemFactionUse = true;
    boolean blockTeamSwitch = true;

    public GeneralConfig(Heart heart, String path) {
        this.path = path;
        this.heart = heart;
    }

}
