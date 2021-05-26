package ch.thechi2000asbyx.short_fallen_kingdom;

import ch.thechi2000asbyx.common.DeathListener;
import ch.thechi2000asbyx.common.AbstractListener;
import ch.thechi2000asbyx.common.NudeBow;
import ch.thechi2000asbyx.common.TntBow;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class rulesCommands implements CommandExecutor {
    private final List<AbstractListener> listeners = new ArrayList<>();

    public rulesCommands(){
        listeners.add(new TntBow());
        listeners.add(new DeathListener());
        listeners.add(new NudeBow());

        listeners.forEach(abstractListener -> {
            abstractListener.disable();
            Bukkit.getPluginManager().registerEvents(abstractListener, Main.PLUGIN);
        });
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] arg) {
        if(sender.isOp()) {
            switch (arg[0].toLowerCase()) {
                case "tntbow":
                    if(arg[1].equalsIgnoreCase("true") || arg[1].equalsIgnoreCase("false")){
                        if (Boolean.parseBoolean(arg[1])) {
                            listeners.get(0).enable();
                            Main.broadcast("Tnt Bow enabled");
                        }
                        else {
                            listeners.get(0).disable();
                            Main.broadcast("Tnt Bow disabled");
                        }
                    } else {
                        try {
                            ((TntBow)listeners.get(0)).setPower(Float.parseFloat(arg[1]));
                        } catch (NumberFormatException e) {
                            sender.sendMessage(ChatColor.RED + "Invalid arguments: boolean (enabling) or float value (set the power)");
                        }
                    }
                    break;

                case "nudebow":
                    if (Boolean.parseBoolean(arg[1])) {
                        listeners.get(2).enable();
                        Main.broadcast("Nude Bow enabled");
                    } else {
                        listeners.get(2).disable();
                        Main.broadcast("Nude Bow disabled");
                    }
                    break;

                case "tomb":
                    if (Boolean.parseBoolean(arg[1])) {
                        listeners.get(1).enable();
                        Main.broadcast("Death chest enabled");
                    } else {
                        listeners.get(1).disable();
                        Main.broadcast("Death chest disabled");
                    }
                    break;
            }
        }
        return true;
    }
}
