package ch.grandgroupe.common;

import ch.grandgroupe.common.tabCompleter.TabCompleter;
import ch.grandgroupe.common.worlds.*;
import ch.grandgroupe.minigames.short_fallen_kingdom.Events.EventsCommands;
import ch.grandgroupe.minigames.short_fallen_kingdom.FKExecutor;
import ch.grandgroupe.minigames.short_fallen_kingdom.Teams.*;
import ch.grandgroupe.minigames.speedrun.SpeedrunExecutor;
import ch.grandgroupe.minigames.trainingPack.TrainingExecutor;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.*;

public final class Main extends JavaPlugin
{
	//use those instead of Bukkit's ones !
	public static JavaPlugin PLUGIN;
	public static BukkitScheduler SCHEDULER;
	
	@Override
	public void onEnable() {
		PLUGIN    = this;
		SCHEDULER = getServer().getScheduler();
		
		Worlds.init();
		
		registerMinigame("fk", new FKExecutor(new EventsCommands(), new FKTeamCommands()));
		registerMinigame("rules", new RulesCommands());
		registerMinigame("speedrun", new SpeedrunExecutor());
		registerMinigame("training", new TrainingExecutor());
		//registerMinigame("worlds", new WorldCommands());
		
		FKTeam.loadTeamsFromConfig();
	}
	
	//register for the minigames: name = name of the command | executor = executor managing the minigame
	private void registerMinigame(String name, CommandExecutor executor) {
		PluginCommand command = Objects.requireNonNull(getCommand(name));
		command.setExecutor(executor);
		command.setTabCompleter(new TabCompleter());
	}
	
	/**
	 * DO NOT USE Bukkit.broadcastMessage(), USE THIS INSTEAD ! Main.broadcast()
	 *
	 * Broadcast a message to every player
	 */
	public static void broadcast(String arg) {
		getPlugin(Main.class).getServer().broadcastMessage(ChatColor.BLUE + "[Server] " + arg);
	}
}
