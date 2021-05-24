package ch.Asbyx.tomb;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new DeathListener(), this);
    }

    @Override
    public void onDisable() {
    }
}
