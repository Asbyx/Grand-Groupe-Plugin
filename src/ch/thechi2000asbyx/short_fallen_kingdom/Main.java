package ch.thechi2000asbyx.short_fallen_kingdom;

import ch.thechi2000asbyx.short_fallen_kingdom.Events.EventsCommands;
import ch.thechi2000asbyx.short_fallen_kingdom.Scoreboards.FKScoreboard;
import ch.thechi2000asbyx.short_fallen_kingdom.Teams.*;
import org.bukkit.*;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Objects;

public class Main extends JavaPlugin implements Listener {
    public static JavaPlugin PLUGIN;
    public static BukkitScheduler SCHEDULER;

    private FKScoreboard test;

    @Override
    public void onEnable() {
        PLUGIN = this;
        SCHEDULER = getServer().getScheduler();

        Bukkit.getPluginManager().registerEvents(new FlagEvents(), this);
        Bukkit.getPluginManager().registerEvents(new BuildEvents(), this);
        Bukkit.getPluginManager().registerEvents(this, this);

        Objects.requireNonNull(getCommand("fkteam")).setExecutor(new FKTeamCommands());
        Objects.requireNonNull(getCommand("game")).setExecutor(new EventsCommands());
        Objects.requireNonNull(getCommand("rules")).setExecutor(new rulesCommands());

        FKTeam.loadTeamsFromConfig();
    }

    public static void broadcast(String arg) {
        getPlugin(Main.class).getServer().broadcastMessage(ChatColor.BLUE + "[Server] " + arg + ChatColor.WHITE);
    }
}
