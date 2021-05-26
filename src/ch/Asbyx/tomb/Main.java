package ch.Asbyx.tomb;

import ch.thechi2000asbyx.common.DeathChest;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new DeathChest(), this);
    }

    @Override
    public void onDisable() {
    }
}
