package ch.thechi2000.player_pointers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin implements Listener
{
    DistanceBoardsManager manager;

    @Override
    public void onEnable()
    {
        getServer().getPluginManager().registerEvents(this, this);
        manager = new DistanceBoardsManager();
    }


    @Override
    public void onDisable()
    {
        super.onDisable();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        event.setJoinMessage("Welcome !");
        manager.addPlayer(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        manager.removePlayer(event.getPlayer());
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event)
    {
        manager.updatePlayer(event.getPlayer());
    }
}
