package ch.thechi2000asbyx.short_fallen_kingdom;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new EventListener(500000, 60, 90, 0), this);
    }

    @Override
    public void onDisable() {
    }
}
