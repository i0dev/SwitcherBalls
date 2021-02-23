package com.i0dev.command;


import com.i0dev.SwitcherBalls;
import com.i0dev.util.Utility;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class cmdSwitcher implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.isOp() || (sender instanceof ConsoleCommandSender) || sender.hasPermission("switcherballs.give")) {
            FileConfiguration config = SwitcherBalls.getConfiguration();
            if ((args.length == 0) || (args.length == 1 && args[0].equalsIgnoreCase("give"))) {
                sender.sendMessage(Utility.colorize(config.getString("messaging.format")));
                return false;
            }

            Player giveTo;
            if (args.length == 2 && args[0].equalsIgnoreCase("give")) {
                giveTo = Bukkit.getServer().getPlayer(args[1]);
                sender.sendMessage(Utility.colorize(config.getString("messaging.gaveSwitcher").replace("{player}", giveTo.getName()).replace("{count}", "1")));
                giveTo.getInventory().addItem(Utility.getSwitcherItemStack(1));
                giveTo.sendMessage(Utility.colorize(config.getString("messaging.receivedSwitcher").replace("{player}", sender.getName()).replace("{count}", "1")));
            }

            if (args.length == 3 && args[0].equalsIgnoreCase("give")) {
                giveTo = Bukkit.getServer().getPlayer(args[1]);
                sender.sendMessage(Utility.colorize(config.getString("messaging.gaveSwitcher").replace("{player}", giveTo.getName()).replace("{count}", args[2])));
                giveTo.sendMessage(Utility.colorize(config.getString("messaging.receivedSwitcher").replace("{player}", sender.getName()).replace("{count}", args[2])));
                giveTo.getInventory().addItem(Utility.getSwitcherItemStack(Integer.parseInt(args[2])));
            }
        }
        return true;
    }
}
