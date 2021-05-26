package ch.thechi2000asbyx.short_fallen_kingdom;

import ch.thechi2000asbyx.short_fallen_kingdom.Commands.FKTabCompleter;
import ch.thechi2000asbyx.short_fallen_kingdom.Events.EventsCommands;
import ch.thechi2000asbyx.short_fallen_kingdom.Scoreboards.Compass;
import ch.thechi2000asbyx.short_fallen_kingdom.Teams.*;
import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.*;

public class Main extends JavaPlugin implements Listener
{
	public static JavaPlugin PLUGIN;
	public static BukkitScheduler SCHEDULER;
	
	private final List<Compass> compasses = new ArrayList<>();
	
	@Override
	public void onEnable() {
		PLUGIN    = this;
		SCHEDULER = getServer().getScheduler();
		
		Bukkit.getPluginManager().registerEvents(new FlagEvents(), this);
		Bukkit.getPluginManager().registerEvents(new BuildEvents(), this);
		Bukkit.getPluginManager().registerEvents(this, this);
		
		Objects.requireNonNull(getCommand("fkteam")).setExecutor(new FKTeamCommands());
		Objects.requireNonNull(getCommand("game")).setExecutor(new EventsCommands());
		Objects.requireNonNull(getCommand("rules")).setExecutor(new rulesCommands());
		
		Objects.requireNonNull(getCommand("fkteam")).setTabCompleter(new FKTabCompleter());
		Objects.requireNonNull(getCommand("game")).setTabCompleter(new FKTabCompleter());
		
		FKTeam.loadTeamsFromConfig();
	}
	
	public static void broadcast(String arg) {
		getPlugin(Main.class).getServer().broadcastMessage(ChatColor.BLUE + "[Server] " + arg + ChatColor.WHITE);
	}
}
