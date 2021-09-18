package com.i0dev.SwitcherBalls.config;

import com.i0dev.SwitcherBalls.Heart;
import com.i0dev.SwitcherBalls.templates.AbstractConfiguration;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MessageConfig extends AbstractConfiguration {

    String reloadUsage = "&cUsage: &7/switcherBalls reload";
    String giveUsage = "&cUsage: &7/switcherBalls give <player> [amount]";
    String cantUseSystemFaction = "&cYou cannot use Switcher Balls in system factions.";
    String cantSwitchOwnTeam = "&cYou cannot use Switcher Balls on your own faction.";
    String switched = "&7You switched locations with &c{player}";
    String gave = "&7You gave {player} &fx{amt} &7Switcher Balls.";
    String received = "&7You have received &fx{amt} &7Switcher Balls from &c{player}";
    String onCoolDown = "&7You are on cool-down from using Switcher Balls for &c{sec} seconds.";
    String noLongerCoolDown = "&7You are &ano longer&7 on cool-down from using Switcher Balls.";

    String reloadedConfig = "&7You have&a reloaded&7 the configuration.";
    String noPermission = "&cYou don not have permission to run that command.";
    String cantFindPlayer = "&cThe player: &f{player}&c cannot be found!";
    String invalidNumber = "&cThe number &f{num} &cis invalid! Try again.";

    public MessageConfig(Heart heart, String path) {
        this.path = path;
        this.heart = heart;
    }
}
