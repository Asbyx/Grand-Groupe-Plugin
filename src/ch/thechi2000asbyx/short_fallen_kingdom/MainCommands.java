package ch.thechi2000asbyx.short_fallen_kingdom;

import ch.Asbyx.tomb.DeathListener;
import ch.thechi2000asbyx.common.TntBow;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MainCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(sender.isOp()) {
            switch (command.getName().toLowerCase()) {
                case "allowtntbow":
                    Bukkit.getPluginManager().registerEvents(new TntBow(), Main.PLUGIN);
                    break;
                case "allowtomb":
                    Bukkit.getPluginManager().registerEvents(new DeathListener(), Main.PLUGIN);
                    break;
            }
        }
        return true;
    }
}
