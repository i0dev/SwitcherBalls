package com.i0dev.SwitcherBalls.templates;

import com.i0dev.SwitcherBalls.Heart;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.List;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public abstract class AbstractCommand extends AbstractManager implements CommandExecutor, TabExecutor {

    /*
    Credit given to EmberCM
    Discord: Ember#1404
    GitHub: https://github.com/EmberCM
     */

    String command;

    public AbstractCommand(Heart heart, String command) {
        super(heart);
        this.command = command;
    }

    public AbstractCommand(Heart heart) {
        super(heart);
        String name = getClass().getSimpleName().toLowerCase();
        this.command = name.substring(2);
    }

    public abstract void execute(CommandSender sender, String[] args);

    public List<String> tabComplete(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equals(this.command)) return false;
        execute(sender, args);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equals(this.command)) return null;
        return tabComplete(sender, args);
    }
}