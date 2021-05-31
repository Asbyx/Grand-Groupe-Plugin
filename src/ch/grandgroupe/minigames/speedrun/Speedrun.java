package ch.grandgroupe.minigames.speedrun;

import ch.grandgroupe.common.Main;
import ch.grandgroupe.common.features.AbstractListener;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

import java.util.*;
import java.util.stream.Collectors;

public class Speedrun extends AbstractListener
{
	private final Objective objective;
	private final List<Player> players;
	private final List<SpeedrunScoreboard> scoreboards;
	
	public Speedrun(Objective objective) {
		enable();
		
		this.objective = objective;
		Bukkit.getPluginManager().registerEvents(this, Main.PLUGIN);
		
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rules deathChest true");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rules compassTargeting true");
		
		players     = new ArrayList<>(Bukkit.getOnlinePlayers());
		scoreboards = players.stream().map(SpeedrunScoreboard::new).collect(Collectors.toList());
		
		Main.broadcast("Speedrun started ! The first to " + objective.description + " wins");
		Main.broadcast("May the odds be with you !");
	}
	
	public void stop() {
		Main.broadcast(ChatColor.RED + "Speedrun stopped");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rules deathChest false");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rules compassTargeting false");
		disable();
	}
	
	@EventHandler
	public void checkVictory(PlayerDeathEvent event) {
		Player killer = event.getEntity().getKiller();
		switch (objective.type) {
			case KILL:
				if (killer != null) endGame(killer);
				break;
			
			case POTION_KILL:
				if (killer != null
						&& (killer.getActivePotionEffects().stream().anyMatch(e -> Objective.bonusEffects.contains(e.getType()))
						|| event.getEntity().getActivePotionEffects().stream().anyMatch(e -> Objective.malusEffects.contains(e.getType()))))
					endGame(killer);
				break;
			
			default:
				break;
		}
	}
	
	@EventHandler
	public void checkVictory(PlayerAdvancementDoneEvent event) {
		if (objective.type != Objective.Types.ACHIEVEMENT) return;
		
		if (event.getAdvancement().getKey().getKey().equals(objective.data[0]))
			endGame(event.getPlayer());
	}
	
	private void endGame(Player winner) {
		String winCommand = "title %s title {\"text\":\"You won\",\"color\":\"green\"}",
				looseCommand = "title %s title {\"text\":\"%s won\",\"color\":\"red\",\"bold\":true}";
		
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), String.format(winCommand, winner.getName()));
		players.stream().filter(p -> p != winner).forEach(p ->
		{
			String command = String.format(looseCommand, p.getName(), winner.getName());
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
			Main.broadcast("dispatching command " + command);
		});
		
		scoreboards.forEach(SpeedrunScoreboard::stop);
	}
}
