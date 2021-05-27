package ch.thechi2000asbyx.common;

import ch.thechi2000asbyx.common.listeners.AbstractListener;
import ch.thechi2000asbyx.common.listeners.DeathChest;
import ch.thechi2000asbyx.common.listeners.NudeBow;
import ch.thechi2000asbyx.common.listeners.TntBow;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class rulesCommands implements CommandExecutor {
    private final List<AbstractListener> listeners = new ArrayList<>();

    public rulesCommands(){
        listeners.add(new TntBow());
        listeners.add(new DeathChest());
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
                            enable("Tnt bow");
                        }
                        else {
                            listeners.get(0).disable();
                            disable("Tnt bow");
                        }
                    } else {
                        try {
                            ((TntBow) listeners.get(0)).setPower(Float.parseFloat(arg[1]));
                        } catch (NumberFormatException e) {
                            sender.sendMessage(ChatColor.RED + "Invalid arguments: boolean (enabling) or float value (set the power)");
                        }
                    }
                    break;

                case "nudebow":
                    if (Boolean.parseBoolean(arg[1])) {
                        listeners.get(2).enable();
                        enable("Nude Bow");
                    } else {
                        listeners.get(2).disable();
                        disable("Nude Bow");
                    }
                    break;

                case "tomb":
                    if (Boolean.parseBoolean(arg[1])) {
                        listeners.get(1).enable();
                        enable("Deat chest");
                    } else {
                        listeners.get(1).disable();
                        disable("Death chest");
                    }
                    break;
            }
        }
        return true;
    }

    private void disable(String str){
        Main.broadcast(str + ChatColor.RED + " disabled");
    }

    private void enable(String str){
        Main.broadcast(str + ChatColor.GREEN + " enabled");
    }
}
