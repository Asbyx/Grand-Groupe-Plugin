package ch.grandgroupe.common;

import ch.grandgroupe.common.tabCompleter.TabCompleter;
import ch.grandgroupe.minigames.short_fallen_kingdom.Events.EventsCommands;
import ch.grandgroupe.minigames.short_fallen_kingdom.FKExecutor;
import ch.grandgroupe.minigames.short_fallen_kingdom.Scoreboards.Compass;
import ch.grandgroupe.minigames.short_fallen_kingdom.Teams.*;
import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.*;

public class Main extends JavaPlugin implements Listener {
	public static JavaPlugin PLUGIN;
	public static BukkitScheduler SCHEDULER;

	private final List<Compass> compasses = new ArrayList<>();

	@Override
	public void onEnable() {
		PLUGIN = this;
		SCHEDULER = getServer().getScheduler();
		Bukkit.getPluginManager().registerEvents(this, this); //fixme wtf ?

		Objects.requireNonNull(getCommand("fk")).setExecutor(new FKExecutor(new EventsCommands(), new FKTeamCommands()));
		Objects.requireNonNull(getCommand("rules")).setExecutor(new rulesCommands());

		Objects.requireNonNull(getCommand("fk")).setTabCompleter(new TabCompleter());
		Objects.requireNonNull(getCommand("rules")).setTabCompleter(new TabCompleter());

		FKTeam.loadTeamsFromConfig();
	}

	public static void broadcast(String arg) {
		getPlugin(Main.class).getServer().broadcastMessage(ChatColor.BLUE + "[Server] " + arg + ChatColor.WHITE);
	}
}
