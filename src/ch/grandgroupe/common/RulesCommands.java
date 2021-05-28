package ch.grandgroupe.common;

import ch.grandgroupe.common.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

/**
 * Class which manages all the listeners and their commands.
 */
public final class RulesCommands implements CommandExecutor {
    private final List<AbstractListener> listeners = new ArrayList<>();

    /**
     * Default constructor: initiate all listeners, disabled by default
     */
    public RulesCommands(){
        listeners.add(new TntBow());
        listeners.add(new DeathChest());
        listeners.add(new NudeBow());
        listeners.add(new Harvester());

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
                        enabler(Boolean.parseBoolean(arg[1]), 0, "Tnt Bow");
                    } else {
                        try {
                            ((TntBow) listeners.get(0)).setPower(Float.parseFloat(arg[1]));
                            Main.broadcast("Power of Tnt bow set to: " + ChatColor.GOLD + Float.parseFloat(arg[1]));
                        } catch (NumberFormatException e) {
                            sender.sendMessage(ChatColor.RED + "Invalid arguments: boolean (enabling) or float value (set the power)");
                        }
                    }
                    break;

                case "deathchest":
                    enabler(Boolean.parseBoolean(arg[1]), 1, "Death chest");
                    break;

                case "nudebow":
                    enabler(Boolean.parseBoolean(arg[1]), 2, "Nude Bow");
                    break;

                case "harvester":
                    if(arg[1].equalsIgnoreCase("true") || arg[1].equalsIgnoreCase("false")){
                        enabler(Boolean.parseBoolean(arg[1]), 3, "Harvester");
                    } else {
                        try {
                            ((Harvester) listeners.get(3)).setRadius(Integer.parseInt(arg[1]));
                            Main.broadcast("Radius of action of the harvester set to: " + ChatColor.GOLD + Integer.parseInt(arg[1]));
                        } catch (NumberFormatException e) {
                            sender.sendMessage(ChatColor.RED + "Invalid arguments: boolean (enabling) or float value (set the radius)");
                        }
                    }
                    break;
            }
        }
        return true;
    }

    //method to be called for basic enable/disable event: new value = enable value | index = index in the list | name = name of the Listener for the players
    private void enabler(boolean newValue, int index, String name){
        if (newValue) {
            listeners.get(index).enable();
            enable(name);
        } else {
            listeners.get(index).disable();
            disable(name);
        }
    }

    //do not use
    private void disable(String str){
        Main.broadcast(str + ChatColor.RED + " disabled");
    }
    private void enable(String str){
        Main.broadcast(str + ChatColor.GREEN + " enabled");
    }
}
