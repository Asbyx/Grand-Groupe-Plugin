package ch.grandgroupe.common;

import ch.grandgroupe.common.tabCompleter.TabCompleter;
import ch.grandgroupe.minigames.coop_defense.CoopCommands;
import ch.grandgroupe.minigames.short_fallen_kingdom.Events.EventsCommands;
import ch.grandgroupe.minigames.short_fallen_kingdom.FKExecutor;
import ch.grandgroupe.minigames.short_fallen_kingdom.Teams.*;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Objects;

public class Main extends JavaPlugin
{
	public static JavaPlugin PLUGIN;
	public static BukkitScheduler SCHEDULER;
	
	@Override
	public void onEnable() {
		PLUGIN    = this;
		SCHEDULER = getServer().getScheduler();
		
		registerCommand("fk", new FKExecutor(new EventsCommands(), new FKTeamCommands()));
		registerCommand("rules", new RulesCommands());
		registerCommand("coop", new CoopCommands());
		
		FKTeam.loadTeamsFromConfig();
	}
	
	private void registerCommand(String name, CommandExecutor executor) {
		PluginCommand c = Objects.requireNonNull(getCommand(name));
		c.setExecutor(executor);
		c.setTabCompleter(new TabCompleter());
	}
	
	public static void broadcast(String arg) {
		getPlugin(Main.class).getServer().broadcastMessage(ChatColor.BLUE + "[Server] " + arg + ChatColor.WHITE);
	}
}
