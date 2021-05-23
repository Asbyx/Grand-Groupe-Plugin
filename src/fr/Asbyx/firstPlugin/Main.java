package fr.Asbyx.firstPlugin;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new ConcreteListener(), this);
    }

    @Override
    public void onDisable() {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            if(command.getName().equals("coucou")) {
                getServer().broadcastMessage("Salut !");
            }
        }
        if(sender instanceof Player){
            if(command.getName().equals("showCoordinates")) {
                getServer().broadcastMessage(((Player) sender).getDisplayName() + ": " + new Coordinates(((Player) sender).getLocation()));
            }
        }
        return true;
    }
}
